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
package memo.portal.web.client;

public class PortalEntryPoint extends PortalClientFactory {

	@Override
	public void onModuleLoad() {
		super.onModuleLoad();

//		Anchor forum = Anchor.wrap(DOM.getElementById("menu-forum").getFirstChildElement());
//		forum.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				event.preventDefault();
//
//				Label moduleBaseURL = new Label("ModuleBaseURL: " + GWT.getModuleBaseURL());
//				rootContainer.add(moduleBaseURL);
//			}
//		});
//
//		Anchor users = Anchor.wrap(DOM.getElementById("menu-users").getFirstChildElement());
//		users.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				event.preventDefault();
//
//				Label hostPageBaseURL = new Label("HostPageBaseURL: " + GWT.getHostPageBaseURL());
//				rootContainer.add(hostPageBaseURL);
//			}
//		});
	}
}
