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
package memo.portal.web.client.profile;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import mojo.gwt.ui.client.activity.BaseActivity;
import mojo.gwt.ui.client.activity.ClientFactory;

import memo.portal.web.client.profile.ProfileActivity.ProfilePlace;

public class ProfileActivity extends BaseActivity<ProfilePlace> {

	public ProfileActivity(ClientFactory clientFactory, ProfilePlace place) {
		super(clientFactory, place);
	}

	@Override
	public void start(final AcceptsOneWidget container, EventBus eventBus) {
		// TODO
	}

	/**
	 * Carries activity parameters.
	 */
	public static class ProfilePlace extends Place {

		public Integer id;

		public ProfilePlace() {
			this(null);
		}

		public ProfilePlace(Integer id) {
			this.id = id;
		}
	}

	/**
	 * Converts place to / from uri compatible form.
	 */
	@Prefix("profile")
	public static class ProfileTokenizer implements PlaceTokenizer<ProfilePlace> {

		/**
		 * Called when navigating through the browser history buttons.
		 */
		@Override
		public ProfilePlace getPlace(String token) {
			Integer id = Integer.valueOf(token);
			return new ProfilePlace(id);
		}

		/**
		 * Called after the activity is started (but not the first time).
		 */
		@Override
		public String getToken(ProfilePlace place) {
			if (place.id != null) {
				return place.id.toString();
			}

			return "";
		}
	}
}
