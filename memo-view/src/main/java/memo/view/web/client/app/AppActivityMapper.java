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
package memo.view.web.client.app;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import mojo.gwt.ui.client.activity.ClientFactory;
import mojo.gwt.ui.client.activity.page.PageActivity;
import mojo.gwt.ui.client.activity.page.PageActivity.PagePlace;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof PagePlace) {
			PagePlace contentPlace = (PagePlace) place;
			return new PageActivity(clientFactory, contentPlace);
		}

		return null;
	}
}
