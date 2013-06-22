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
package memo.domain.dao.service.node;

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
import memo.domain.dao.model.Permissions;
import memo.domain.dao.model.node.Node;
import memo.domain.dao.model.node.NodeRule;
import memo.domain.dao.model.user.User;
import memo.domain.dao.model.user.UserRole;
import memo.domain.dao.service.PermissionResolver;

@Component
public class NodePermissionResolver extends PermissionResolver {

	@Autowired
	@Qualifier("auditContext")
	private AuditContext auditContext;

	@Autowired
	@Qualifier("nodeRuleService")
	private DataService<NodeRule> nodeRuleService;

	@Override
	protected int resolveRulePermissions(Permissions entity) {
		if (entity instanceof Node) {
			Node node = (Node) entity;
			User user = (User) auditContext.getUser();

			if (user != null) {
				// fetch node rules for this node
				Select<NodeRule> select = new Select<NodeRule>();
				select.filter(new ByKey().property("node").key(node.getId()));
				DataPage<NodeRule> dataPage = nodeRuleService.select(select);
				List<NodeRule> nodeRules = dataPage.getData();

				// get user roles for current user
				List<UserRole> userRoles = user.getRoles();

				for (NodeRule nodeRule : nodeRules) {
					UserRole nodeRole = nodeRule.getUserRole();

					for (UserRole userRole : userRoles) {
						if (EntityUtils.equals(nodeRole, userRole)) {
							return nodeRule.getPermissions();
						}
					}
				}
			}
		}

		return Permissions.NONE;
	}
}
