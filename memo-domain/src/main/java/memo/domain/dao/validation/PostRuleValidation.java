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
package memo.domain.dao.validation;

import java.util.List;

import mojo.dao.core.Validation;

import memo.domain.dao.model.post.PostRule;

public class PostRuleValidation extends Validation<PostRule> {

	@Override
	protected void validate(PostRule entity, List<String> errors) {
		if (checkNull(entity, errors, "PostRule.null")) {
			return;
		}

		checkNull(entity.getPost(), errors, "PostRule.node.null");
		checkNull(entity.getUserRole(), errors, "PostRule.userGroup.null");
	}
}
