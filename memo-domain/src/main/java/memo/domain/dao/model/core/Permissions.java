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
package memo.domain.dao.model.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import memo.domain.dao.model.AbstractEntity;

@MappedSuperclass
public class Permissions extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// @formatter:off
	public static final int ALL     = 7;
	public static final int READ    = 4;
	public static final int WRITE   = 2;
	public static final int EXECUTE = 1;
	public static final int NONE    = 0;
	// @formatter:on

	/**
	 * Same as UNIX.
	 */
	private int permissions;

	@Column(nullable = false)
	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
}
