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
package memo.domain.test.service.ctx;

import junit.framework.Test;
import junit.framework.TestSuite;

import memo.domain.dao.model.core.ctx.IName;
import memo.domain.dao.service.core.ctx.Name;
import memo.domain.test.BaseTest;

public class NameTest extends BaseTest {

	public NameTest(String testName) {
		super(testName);
	}

	public void testBasics() {
		log("Testing [append, insert, remove, getElement, getSize, iterator]");

		log("Creating name na");
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

		log("Creating name nb");
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

		assertEquals("03", na.getElement(3));
		assertEquals(5, na.getSize());
	}

	public void testRest() {
		log("Testing [createPrefix, createSuffix, startsWith, endsWith]");

		log("Creating name na");
		Name na = new Name("00", "01", "02", "03", "04");
		log(na.toString());

		log("Creating prefix nb from na");
		IName nb = na.createPrefix(2);
		log(nb.toString());

		if (!na.startsWith(nb)) {
			fail("nb is not a prefix of na.");
		}

		log("Creating suffix nb from na");
		nb = na.createSuffix(2);
		log(nb.toString());

		if (!na.endsWith(nb)) {
			fail("nb is not a suffix of na.");
		}
	}

	public static Test suite() {
		return new TestSuite(NameTest.class);
	}
}
