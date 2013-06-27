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
package memo.domain.dao.service.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mojo.dao.AuditContext;
import mojo.dao.core.DataPage;
import mojo.dao.core.DataService;
import mojo.dao.core.spec.ByKey;
import mojo.dao.core.spec.Select;

import memo.domain.dao.model.EntityUtils;
import memo.domain.dao.model.core.Actor;
import memo.domain.dao.model.core.Permission;
import memo.domain.dao.model.core.Resource;
import memo.domain.dao.model.user.User;
import memo.domain.dao.model.user.UserRole;

@Component
public class PermissionResolver {

	@Autowired
	@Qualifier("auditContext")
	private AuditContext auditContext;

	@Autowired
	@Qualifier("permissionService")
	private DataService<Permission> permissionService;

	public boolean hasReadAccess(Resource entity) {
		return hasAccess(entity, Permission.READ);
	}

	public boolean hasWriteAccess(Resource entity) {
		return hasAccess(entity, Permission.WRITE);
	}

	public boolean hasExecuteAccess(Resource entity) {
		return hasAccess(entity, Permission.EXECUTE);
	}

	protected boolean hasAccess(Resource entity, int access) {
		List<Permission> entityPermissionsList = getEntityPermissionsList(entity.getId());

		if (entityPermissionsList != null && !entityPermissionsList.isEmpty()) {
			int actorPermissions = resolveActorPermissions(entityPermissionsList);
			return access == (actorPermissions & access);
		}

		return true;
	}

	protected List<Permission> getEntityPermissionsList(Object resourceId) {
		Select<Permission> select = new Select<Permission>();
		select.filter(new ByKey().property("resource").key(resourceId));
		DataPage<Permission> dataPage = permissionService.select(select);
		List<Permission> permissions = dataPage.getData();
		return permissions;
	}

	protected int resolveActorPermissions(List<Permission> permissions) {
		User user = (User) auditContext.getUser();

		if (user != null) {

			// check for user permission match

			for (Permission permission : permissions) {
				Actor permissionActor = permission.getActor();

				if (EntityUtils.equals(user, permissionActor)) {
					return permission.getPermission();
				}
			}

			// check for user role permission match

			List<UserRole> userRoles = user.getRoles();

			for (Permission permission : permissions) {
				Actor permissionActor = permission.getActor();

				for (UserRole userRole : userRoles) {
					if (EntityUtils.equals(userRole, permissionActor)) {
						return permission.getPermission();
					}
				}
			}
		}

		return Permission.NONE;
	}
}
