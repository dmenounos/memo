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

import memo.domain.dao.model.Permissions;

public class PermissionResolver {

	public boolean hasReadAccess(Permissions entity) {
		return hasAccess(entity, Permissions.READ);
	}

	public boolean hasWriteAccess(Permissions entity) {
		return hasAccess(entity, Permissions.WRITE);
	}

	public boolean hasExecuteAccess(Permissions entity) {
		return hasAccess(entity, Permissions.EXECUTE);
	}

	protected boolean hasAccess(Permissions entity, int access) {
		int defaultAccess = resolvePermissions(entity);

		if (access == (defaultAccess & access)) {
			return true;
		}

		int ruleAccess = resolveRulePermissions(entity);

		if (access == (ruleAccess & access)) {
			return true;
		}

		return false;
	}

	/**
	 * Resolve default access for entity.
	 */
	protected int resolvePermissions(Permissions entity) {
		return entity.getPermissions();
	}

	/**
	 * Resolve rule based access for entity.
	 */
	protected int resolveRulePermissions(Permissions entity) {
		return Permissions.NONE;
	}
}
