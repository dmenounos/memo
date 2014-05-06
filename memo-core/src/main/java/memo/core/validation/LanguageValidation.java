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
package memo.core.validation;

import java.util.List;

import mojo.core.Validation;

import memo.core.model.misc.Language;

public class LanguageValidation extends Validation<Language> {

	@Override
	protected void validate(Language entity, List<String> errors) {
		if (checkNull(entity, errors, "Language.null")) {
			return;
		}

		checkEmpty(entity.getCode(), errors, "Language.code.empty");
		checkEmpty(entity.getName(), errors, "Language.name.empty");
	}
}
