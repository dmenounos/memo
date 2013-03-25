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
package memo.admin.web.client.node;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import mojo.gwt.ui.client.activity.BaseActivity;
import mojo.gwt.ui.client.activity.ClientFactory;

import memo.admin.web.client.node.NodeActivity.NodePlace;

public class NodeActivity extends BaseActivity<NodePlace> {

	public NodeActivity(ClientFactory clientFactory, NodePlace nodePlace) {
		super(clientFactory, nodePlace);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		NodeComponent view = new NodeComponent();
		view.setActivity(this);
		panel.setWidget(view);
	}

	/**
	 * Carries activity parameters.
	 */
	public static class NodePlace extends Place {
	}

	/**
	 * Converts place to / from uri compatible form.
	 */
	@Prefix("node")
	public static class NodeTokenizer implements PlaceTokenizer<NodePlace> {

		@Override
		public NodePlace getPlace(String token) {
			return new NodePlace();
		}

		@Override
		public String getToken(NodePlace place) {
			return "";
		}
	}
}
