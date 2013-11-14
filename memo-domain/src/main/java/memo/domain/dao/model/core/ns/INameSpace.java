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
package memo.domain.dao.model.core.ns;

import java.util.Set;

/**
 * Hierarchical data structure.
 * <p>
 * INameSpace(s) play a role analogous to that of directories in a filesystem,
 * each of which can contain a number of registered logical names bound with
 * certain values - either subcontexts or other arbitrary objects.
 * <p>
 * <strong>How to use</strong>
 * <p>
 * INameSpace hierarchies can be created explicitly by using the
 * <i>createPath()</i> method or implicitly with the <i>rebind()</i> method.
 * <p>
 * INameSpace's methods can be grouped as such:
 * <ul>
 * <li>Methods that will try to create any missing intermediate sub-contexts -
 * and fail if they can't:
 * <ul>
 * <li>createPath() and rebind()</li>
 * </ul>
 * </li>
 * <li>Methods that require the necessary sub-contexts to exist - and fail if
 * they don't:
 * <ul>
 * <li>lookupPath(), unbind() and lookup()</li>
 * </ul>
 * </li>
 * </ul>
 */
public interface INameSpace {

	/**
	 * Reserves the given name by making sure that each of it's elements is
	 * registered as a subcontext.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the subcontext related to the terminal name element.
	 * @throws RuntimeException if any of the name elements has been bound as a
	 * leaf rather as a hub.
	 */
	INameSpace createPath(IName name);

	/**
	 * Traverses the given name and returns the terminal subcontext.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the subcontext related to the terminal name element.
	 * @throws RuntimeException if any of the name elements is not bound or has
	 * been bound as a leaf rather as a hub.
	 */
	INameSpace lookupPath(IName name);

	/**
	 * Binds a name to an object.
	 * <p>
	 * If the name has already been bound, the old value will be replaced with
	 * the new one and returned as a result.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @param obj the object to bind.
	 * @return the object previously related with the name.
	 * @throws RuntimeException if any of the intermediate name elements has
	 * been bound as a leaf rather as a hub.
	 */
	Object rebind(IName name, Object obj);

	/**
	 * Unbinds a name from the structure.
	 * <p>
	 * This operation will succeed even if the terminal path element has not
	 * been bound.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the object previously related with the name.
	 * @throws RuntimeException if any of the intermediate name elements is not
	 * bound or has been bound as a leaf rather as a hub.
	 */
	Object unbind(IName name);

	/**
	 * Retrieves the object related to the specified name.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the object bound to path.
	 * @throws RuntimeException if any of the intermediate path elements is not
	 * bound or has been bound as a leaf rather as a hub.
	 */
	Object lookup(IName name);

	/**
	 * Retrieves the local names bound in this context.
	 * 
	 * @return a collection with the local names in this context.
	 */
	Set<?> list();

	/**
	 * Prints a debug string.
	 */
	String toTreeString();
}
