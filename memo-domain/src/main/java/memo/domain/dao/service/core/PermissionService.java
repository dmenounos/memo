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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mojo.dao.core.DataService;

import memo.domain.dao.model.core.Permission;

@Service
public class PermissionService extends DataService<Permission> {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private PermissionValidation permissionValidation;

	@PostConstruct
	protected void init() {
		setRepository(permissionRepository);
		setValidation(permissionValidation);
	}
}
