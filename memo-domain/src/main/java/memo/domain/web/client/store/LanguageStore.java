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
package memo.domain.web.client.store;

import com.google.gwt.core.client.GWT;

import mojo.gwt.data.client.HttpSource;
import mojo.gwt.data.client.XmlReader;

import memo.domain.web.client.model.LanguageModel;

public class LanguageStore extends HttpSource<LanguageModel> {

	public LanguageStore() {
		super(GWT.getModuleBaseURL() + "../app/misc/language");
		setReader(XmlReader.create(LanguageModel.TYPE, "Language"));
	}
}
