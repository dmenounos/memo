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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

import mojo.gwt.i18n.client.JSConstants;
import mojo.gwt.ui.client.util.WebUtils;

import memo.domain.web.client.model.UserModel;

public abstract class ProfilePanel extends Composite {

	public static final Messages msg = GWT.create(Messages.class);

	public static final String STYLENAME = "ProfilePanel";

	private ProfileForm formPanel;
	private Button submitButton;
	private Button cancelButton;

	public ProfilePanel(UserModel user) {
		Label greeting = new HTML(msg.greeting());
		greeting.addStyleName(STYLENAME + "-greeting");

		SimplePanel decor = new SimplePanel();
		decor.addStyleName(STYLENAME + "-decor");
		decor.setWidget(getFormPanel());

		FlowPanel buttons = new FlowPanel();
		buttons.addStyleName(STYLENAME + "-buttons");
		buttons.add(getSubmitButton());
		buttons.add(getCancelButton());

		FlowPanel main = new FlowPanel();
		main.addStyleName(STYLENAME);
		main.addStyleName("content");
		main.add(greeting);
		main.add(decor);
		main.add(buttons);

		getFormPanel().initData(user);
		initWidget(main);
	}

	public abstract void onSubmit();

	public abstract void onCancel();

	public ProfileForm getFormPanel() {
		if (formPanel == null) {
			formPanel = new ProfileForm();
			formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {

				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					String results = event.getResults();
					System.out.println("### " + results);

					if (results != null && results.contains("exception")) {
						WebUtils.alert("Whoops!", results);
						return;
					}

					onSubmit();
				}
			});
		}

		return formPanel;
	}

	public Button getSubmitButton() {
		if (submitButton == null) {
			submitButton = new Button(msg.submit());
			submitButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					getFormPanel().submit();
				}
			});
		}

		return submitButton;
	}

	public Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Button(msg.cancel());
			cancelButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					onCancel();
				}
			});
		}

		return cancelButton;
	}

	public interface Messages extends JSConstants {

		String greeting();

		String submit();

		String cancel();
	}
}
