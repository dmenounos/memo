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
package memo.view.web.server.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mojo.web.login.RequireLogin;

@Controller
@RequireLogin
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/controller", method = RequestMethod.GET)
	public void controller(HttpServletRequest req, HttpServletResponse res) {
		TestTemplate template = new TestTemplate("testTemplate");
		template.render();
	}

	@RequestMapping(value = "/urisuffix.pro", method = RequestMethod.GET)
	public void urisuffix(HttpServletRequest req, HttpServletResponse res) {
		TestTemplate template = new TestTemplate("testTemplate");
		template.render();
	}
}