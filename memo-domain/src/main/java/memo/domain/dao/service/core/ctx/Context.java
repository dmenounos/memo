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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mojo.dao.core.DataService;

import memo.domain.dao.model.core.MountPoint;
import memo.domain.dao.model.core.Resource;
import memo.domain.dao.model.core.ctx.IContext;
import memo.domain.dao.model.core.ctx.IName;
import memo.domain.dao.service.core.ResourceService;

/**
 * Concrete Context implementation.
 */
public class Context implements IContext {

	private DataService<Object> service;

	private ResourceService resourceService;
	private Resource resource;

	private Map<String, Context> contexts;

	public Context(ResourceService resourceService, Resource resource) {
		this.resourceService = resourceService;
		this.resource = resource;
	}

	protected void initContexts() {
		contexts = new HashMap<String, Context>();

		for (Resource childNode : resourceService.getChildNodes(resource)) {
			contexts.put(childNode.getCode(), new Context(resourceService, childNode));
		}
	}

	protected Map<String, Context> getContexts() {
		if (contexts == null) {
			initContexts();
		}

		return contexts;
	}

	@Override
	public IContext reserveCtx(IName name) {
		if (name == null || name.getSize() == 0) {
			throw new IllegalArgumentException("name must not be empty");
		}

		Context ctx = this;
		Context subctx = ctx;

		for (String ne : name) {
			if (ctx.resource.isLeaf()) {
				throw new RuntimeException("Context [" + ctx.resource.getCode() + "] has been bound as a leaf.");
			}

			subctx = ctx.getContexts().get(ne);

			if (subctx == null) {
				Resource childNode = resourceService.createChildNode(ctx.resource, ne);
				subctx = new Context(resourceService, childNode);
				ctx.getContexts().put(ne, subctx);
			}

			ctx = subctx;
		}

		return ctx;
	}

	@Override
	public IContext traverseCtx(IName name) {
		if (name == null || name.getSize() == 0) {
			throw new IllegalArgumentException("name must not be empty");
		}

		Context ctx = this;
		Context subctx = ctx;

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
	public void bind(IName name, Object obj) {
		if (name != null) {
			IContext ctx = reserveCtx(name);
			ctx.bind(null, obj);
			return;
		}

		if (resource.getHint() != null) {
			throw new RuntimeException("Context [" + resource.getCode() + "] has already been bound.");
		}

		MountPoint m = (MountPoint) obj;
		m.setResource(resource);
		service.update(m);

		resource.setHint(obj.getClass().getName());
		resource = resourceService.update(resource);
	}

	@Override
	public Object rebind(IName name, Object obj) {
		if (name != null) {
			IContext ctx = reserveCtx(name);
			return ctx.rebind(null, obj);
		}

		Object result = lookup(name);

		MountPoint m = (MountPoint) obj;
		m.setResource(resource);
		service.update(m); // TODO

		resource.setHint(obj.getClass().getName());
		resource = resourceService.update(resource);

		return result;
	}

	@Override
	public void unbind(IName name) {
		if (name == null || name.getSize() == 0) {
			throw new IllegalArgumentException("name must not be empty");
		}

		if (name.getSize() > 1) {
			IName prefix = name.createPrefix(name.getSize() - 1);
			IContext ctx = traverseCtx(prefix);
			IName suffix = name.createSuffix(name.getSize() - 1);
			ctx.unbind(suffix);
		}
		else {
			Object ne = name.getElement(0);

			if (getContexts().containsKey(ne)) {
				Context subctx = getContexts().get(ne);
				resourceService.delete(subctx.resource.getId());
				getContexts().remove(ne);
			}
			else if (getObjects().containsKey(ne)) {
				resource.setHint(null);
				resource = resourceService.update(resource);
				getObjects().remove(ne);
			}
		}
	}

	@Override
	public Object lookup(IName name) {
		if (name != null) {
			IContext ctx = traverseCtx(name);
			return ctx.lookup(null);
		}

		return resource.getHint(); // TODO
	}

	@Override
	public Set<?> list() {
		Set<Object> result = new HashSet<Object>();
		result.addAll(contexts.keySet());
		return result;
	}
}
