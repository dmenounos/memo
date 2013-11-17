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
package memo.core.test.service.ns;

import junit.framework.Test;
import junit.framework.TestSuite;

import memo.core.dao.model.core.ns.IName;
import memo.core.dao.service.core.ns.Name;
import memo.core.test.BaseTest;

public class NameTest extends BaseTest {

	private Name name;

	public NameTest(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() {
		super.setUp();
		name = new Name("00", "01", "02", "03", "04");
	}

	public void testAppendInsert() {
		log("Creating name");
		Name na = new Name();
		na.append("00");
		na.append("01");
		na.append("04");
		na.insert(2, "02");
		na.insert(3, "03");
		log(na.toString());

		assertEquals("02", na.getElement(2));
		assertEquals("03", na.getElement(3));
		assertEquals(5, na.getSize());
	}

	public void testAppendRemove() {
		log("Creating name");
		Name nb = new Name();
		nb.append("00");
		nb.append("01");
		nb.append("02");
		nb.append("error");
		nb.append("03");
		nb.append("04");
		nb.append("error");
		nb.remove(3);
		nb.remove(5);
		log(nb.toString());

		assertEquals("03", name.getElement(3));
		assertEquals(5, name.getSize());
	}

	public void testCreatePrefix() {
		log("Creating prefix from name");
		IName nb = name.createPrefix(2);
		log(nb.toString());

		if (!name.startsWith(nb)) {
			fail("nb is not a prefix of na.");
		}
	}

	public void testCreateSuffix() {
		log("Creating suffix from name");
		IName nb = name.createSuffix(2);
		log(nb.toString());

		if (!name.endsWith(nb)) {
			fail("nb is not a suffix of na.");
		}
	}

	public static Test suite() {
		return new TestSuite(NameTest.class);
	}
}
