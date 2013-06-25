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
package memo.domain.dao.service;

import java.util.Collections;
import java.util.List;

import memo.domain.dao.model.AbstractEntity;
import memo.domain.dao.model.core.Permissions;

public class PermissionResolver<E extends AbstractEntity, P extends Permissions> {

	public boolean hasReadAccess(E entity) {
		return hasAccess(entity, Permissions.READ);
	}

	public boolean hasWriteAccess(E entity) {
		return hasAccess(entity, Permissions.WRITE);
	}

	public boolean hasExecuteAccess(E entity) {
		return hasAccess(entity, Permissions.EXECUTE);
	}

	protected boolean hasAccess(E entity, int access) {
		List<P> entityPermissionsList = getEntityPermissionsList(entity.getId());

		if (entityPermissionsList != null && !entityPermissionsList.isEmpty()) {
			int actorPermissions = resolveActorPermissions(entityPermissionsList);
			return access == (actorPermissions & access);
		}

		return true;
	}

	protected List<P> getEntityPermissionsList(Object entityId) {
		return Collections.emptyList();
	}

	protected int resolveActorPermissions(List<P> permissions) {
		return Permissions.NONE;
	}
}
