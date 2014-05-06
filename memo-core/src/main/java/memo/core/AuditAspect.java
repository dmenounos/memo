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
package memo.core;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mojo.core.AuditContext;
import mojo.core.spec.Insert;
import mojo.core.spec.Update;

import memo.core.model.AuditableEntity;
import memo.core.model.user.User;

@Component
public class AuditAspect {

	@Autowired
	@Qualifier("auditContext")
	private AuditContext auditContext;

	public void insert(Insert<?> query) {
		Object object = query.getEntity();

		if (object instanceof AuditableEntity) {
			AuditableEntity entity = (AuditableEntity) object;
			entity.setCreateUser((User) auditContext.getUser());
			entity.setCreateDate(new Date());
		}
	}

	public void update(Update<?> query) {
		Object object = query.getEntity();

		if (object instanceof AuditableEntity) {
			AuditableEntity entity = (AuditableEntity) object;
			entity.setUpdateUser((User) auditContext.getUser());
			entity.setUpdateDate(new Date());
		}
	}
}
