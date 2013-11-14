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
package memo.domain.dao.service.core.ns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Concrete Name implementation.
 */
public class Name extends NameBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> elements;

	public Name() {
		elements = new ArrayList<String>();
	}

	public Name(String... elements) {
		if (elements == null) {
			throw new IllegalArgumentException("elements must not be empty");
		}

		this.elements = new ArrayList<String>(elements.length);

		for (String o : elements) {
			append(o);
		}
	}

	public String getElement(int pos) {
		return elements.get(pos);
	}

	public int getSize() {
		return elements.size();
	}

	public Iterator<String> iterator() {
		return elements.iterator();
	}

	public void append(String element) {
		if (element == null || element.isEmpty()) {
			throw new IllegalArgumentException("element must not be empty");
		}

		elements.add(element);
	}

	public void insert(int pos, String element) {
		if (element == null || element.isEmpty()) {
			throw new IllegalArgumentException("element must not be empty");
		}

		elements.add(pos, element);
	}

	public void remove(int pos) {
		elements.remove(pos);
	}

	@Override
	public char getSeparator() {
		return '/';
	}
}
