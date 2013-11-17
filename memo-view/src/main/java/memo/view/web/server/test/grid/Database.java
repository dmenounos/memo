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
package memo.view.web.server.test.grid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public final class Database {

	public final List<Person> persons;
	public final List<Pet> pets;

	public Database() {
		persons = new ArrayList<Person>();
		pets = new ArrayList<Pet>();

		Person p;

		p = initPerson("Donald", "Duck");
		p = initPerson("Scrooge", "McDuck");

		p = initPerson("Mikey", "Mouse");
		initPet("Pluto", p);

		p = initPerson("Jon", "Arbuckle");
		initPet("Garfield", p);
	}

	private Person initPerson(String firstName, String lastName) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		persons.add(person);
		return person;
	}

	private Pet initPet(String name, Person owner) {
		Pet pet = new Pet();
		pet.setName(name);
		pet.setOwner(owner);
		owner.getPets().add(pet);
		pets.add(pet);
		return pet;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public List<Pet> getPets() {
		return pets;
	}
}
