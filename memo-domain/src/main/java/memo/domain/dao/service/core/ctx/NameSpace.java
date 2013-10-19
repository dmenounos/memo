/*
 * Copyright (C) 2010 Dimitrios Menounos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package memo.domain.dao.service.core.ctx;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import memo.domain.dao.model.core.MountPoint;
import memo.domain.dao.model.core.Resource;
import memo.domain.dao.model.core.ctx.IName;
import memo.domain.dao.model.core.ctx.INameSpace;
import memo.domain.dao.service.core.ResourceService;

/**
 * Concrete Context implementation.
 */
public class NameSpace implements INameSpace {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ResourceService resourceService;
	private Resource resource;

	private NameSpace parent;
	private Map<String, NameSpace> contexts;

	public NameSpace(ResourceService resourceService, Resource resource, NameSpace parent) {
		this.resourceService = resourceService;
		this.resource = resource;
		this.parent = parent;
	}

	protected void initContexts() {
		logger.debug("initContexts [ " + this + " ]");
		contexts = new LinkedHashMap<String, NameSpace>();

		for (Resource childNode : resourceService.getChildNodes(resource)) {
			contexts.put(childNode.getCode(), new NameSpace(resourceService, childNode, this));
		}
	}

	protected Map<String, NameSpace> getContexts() {
		if (contexts == null) {
			initContexts();
		}

		return contexts;
	}

	@Override
	public INameSpace createPath(IName name) {
		if (name == null || name.getSize() == 0) {
			throw new IllegalArgumentException("name must not be empty");
		}

		NameSpace ctx = this;
		NameSpace subctx = null;

		for (String ne : name) {
			if (ctx.resource.isLeaf()) {
				throw new RuntimeException("Context [" + ctx.resource.getCode() + "] has been bound as a leaf.");
			}

			subctx = ctx.getContexts().get(ne);

			if (subctx == null) {
				Resource childNode = resourceService.createChildNode(ctx.resource, ne);
				subctx = new NameSpace(resourceService, childNode, ctx);
				ctx.getContexts().put(ne, subctx);
			}

			ctx = subctx;
		}

		return ctx;
	}

	@Override
	public INameSpace lookupPath(IName name) {
		if (name == null || name.getSize() == 0) {
			throw new IllegalArgumentException("name must not be empty");
		}

		NameSpace ctx = this;
		NameSpace subctx = null;

		for (Object ne : name) {
			if (ctx.resource.isLeaf()) {
				throw new RuntimeException("Context [" + ctx.resource.getCode() + "] has been bound as a leaf.");
			}

			subctx = ctx.getContexts().get(ne);

			if (subctx == null) {
				throw new RuntimeException("Element [" + ne + "] from name [" + name + "] has not been bound.");
			}

			ctx = subctx;
		}

		return ctx;
	}

	@Override
	public Object rebind(IName name, Object obj) {
		if (name != null) {
			INameSpace ctx = createPath(name);
			return ctx.rebind(null, obj);
		}

		Object result = lookup(name);

		MountPoint m = (MountPoint) obj;
		m.setResource(resource);

		resource.setHint(obj.getClass().getName());
		resource = resourceService.update(resource);

		return result;
	}

	@Override
	public Object unbind(IName name) {
		if (name != null) {
			INameSpace ctx = lookupPath(name);
			ctx.unbind(null);
		}

		Object result = lookup(name);

		resource.setHint(null);
		resource = resourceService.update(resource);

		return result;
	}

	@Override
	public Object lookup(IName name) {
		if (name != null) {
			INameSpace ctx = lookupPath(name);
			return ctx.lookup(null);
		}

		return resource.getHint();
	}

	@Override
	public Set<?> list() {
		Set<Object> result = new LinkedHashSet<Object>();
		result.addAll(contexts.keySet());
		return result;
	}

	@Override
	public String toString() {
		Name name = new Name();
		NameSpace ns = this;

		while (ns != null && !ns.resource.isRoot()) {
			name.insert(0, ns.resource.getCode());
			ns = ns.parent;
		}

		return name.toString();
	}

	@Override
	public String toTreeString() {
		StringBuilder sb = new StringBuilder();
		printTree(0, sb);
		return sb.toString();
	}

	public void printTree(int d, StringBuilder sb) {
		for (int i = 0; i < d; ++i) {
			sb.append("\t");
		}

		sb.append(resource.getCode());
		sb.append(" --> ");
		sb.append(resource.getHint());
		sb.append("\n");

		d++;

		for (Entry<String, NameSpace> entry : getContexts().entrySet()) {
			entry.getValue().printTree(d, sb);
		}
	}
}
