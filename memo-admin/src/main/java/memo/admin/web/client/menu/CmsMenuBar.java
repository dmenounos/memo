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
package memo.admin.web.client.menu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class CmsMenuBar extends MenuBar {

	public CmsMenuBar() {
		initSystemMenu();
	}

	protected void initSystemMenu() {
		MenuBar systemMenu = new MenuBar(true);

		systemMenu.addItem("Menu", new Command() {

			@Override
			public void execute() {
			}
		});

		systemMenu.addItem("Users", new Command() {

			@Override
			public void execute() {
			}
		});

		systemMenu.addItem("Groups", new Command() {

			@Override
			public void execute() {
			}
		});

		addItem(new MenuItem("System", systemMenu));
	}
}
