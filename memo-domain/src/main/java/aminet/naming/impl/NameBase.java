package aminet.naming.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import aminet.naming.Name;

/**
 * Abstract Name implementation.
 *
 * @author Dimitris Menounos
 */
public abstract class NameBase implements Name {
    
    public Name createPrefix(int pos) {
        return clonePortion(0, pos);
    }
    
    public Name createSuffix(int pos) {
        return clonePortion(pos, getSize());
    }
    
    public boolean startsWith(Name name) {
        if (name == null) {
            return false;
        }
        
        boolean result = false;
        
        if (result = (name.getSize() <= getSize())) {
            Iterator<Object> ita = iterator();
            Iterator<Object> itb = name.iterator();
            
            while (result && itb.hasNext()) {
                result = ita.next().equals(itb.next());
            }
        }
        
        return result;
    }
    
    public boolean endsWith(Name name) {
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
    
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            int pnt = 0;
            
            public boolean hasNext() {
                return pnt < getSize();
            }
            
            public Object next() {
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
    
    public String toString() {
        Iterator<Object> it = iterator();
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        
        for (int x = 0; x < getSize(); x++) {
            sb.append(it.next());
            
            if (x < getSize() -1) {
                sb.append(", ");
            }
        }
        
        sb.append(']');
        return sb.toString();
    }
    
    private NameImpl clonePortion(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("start [" + start + "] is greater than end [" + end + "]");
        }
        
        if (start < 0) {
            throw new IndexOutOfBoundsException("argument: start [" + start + "]");
        }
        
        if (end > getSize()) {
            throw new IndexOutOfBoundsException("argument: end [" + end + "]");
        }
        
        NameImpl result = new NameImpl();
        
        for (int i = start; i < end; i++) {
            result.append(getElement(i));
        }
        
        return result;
    }
}
