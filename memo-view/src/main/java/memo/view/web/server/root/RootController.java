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
package memo.view.web.server.root;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		System.out.println("### initBinder " + binder.getTarget());
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void doRoot(HttpServletRequest req, HttpServletResponse res) {
		RootTemplate template = new RootTemplate("portalTemplate");
		template.render();
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String doExit(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
