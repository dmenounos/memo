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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;

import mojo.gwt.i18n.client.JSConstants;
import mojo.gwt.ui.client.form.FieldPanel;
import mojo.gwt.ui.client.form.RequiredValidation;
import mojo.gwt.ui.client.form.SelectBox;

import memo.domain.web.client.model.UserModel;
import memo.domain.web.client.store.CountryStore;
import memo.domain.web.client.store.LanguageStore;

public class ProfileForm extends FormPanel {

	public static final Messages msg = GWT.create(Messages.class);

	public static final String STYLENAME = "ProfileForm";

	private TextBox nickname;
	private TextBox password;
	private TextBox fullname;
	private TextBox email;
	private FlowPanel genderPanel;
	private RadioButton maleGender;
	private RadioButton femaleGender;
	private DateBox birthday;
	private SelectBox country;
	private SelectBox language;
	private TextBox timezone;
	private CheckBox terms;

	public ProfileForm() {
		FlexTable layout = new FlexTable();
		layout.addStyleName(STYLENAME + "-layout");

		placeField(layout, 0, 0, msg.nickname(), getNickname());
		placeField(layout, 1, 0, msg.password(), getPassword());
		placeField(layout, 2, 0, msg.birthday(), getBirthday(), getBirthday().getTextBox());
		placeField(layout, 3, 0, msg.country(), getCountry());
		placeField(layout, 4, 0, msg.timezone(), getTimezone());

		placeField(layout, 0, 1, msg.fullname(), getFullname());
		placeField(layout, 1, 1, msg.email(), getEmail());
		placeField(layout, 2, 1, msg.gender(), getMaleGender(), getFemaleGender());
		placeField(layout, 3, 1, msg.language(), getLanguage());
		placeField(layout, 4, 1, "&nbsp;", getTerms());

		addStyleName(STYLENAME);
		setAction(GWT.getModuleBaseURL() + "../app/login/openid/signup");
		setEncoding(FormPanel.ENCODING_URLENCODED);
		setMethod(FormPanel.METHOD_POST);
		setWidget(layout);

		RequiredValidation nicknameValidation = new RequiredValidation(fetchField(layout, 0, 0));
		nicknameValidation.setError(msg.nicknameEmpty());
		addSubmitHandler(nicknameValidation);

		RequiredValidation termsValidation = new RequiredValidation(fetchField(layout, 4, 1));
		termsValidation.setError(msg.termsEmpty());
		addSubmitHandler(termsValidation);
	}

	private FieldPanel<?> fetchField(HTMLTable layout, int row, int col) {
		return (FieldPanel<?>) layout.getWidget(row, col);
	}

	private void placeField(HTMLTable layout, int row, int col, String label, FocusWidget... fields) {
		layout.setWidget(row, col, new FieldPanel<FocusWidget>(new HTML(label), fields));
	}

	private void placeField(HTMLTable layout, int row, int col, String label, Composite wrap, FocusWidget... fields) {
		layout.setWidget(row, col, new FieldPanel<FocusWidget>(new HTML(label), wrap, fields));
	}

	public void initData(UserModel user) {
		getNickname().setValue(user.getNickname());
		getFullname().setValue(user.getFullname());
		getEmail().setValue(user.getEmail());
		getMaleGender().setValue(getMaleGender().getFormValue().equals(user.getGender()));
		getFemaleGender().setValue(getFemaleGender().getFormValue().equals(user.getGender()));
		getBirthday().setValue(user.getBirthday());
		getCountry().setSelectedValue(user.getCountry());
		getLanguage().setSelectedValue(user.getLanguage());
		getTimezone().setValue(user.getTimezone());
	}

	public TextBox getNickname() {
		if (nickname == null) {
			nickname = new TextBox();
			nickname.setName("nickname");
		}

		return nickname;
	}

	public TextBox getPassword() {
		if (password == null) {
			password = new PasswordTextBox();
			password.setName("password");
		}

		return password;
	}

	public TextBox getFullname() {
		if (fullname == null) {
			fullname = new TextBox();
			fullname.setName("fullname");
		}

		return fullname;
	}

	public TextBox getEmail() {
		if (email == null) {
			email = new TextBox();
			email.setName("email");
		}

		return email;
	}

	public FlowPanel getGenderPanel() {
		if (genderPanel == null) {
			genderPanel = new FlowPanel();
			genderPanel.add(getMaleGender());
			genderPanel.add(getFemaleGender());
		}

		return genderPanel;
	}

	public RadioButton getMaleGender() {
		if (maleGender == null) {
			maleGender = new RadioButton("gender", msg.maleGender());
			maleGender.setFormValue("MALE");
		}

		return maleGender;
	}

	public RadioButton getFemaleGender() {
		if (femaleGender == null) {
			femaleGender = new RadioButton("gender", msg.femaleGender());
			femaleGender.setFormValue("FEMALE");
		}

		return femaleGender;
	}

	public DateBox getBirthday() {
		if (birthday == null) {
			birthday = new DateBox();
			birthday.getTextBox().setName("birthday");
			birthday.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
		}

		return birthday;
	}

	public SelectBox getCountry() {
		if (country == null) {
			country = new SelectBox();
			country.setName("country");
			country.setValueField("code");
			country.setDescrField("name");
			country.setStore(new CountryStore());
			country.getSource().loadData();
		}

		return country;
	}

	public SelectBox getLanguage() {
		if (language == null) {
			language = new SelectBox();
			language.setName("language");
			language.setValueField("code");
			language.setDescrField("name");
			language.setStore(new LanguageStore());
			language.getSource().loadData();
		}

		return language;
	}

	public TextBox getTimezone() {
		if (timezone == null) {
			timezone = new TextBox();
			timezone.setName("timezone");
		}

		return timezone;
	}

	public CheckBox getTerms() {
		if (terms == null) {
			terms = new CheckBox(msg.terms());
		}

		return terms;
	}

	public interface Messages extends JSConstants {

		String nickname();

		String password();

		String fullname();

		String email();

		String gender();

		String maleGender();

		String femaleGender();

		String birthday();

		String country();

		String language();

		String timezone();

		String terms();

		String nicknameEmpty();

		String termsEmpty();
	}
}
