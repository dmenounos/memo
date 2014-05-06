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
package memo.core.service.core.ns;

import java.util.Iterator;
import java.util.NoSuchElementException;

import memo.core.model.core.ns.IName;

/**
 * Abstract Name implementation.
 */
public abstract class NameBase implements IName {

	public abstract char getSeparator();

	@Override
	public IName createPrefix(int pos) {
		return clonePortion(0, pos);
	}

	@Override
	public IName createSuffix(int pos) {
		return clonePortion(pos, getSize());
	}

	@Override
	public boolean startsWith(IName name) {
		if (name == null) {
			return false;
		}

		boolean result = false;

		if (result = (name.getSize() <= getSize())) {
			Iterator<String> ita = iterator();
			Iterator<String> itb = name.iterator();

			while (result && itb.hasNext()) {
				result = ita.next().equals(itb.next());
			}
		}

		return result;
	}

	@Override
	public boolean endsWith(IName name) {
		if (name == null) {
			return false;
		}

		boolean result = false;

		if (result = (name.getSize() <= getSize())) {
			int thisPnt = getSize() - 1;
			int thatPnt = name.getSize() - 1;

			while (result && thatPnt >= 0) {
				result = getElement(thisPnt).equals(name.getElement(thatPnt));
				--thisPnt;
				--thatPnt;
			}
		}

		return result;
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			private int pnt = 0;

			public boolean hasNext() {
				return pnt < getSize();
			}

			public String next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				return getElement(pnt++);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = iterator();

		while (it.hasNext()) {
			sb.append(getSeparator());
			sb.append(it.next());
		}

		return sb.toString();
	}

	protected Name clonePortion(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("start [" + start + "] is greater than end [" + end + "]");
		}

		if (start < 0) {
			throw new IndexOutOfBoundsException("argument: start [" + start + "]");
		}

		if (end > getSize()) {
			throw new IndexOutOfBoundsException("argument: end [" + end + "]");
		}

		Name result = new Name();

		for (int i = start; i < end; i++) {
			result.append(getElement(i));
		}

		return result;
	}
}
