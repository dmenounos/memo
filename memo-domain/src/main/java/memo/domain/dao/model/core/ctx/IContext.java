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

import java.util.Set;

/**
 * Hierarchical data structure.
 * <p>
 * Context(s) play a role analogous to that of directories in a filesystem, each
 * of wich can contain a number of registered logical names bound with certain
 * values - either subcontexts or other arbitrary objects.
 * <p>
 * <strong>How to use</strong>
 * <p>
 * Context hierarchies can be created explicitly by using the
 * <i>reserveCtx()</i> method or implicitly with the <i>bind()</i> and
 * <i>rebind()</i> methods.
 * <p>
 * For example, given a context "c" and a name "n" like "/programs/java", a
 * <i>c.reserveCtx(n)</i> call would create a subcontext binded as "programs"
 * inside "c" and another subcontext binded as "java" inside "programs".
 * <p>
 * Likewise a <i>c.bind(n, obj)</i> or a <i>c.rebind(n, obj)</i> call would
 * implicitly create a subcontext binded as "programs" inside "c" and bind "obj"
 * as "java" inside "programs".
 * <p>
 * It should be pointed out that <i>bind() and rebind()</i> do not treat other
 * contexts passed as arguments specialy, rather everything gets bind as a
 * common object.
 * <p>
 * Context's methods can be grouped as such:
 * <ul>
 * <li>Methods that will try to create any missing intermediate subcontexts -
 * and fail if they can't:
 * <ul>
 * <li>reserveCtx(), bind() and rebind()</li>
 * </ul>
 * </li>
 * <li>Methods that require the necessary subcontexts to exist - and fail if
 * they don't:
 * <ul>
 * <li>traverseCtx(), unbind() and lookup()</li>
 * </ul>
 * </li>
 * </ul>
 */
public interface IContext {

	/**
	 * Reserves the given name for contextual use by making sure that each of
	 * it's elements is registered as a subcontext.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the subcontext related to the terminal name element.
	 * @throws RuntimeException if any of the name elements has been bound as a
	 * leaf rather as a hub.
	 */
	IContext reserveCtx(IName name);

	/**
	 * Traverses the given name and returns the terminal subcontext.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the subcontext related to the terminal name element.
	 * @throws RuntimeException if any of the name elements is not bound
	 * <b><u>or</u></b> has been bound as a leaf rather as a hub.
	 */
	IContext traverseCtx(IName name);

	/**
	 * Binds a name to an object.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @param obj the object to bind.
	 * @throws RuntimeException if any of the intermediate name elements has
	 * been bound as a leaf rather as a hub <b><u>or</u></b> if the terminal
	 * element has already been bound.
	 */
	void bind(IName name, Object obj);

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
	 * @throws RuntimeException if any of the intermediate name elements is not
	 * bound <b><u>or</u></b> has been bound as a leaf rather as a hub.
	 */
	void unbind(IName name);

	/**
	 * Retrieves the object related to the specified name.
	 * 
	 * @param name the path to use for the operation; must not be empty.
	 * @return the object bound to path.
	 * @throws RuntimeException if any of the intermediate path elements is not
	 * bound <b><u>or</u></b> has been bound as a leaf rather as a hub.
	 */
	Object lookup(IName name);

	/**
	 * Retrieves the local names bound in this context.
	 * 
	 * @return a collection with the local names in this context.
	 */
	Set<?> list();
}
