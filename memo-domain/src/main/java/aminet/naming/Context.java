package aminet.naming;

import java.util.Set;

/**
 * Hierarchical data structure.<br/>
 * <br/>
 * Context(s) play a role analogous to that of directories in a filesystem,
 * each of wich can contain a number of registered logical names bound with
 * certain values - either subcontexts or other arbitrary objects.<br/>
 * <br/>
 * <strong>How to use</strong><br/>
 * <br/>
 * Context hierarchies can be created explicitly by using the <i>reserveCtx()</i> 
 * method or implicitly with the <i>bind()</i> and <i>rebind()</i> methods.<br/>
 * <br/>
 * For example, given a context "c" and a name "n" like "/programs/java", a 
 * <i>c.reserveCtx(n)</i> call would create a subcontext binded as "programs" 
 * inside "c" and another subcontext binded as "java" inside "programs".<br/>
 * <br/>
 * Likewise a <i>c.bind(n, obj)</i> or a <i>c.rebind(n, obj)</i> call would
 * implicitly create a subcontext binded as "programs" inside "c" and bind "obj"
 * as "java" inside "programs".<br/>
 * <br/>
 * It should be pointed out that <i>bind() and rebind()</i> do not treat other 
 * contexts passed as arguments specialy, rather everything gets bind as a 
 * common object.<br/>
 * <br/>
 * Context's methods can be grouped as such:
 * <ul>
 *   <li>Methods that will try to create any missing intermediate subcontexts 
 *       - and fail if they can't:
 *     <ul>
 *     <li>reserveCtx(), bind() and rebind()</li>
 *     </ul>
 *   </li>
 *   <li>Methods that require the necessary subcontexts to exist 
 *       - and fail if they don't:
 *     <ul>
 *     <li>traverseCtx(), unbind() and lookup()</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * @author Dimitris Menounos
 */
public interface Context {
    
    /**
     * Reserves the given name for contextual use by making sure that each of
     * it's elements is registered as a subcontext.
     *
     * @param name the path to use for the operation; must not be empty.
     * @return the subcontext related to the terminal name element.
     * @throws NamingException if any of the name elements has been bound as
     * a leaf rather as a hub.
     */
    Context reserveCtx(Name name);
    
    /**
     * Traverses the given name and returns the terminal subcontext.
     *
     * @param name the path to use for the operation; must not be empty.
     * @return the subcontext related to the terminal name element.
     * @throws NamingException if any of the name elements is not bound
     * <b><u>or</u></b> has been bound as a leaf rather as a hub.
     */
    Context traverseCtx(Name name);
    
    /**
     * Binds a name to an object.
     *
     * @param name the path to use for the operation; must not be empty.
     * @param obj the object to bind.
     * @throws NamingException if any of the intermediate name elements has
     * been bound as a leaf rather as a hub <b><u>or</u></b> if the terminal
     * element has already been bound.
     */
    void bind(Name name, Object obj);
    
    /**
     * Binds a name to an object.<br/>
     * <br/>
     * If the name has already been bound, the old value will be replaced with
     * the new one and returned as a result.
     *
     * @param name the path to use for the operation; must not be empty.
     * @param obj the object to bind.
     * @return the object previously related with the name.
     * @throws NamingException if any of the intermediate name elements has
     * been bound as a leaf rather as a hub.
     */
    Object rebind(Name name, Object obj);
    
    /**
     * Unbinds a name from the structure.<br/>
     * <br/>
     * This operation will succeed even if the terminal path element has not
     * been bound.
     *
     * @param name the path to use for the operation; must not be empty.
     * @throws NamingException if any of the intermediate name elements is not
     * bound <b><u>or</u></b> has been bound as a leaf rather as a hub.
     */
    void unbind(Name name);
    
    /**
     * Retrieves the object related to the specified name.
     *
     * @param name the path to use for the operation; must not be empty.
     * @return the object bound to path.
     * @throws NamingException if any of the intermediate path elements is not
     * bound <b><u>or</u></b> has been bound as a leaf rather as a hub.
     */
    Object lookup(Name name);
    
    /**
     * Retrieves the local names bound in this context.
     *
     * @return a collection with the local names in this context.
     */
    Set<?> list();
}
