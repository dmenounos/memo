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
package memo.view.web.html.test.grid;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mojo.web.component.grid.GridComponent;

@Component
@Scope("prototype")
public class PersonGrid extends GridComponent<Person> {

	@Autowired
	private Database database;

	public PersonGrid(String id) {
		super(id);
	}

	@PostConstruct
	public void init() {
		logger.debug("INIT {}", getClass().getName());

		setTitle("Persons");

		Column column;

		column = addColumn();
		column.setName("firstName");
		column.setLabel("First Name");
		column.setWidth("50%");

		column = addColumn();
		column.setName("lastName");
		column.setLabel("Last Name");
		column.setWidth("50%");

		initRecords(database.getPersons());
	}
}
