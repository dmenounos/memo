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

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mojo.dao.core.jpa.JpaRepository;

import memo.domain.dao.model.core.Permission;
import memo.domain.dao.model.core.Resource;

@Repository
public class ResourceRepository extends JpaRepository<Resource> {

	public ResourceRepository() {
		setEntityType(Resource.class);
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissions(Object resourceId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p FROM Permission p ");
		sb.append("WHERE p.resource.id = :resourceId");

		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter("resourceId", resourceId);

		return query.getResultList();
	}
}
