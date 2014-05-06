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
package memo.view.html.login.prompt;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mojo.view.util.SpringUtils;

import memo.view.html.base.BaseTemplate;

@Component
@Scope("prototype")
public class LoginPromptContainer extends BaseTemplate {

	private SimpleLoginComponent simpleLoginComponent;
	private OpenIDLoginComponent openIDLoginComponent;

	public LoginPromptContainer() {
		setTitle("Login or Register");

		simpleLoginComponent = SpringUtils.getComponent(SimpleLoginComponent.class);
		openIDLoginComponent = SpringUtils.getComponent(OpenIDLoginComponent.class);
	}

	public SimpleLoginComponent getSimpleLoginComponent() {
		return simpleLoginComponent;
	}

	public void setSimpleLoginComponent(SimpleLoginComponent simpleLoginComponent) {
		this.simpleLoginComponent = simpleLoginComponent;
	}

	public OpenIDLoginComponent getOpenIDLoginComponent() {
		return openIDLoginComponent;
	}

	public void setOpenIDLoginComponent(OpenIDLoginComponent openIDLoginComponent) {
		this.openIDLoginComponent = openIDLoginComponent;
	}
}
