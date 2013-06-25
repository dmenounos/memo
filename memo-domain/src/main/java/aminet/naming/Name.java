package aminet.naming;

/**
 * Logical path used in conjuction with Context.<br/>
 * <br/>
 * Name consists of an ordered sequence of elements each of which represents a 
 * node, either leaf or hub, within a hierarchical data structure.
 *
 * @author Dimitris Menounos
 */
public interface Name extends Iterable<Object> {
    
    /**
     * Retrieves the element in the position specified by the "pos" argument.
     *
     * @param pos the 0-based index of the element to retrieve. Must be in
     * the range [0,size()).
     * @throws IndexOutOfBoundsException if pos is outside the specified range.
     */
    Object getElement(int pos);
    
    /**
     * Returns the total number of elements in this name.
     */
    int getSize();
    
    /**
     * Creates a name whose elements consist of a prefix of the elements in
     * this name.
     *
     * @param pos the 0-based index of the element at which to stop. Must be
     * in the range [0,size()].
     * @throws IndexOutOfBoundsException if pos is outside the specified range.
     */
    Name createPrefix(int pos);
    
    /**
     * Creates a name whose elements consist of a suffix of the elements in
     * this name.
     *
     * @param pos the 0-based index of the element from which to start. Must be
     * in the range [0,size()].
     * @throws IndexOutOfBoundsException if pos is outside the specified range.
     */
    Name createSuffix(int pos);
    
    /**
     * Determines whether this name starts with a specified prefix.
     */
    boolean startsWith(Name name);
    
    /**
     * Determines whether this name ends with a specified suffix.
     */
    boolean endsWith(Name name);
}
