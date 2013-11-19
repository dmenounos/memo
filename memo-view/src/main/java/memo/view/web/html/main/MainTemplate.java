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
package memo.view.web.html.main;

import mojo.web.component.html.HtmlTemplate;

public class MainTemplate extends HtmlTemplate {

	protected MainTemplate(String id) {
		super(id);

		setTitle("MEMO");

		String contextPath = getRequest().getContextPath();

		// Init css style files.
		// getStyles().add(contextPath + "/resources/css/reset.css");
		getStyles().add(contextPath + "/resources/css/grids.css");
		getStyles().add(contextPath + "/bootstrap/css/bootstrap.css");
		getStyles().add(contextPath + "/kendoui/styles/kendo.common.min.css");
		getStyles().add(contextPath + "/kendoui/styles/kendo.default.min.css");
		getStyles().add(contextPath + "/resources/theme.css");

		// Init javascript files.
		getScripts().add(contextPath + "/resources/jquery/jquery.js");
		getScripts().add(contextPath + "/bootstrap/js/bootstrap.js");
		getScripts().add(contextPath + "/backbone/js/underscore.js");
		getScripts().add(contextPath + "/backbone/js/backbone.js");
		getScripts().add(contextPath + "/kendoui/js/kendo.web.min.js");
		getScripts().add(contextPath + "/resources/script.js");
	}
}
