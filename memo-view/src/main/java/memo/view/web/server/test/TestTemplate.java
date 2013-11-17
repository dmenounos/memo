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
package memo.view.web.server.test;

import mojo.web.util.SpringUtils;

import memo.view.web.server.html.MainTemplate;
import memo.view.web.server.test.grid.PersonGrid;
import memo.view.web.server.test.grid.PetGrid;

public class TestTemplate extends MainTemplate {

	private PersonGrid personGrid;
	private PetGrid petGrid;

	protected TestTemplate(String id) {
		super(id);

		personGrid = SpringUtils.getComponent(PersonGrid.class);
		setPersonGrid(personGrid);
		add(personGrid);

		petGrid = SpringUtils.getComponent(PetGrid.class);
		setPetGrid(petGrid);
		add(petGrid);
	}

	public PersonGrid getPersonGrid() {
		return personGrid;
	}

	public void setPersonGrid(PersonGrid personGrid) {
		this.personGrid = personGrid;
	}

	public PetGrid getPetGrid() {
		return petGrid;
	}

	public void setPetGrid(PetGrid petGrid) {
		this.petGrid = petGrid;
	}
}
