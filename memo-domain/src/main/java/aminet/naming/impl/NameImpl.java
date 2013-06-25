package aminet.naming.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Concrete Name implementation.
 *
 * @author Dimitris Menounos
 */
public class NameImpl extends NameBase implements Serializable {
    private List<Object> elements;
    
    public NameImpl() {
        elements = new ArrayList<Object>();
    }
    
    public NameImpl(Object... elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements must not be empty");
        }
        
        this.elements = new ArrayList<Object>(elements.length);
        
        for (Object o : elements) {
            append(o);
        }
    }
    
    public Object getElement(int pos) {
        if (pos < 0 || pos >= getSize()) {
            throw new IndexOutOfBoundsException("argument: pos [" + pos + "]");
        }
        
        return elements.get(pos);
    }
    
    public int getSize() {
        return elements.size();
    }
    
    public Iterator<Object> iterator() {
        return elements.iterator();
    }
    
    public void append(Object element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be empty");
        }
        
        elements.add(element);
    }
    
    public void insert(int pos, Object element) {
        if (pos < 0 || pos > getSize()) {
            throw new IndexOutOfBoundsException("argument: pos [" + pos + ']');
        }
        
        if (element == null) {
            throw new IllegalArgumentException("element must not be empty");
        }
        
        elements.add(pos, element);
    }
    
    public void remove(int pos) {
        if (pos < 0 || pos >= getSize()) {
            throw new IndexOutOfBoundsException("argument: pos [" + pos + ']');
        }
        
        elements.remove(pos);
    }
}
