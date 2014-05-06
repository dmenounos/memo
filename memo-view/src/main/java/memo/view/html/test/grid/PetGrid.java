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
package memo.view.html.test.grid;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mojo.view.component.grid.GridComponent;

@Component
@Scope("prototype")
public class PetGrid extends GridComponent<Pet> {

	@Autowired
	private Database database;

	@PostConstruct
	public void init() {
		logger.debug("INIT {}", getClass().getName());

		setTitle("Pets");

		Column column;

		column = addColumn();
		column.setName("name");
		column.setLabel("Name");
		column.setWidth("50%");

		column = addColumn();
		column.setName("owner");
		column.setLabel("Owner");
		column.setWidth("50%");
		column.setRenderer(new Renderer() {

			@Override
			public String render(Object obj) {
				Pet pet = (Pet) obj;
				Person owner = pet.getOwner();

				if (owner != null) {
					return owner.getFirstName() + " " + owner.getLastName();
				}

				return "-";
			}
		});

		initRecords(database.getPets());
	}
}
