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
package memo.core.dao.service.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mojo.dao.AuditContext;
import mojo.dao.core.DataException;
import mojo.dao.core.DataService;

import memo.core.dao.model.EntityUtils;
import memo.core.dao.model.core.Permission;
import memo.core.dao.model.core.Resource;
import memo.core.dao.model.user.User;
import memo.core.dao.model.user.UserRole;

@Service
public class ResourceService extends DataService<Resource> {

	@Autowired
	private AuditContext auditContext;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ResourceValidation resourceValidation;

	@PostConstruct
	protected void init() {
		setRepository(resourceRepository);
		setValidation(resourceValidation);
	}

	public Resource getRootNode() {
		logger.debug("FETCHING ROOT NODE");
		return resourceRepository.getRootNode();
	}

	public Resource createRootNode() {
		logger.debug("CREATING ROOT NODE");
		Resource rootNode = new Resource();
		return insert(rootNode);
	}

	public List<Resource> getChildNodes(Resource parentNode) {
		logger.debug("FETCHING CHILD NODES FOR " + parentNode);
		return resourceRepository.getChildNodes(parentNode);
	}

	public Resource createChildNode(Resource parentNode, String code) {
		logger.debug("CREATING CHILD NODE " + code + " FOR " + parentNode);
		Resource childNode = parentNode.createChildNode(code);
		return insert(childNode);
	}

	//
	// PERMISSION RESOLVING FUNCTIONALLITY
	//

	protected boolean hasReadAccess(Resource entity) {
		return hasAccess(entity, Permission.READ);
	}

	protected boolean hasWriteAccess(Resource entity) {
		return hasAccess(entity, Permission.WRITE);
	}

	protected boolean hasExecuteAccess(Resource entity) {
		return hasAccess(entity, Permission.EXECUTE);
	}

	protected boolean hasAccess(Resource entity, int access) {
		List<Permission> resourcePermissions = resourceRepository.getPermissions(entity);

		if (resourcePermissions != null && !resourcePermissions.isEmpty()) {
			int actorPermissions = resolvePermission(resourcePermissions);
			return access == (actorPermissions & access);
		}

		return true;
	}

	protected int resolvePermission(List<Permission> permissions) {
		User user = (User) auditContext.getUser();

		if (user != null) {
			List<UserRole> userRoles = user.getRoles();

			for (Permission permission : permissions) {
				UserRole permissionRole = permission.getUserRole();

				for (UserRole userRole : userRoles) {
					if (EntityUtils.equals(userRole, permissionRole)) {
						return permission.getPermission();
					}
				}
			}
		}

		return Permission.NONE;
	}

	//
	// AUTHORIZATION FUNCTIONALLITY
	// USES PERMISSION RESOLVING FUNCTIONALLITY
	//

	@Override
	protected void beforeInsert(Resource node) {
		checkWrite(node, null);
	}

	@Override
	protected void beforeUpdate(Resource node) {
		checkWrite(node, findById(node.getId()));
	}

	@Override
	protected void beforeDelete(Object id) {
		checkWrite(null, findById(id));
	}

	protected void checkWrite(Resource clientNode, Resource serverNode) {
		Resource serverParent;

		//
		// Update or Delete.
		//

		if (serverNode != null) {
			logger.debug("CHECKING WRITE PERMISSION FOR UPDATE OR DELETE");

			serverParent = serverNode.getParentNode();

			// check parent node permission
			if (serverParent != null && !hasWriteAccess(serverParent)) {
				StringBuilder sb = new StringBuilder("Parent node permission violation;");
				sb.append(" #" + serverParent.getId() + " " + serverParent.getCode());
				throw new DataException(sb.toString());
			}

			// check node permission
			if (!hasWriteAccess(serverNode)) {
				StringBuilder sb = new StringBuilder("Node permission violation;");
				sb.append(" #" + serverNode.getId() + " " + serverNode.getCode());
				throw new DataException(sb.toString());
			}
		}

		//
		// Create or Update.
		//

		if (clientNode != null) {
			logger.debug("CHECKING WRITE PERMISSION FOR CREATE OR UPDATE");

			boolean isCreate = serverNode == null;
			boolean isUpdate = serverNode != null;

			if (!clientNode.isRoot()) {
				serverParent = super.findById(clientNode.getParentNode().getId());

				if (serverParent == null) {
					throw new DataException("Non existing parent node");
				}

				if (isCreate && clientNode.getId() != null) {
					// either insert with id (from beforeInsert)
					// or update with non-existing id (from beforeUpdate)
					throw new DataException("Illegal create or update; wrong id");
				}

				if (isUpdate && checkHierarchy(serverNode, serverParent)) {
					// serverParent at the same level (or below) of serverNode
					throw new DataException("Illegal update; move node within it's own subtree");
				}

				// check parent node permission
				if (!hasWriteAccess(serverParent)) {
					StringBuilder sb = new StringBuilder("Parent node permission violation;");
					sb.append(" #" + serverParent.getId() + " " + serverParent.getCode());
					throw new DataException(sb.toString());
				}
			}
		}
	}

	/**
	 * Check whether the "child" belongs to a subtree.<br />
	 * The subtree starts from (and includes) the "parent".
	 */
	protected boolean checkHierarchy(Resource parent, Resource child) {
		for (Resource node = child; node != null; node = node.getParentNode()) {
			if (node.getId().equals(parent.getId())) {
				return true;
			}
		}

		return false;
	}
}
