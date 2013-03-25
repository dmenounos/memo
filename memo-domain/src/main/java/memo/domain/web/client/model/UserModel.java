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
package memo.domain.web.client.model;

import java.util.Date;

import com.google.gwt.core.client.GWT;

import mojo.gwt.data.client.convert.base.DateConverter;
import mojo.gwt.data.client.type.ClassType;
import mojo.gwt.data.client.util.JSObject;

public class UserModel {

	public interface UserType extends ClassType<UserModel> {
	}

	public static final UserType TYPE = GWT.create(UserType.class);

	private Integer id;
	private String email;
	private String nickname;
	private String password;
	private String fullname;
	private String gender;
	private Date birthday;
	private String country;
	private String language;
	private String postcode;
	private String timezone;

	public UserModel() {
	}

	public UserModel(JSObject openid) {
		setEmail(openid.getString("email"));
		setNickname(openid.getString("nickname"));
		setFullname(openid.getString("fullname"));

		String gender = openid.getString("gender");

		if ("M".equals(gender)) {
			setGender("MALE");
		}
		else if ("F".equals(gender)) {
			setGender("FEMALE");
		}

		String dob = openid.getString("dob");

		if (dob != null) {
			DateConverter converter = new DateConverter("yyyy-MM-dd");
			setBirthday(converter.parse(dob));
		}

		String country = openid.getString("country");

		if (country != null) {
			country = country.toUpperCase();
			setCountry(country);
		}

		String language = openid.getString("language");

		if (language != null) {
			language = language.toLowerCase();
			setLanguage(language);
		}

		setPostcode(openid.getString("postcode"));
		setTimezone(openid.getString("timezone"));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}
