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
package memo.portal.web.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import mojo.gwt.i18n.client.JSConstants;

public class LoginPanel extends Composite {

	interface Binder extends UiBinder<Widget, LoginPanel> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField(provided = true)
	public static final Messages msg = GWT.create(Messages.class);

	@UiField
	TextBox username;

	@UiField
	TextBox password;

	@UiField
	Button signIn;

	@UiField
	Button signUp;

	@UiField
	FlowPanel eastPanel;

	public LoginPanel() {
		initWidget(binder.createAndBindUi(this));
	}

	public interface Messages extends JSConstants {

		String username();

		String password();

		String remember();

		String signIn();

		String signUp();

		String signUpInfo();
	}
}
