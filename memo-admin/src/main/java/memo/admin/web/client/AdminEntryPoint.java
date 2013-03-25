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
package memo.admin.web.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import memo.admin.web.client.menu.CmsMenuBar;
import memo.admin.web.client.node.NodeActivity.NodePlace;

public class AdminEntryPoint extends AdminClientFactory {

	private DockLayoutPanel dockPanel;

	@Override
	public void onModuleLoad() {
		initClientFactory();
		getPlaceController().goTo(new NodePlace());
	}

	@Override
	protected AcceptsOneWidget createRootContainer() {
		dockPanel = new DockLayoutPanel(Unit.PX);
		dockPanel.addStyleName("RootDockLayout");
		dockPanel.addNorth(new CmsMenuBar(), 20);

		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		rootPanel.addStyleName("RootLayout");
		rootPanel.add(dockPanel);

		return new AcceptsOneWidget() {

			private Widget widget;

			@Override
			public void setWidget(IsWidget w) {
				if (widget != null) {
					dockPanel.remove(widget);
				}

				if (w != null) {
					widget = w.asWidget();
					dockPanel.add(widget);
				}
			}
		};
	}
}
