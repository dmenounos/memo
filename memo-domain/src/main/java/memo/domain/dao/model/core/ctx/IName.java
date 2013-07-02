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
package memo.domain.dao.model.core.ctx;

/**
 * Logical path used in conjunction with Context.
 * <p>
 * Name consists of an ordered sequence of elements each of which represents a
 * node, either leaf or hub, within a hierarchical data structure.
 */
public interface IName extends Iterable<String> {

	/**
	 * Retrieves the element in the position specified by the "pos" argument.
	 * 
	 * @param pos the index of the element to retrieve. Must be in the range
	 * [0,size()).
	 */
	String getElement(int pos);

	/**
	 * Returns the total number of elements in this name.
	 */
	int getSize();

	/**
	 * Creates a name whose elements consist of a prefix of the elements in this
	 * name.
	 * 
	 * @param pos the index of the element at which to stop. Must be in the
	 * range [0,size()].
	 */
	IName createPrefix(int pos);

	/**
	 * Creates a name whose elements consist of a suffix of the elements in this
	 * name.
	 * 
	 * @param pos the index of the element from which to start. Must be in the
	 * range [0,size()].
	 */
	IName createSuffix(int pos);

	/**
	 * Determines whether this name starts with a specified prefix.
	 */
	boolean startsWith(IName name);

	/**
	 * Determines whether this name ends with a specified suffix.
	 */
	boolean endsWith(IName name);
}
