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
package memo.view.web.client;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import mojo.gwt.data.client.util.JSObject;
import mojo.gwt.i18n.client.JSConstants;
import mojo.gwt.ui.client.activity.BaseClientFactory;
import mojo.gwt.ui.client.activity.page.PageContainer;

import memo.core.web.client.model.UserModel;
import memo.view.web.client.app.AppActivityMapper;
import memo.view.web.client.app.AppPlaceHistoryMapper;
import memo.view.web.client.login.LoginContainer;
import memo.view.web.client.login.ProfilePanel;
import memo.view.web.client.util.UIHelper;

public class PortalClientFactory extends BaseClientFactory {

	public static final Messages msg = GWT.create(Messages.class);

	protected PageContainer rootContainer;

	/**
	 * Setup callback functions.
	 */
	public static native void initGlobalJS()
	/*-{
		$wnd.obj.ns('session', {
			signIn: @memo.view.web.client.PortalClientFactory::signIn(),
			signUp: @memo.view.web.client.PortalClientFactory::signUp(Lmojo/gwt/data/client/util/JSObject;)
		});
	}-*/;

	@Override
	public void onModuleLoad() {
		initClientFactory();
		initGlobalJS();
	}

	@Override
	protected ActivityMapper createActivityMapper() {
		return new AppActivityMapper(this);
	}

	@Override
	protected PlaceHistoryMapper createHistoryMapper() {
		return GWT.create(AppPlaceHistoryMapper.class);
	}

	@Override
	protected AcceptsOneWidget createRootContainer() {
		Element element = Document.get().getElementById("root");
		rootContainer = PageContainer.wrap(element);
		rootContainer.setClientFactory(this);
		return rootContainer;
	}

	@Override
	public void requireLogin() {
		LoginContainer loginContainer = new LoginContainer();
		UIHelper.openDialog(loginContainer, msg.loginPanel());
	}

	/**
	 * Callback; finalizes the login process.
	 */
	public static void signIn() {
		Window.Location.reload();
	}

	/**
	 * Callback; finalizes the register process.
	 */
	public static void signUp(JSObject userJSO) {
		UserModel userModel = new UserModel(userJSO);
		ProfilePanel profilePanel = new ProfilePanel(userModel) {

			@Override
			public void onSubmit() {
				signIn();
			}

			@Override
			public void onCancel() {
				UIHelper.closeDialog();
			}
		};

		UIHelper.reopenDialog(profilePanel, msg.profilePanel());
	}

	public interface Messages extends JSConstants {

		String loginPanel();

		String profilePanel();
	}
}
