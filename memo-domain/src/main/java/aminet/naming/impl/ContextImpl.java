package aminet.naming.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import aminet.naming.Name;
import aminet.naming.Context;
import aminet.naming.NamingException;

/**
 * Concrete Context implementation.
 *
 * @author Dimitris Menounos
 */
public class ContextImpl implements Context {
    private Map<Object, ContextImpl> contexts = new HashMap<Object, ContextImpl>();
    private Map<Object, Object> objects = new HashMap<Object, Object>();
    
    public Context reserveCtx(Name name) {
        if (name == null || name.getSize() == 0) {
            throw new IllegalArgumentException("name must not be empty");
        }
        
        ContextImpl ctx = this; // context cursor
        ContextImpl subctx = ctx; // sub-context
        
        for (Object ne : name) {
            if (ne == null) {
                throw new IllegalArgumentException("name must not be empty - element [" + ne + "]");
            }
            
            subctx = ctx.contexts.get(ne);
            
            if (subctx == null) {
                if (objects.get(ne) != null) {
                    throw new NamingException("Element [" + ne + "] from name [" + name + "] has been bound as a leaf rather as a hub.");
                }
                
                ctx.contexts.put(ne, subctx = new ContextImpl());
            }
            
            ctx = subctx;
        }
        
        return ctx;
    }
    
    public Context traverseCtx(Name name) {
        if (name == null || name.getSize() == 0) {
            throw new IllegalArgumentException("name must not be empty");
        }
        
        ContextImpl ctx = this; // context cursor
        ContextImpl subctx = ctx; // sub-context
        
        for (Object ne : name) {
            if (ne == null) {
                throw new IllegalArgumentException("name must not be empty - element [" + ne + ']');
            }
            
            subctx = ctx.contexts.get(ne);
            
            if (subctx == null) {
                if (objects.get(ne) != null) {
                    throw new NamingException("Element [" + ne + "] from name [" + name + "] has been bound as a leaf rather as a hub.");
                }
                
                throw new NamingException("Element [" + ne + "] from name [" + name + "] has not been bound.");
            }
            
            ctx = subctx;
        }
        
        return ctx;
    }
    
    public void bind(Name name, Object obj) {
        if (name == null || name.getSize() == 0) {
            throw new IllegalArgumentException("name must not be empty");
        }
        
        if (name.getSize() > 1) {
            Name prefix = name.createPrefix(name.getSize() - 1);
            Context ctx = reserveCtx(prefix);
            Name suffix = name.createSuffix(name.getSize() - 1);
            ctx.bind(suffix, obj);
        } else {
            if (lookup(name) != null) {
                throw new NamingException("Local name [" + name.getElement(0) + "] in context [" + this + "] has already been bound.");
            }
            
            objects.put(name.getElement(0), obj);
        }
    }
    
    public Object rebind(Name name, Object obj) {
        if (name == null || name.getSize() == 0) {
            throw new IllegalArgumentException("name must not be empty");
        }
        
        Object result = null;
        
        if (name.getSize() > 1) {
            Name prefix = name.createPrefix(name.getSize() -1);
            Context ctx = reserveCtx(prefix);
            Name suffix = name.createSuffix(name.getSize() -1);
            result = ctx.rebind(suffix, obj);
        } else {
            result = lookup(name);
            objects.put(name.getElement(0), obj);
        }
        
        return result;
    }
    
    public void unbind(Name name) {
        if (name == null || name.getSize() == 0) {
            throw new IllegalArgumentException("name must not be empty");
        }
        
        if (name.getSize() > 1) {
            Name prefix = name.createPrefix(name.getSize() -1);
            Context ctx = traverseCtx(prefix);
            Name suffix = name.createSuffix(name.getSize() -1);
            ctx.unbind(suffix);
        } else {
            Object ne = name.getElement(0);
            
            if (contexts.remove(ne) == null) {
                objects.remove(ne);
            }
        }
    }
    
    public Object lookup(Name name) {
        if (name == null || name.getSize() == 0) {
            throw new IllegalArgumentException("name must not be empty");
        }
        
        Object result = null;
        
        if (name.getSize() > 1) {
            Name prefix = name.createPrefix(name.getSize() -1);
            Context ctx = traverseCtx(prefix);
            Name suffix = name.createSuffix(name.getSize() -1);
            result = ctx.lookup(suffix);
        } else {
            Object ne = name.getElement(0);
            
            if ((result = contexts.get(ne)) == null) {
                result = objects.get(ne);
            }
        }
        
        return result;
    }
    
    public Set<?> list() {
        Set<Object> result = new HashSet<Object>();
        result.addAll(contexts.keySet());
        result.addAll(objects.keySet());
        return result;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Frame> stack = new LinkedList<Frame>(); // stack container
        Frame frame = new Frame(this); // frame cursor
        stack.add(frame);
        Entry<?, ?> e = null; // tmp
        
        hierarchy:
            while (!stack.isEmpty()) {
            frame = stack.getLast();
            
            if (frame.x == 0) {
                sb.append('[');
            } else if (frame.x < frame.size) {
                sb.append(", ");
            }
            
            while (frame.x++ < frame.size) {
                if (frame.x <= frame.sizeCtx) {
                    e = frame.itCtx.next();
                    sb.append(e.getKey()).append("=");
                    frame = new Frame((ContextImpl) e.getValue());
                    stack.addLast(frame);
                    continue hierarchy;
                } else {
                    e = frame.itObj.next();
                    sb.append(e.getKey()).append("=").append(e.getValue());
                }
                
                if (frame.x < frame.size) {
                    sb.append(", ");
                }
            }
            
            sb.append(']');
            stack.removeLast();
            }
        
        return sb.toString();
    }
    
    private static class Frame {
        ContextImpl ctx;
        int sizeCtx;
        int size;
        Iterator<Entry<Object, ContextImpl>> itCtx;
        Iterator<Entry<Object, Object>> itObj;
        int x;
        
        Frame(ContextImpl ctx) {
            this.ctx = ctx;
            sizeCtx = ctx.contexts.size();
            size = sizeCtx + ctx.objects.size();
            itCtx = ctx.contexts.entrySet().iterator();
            itObj = ctx.objects.entrySet().iterator();
        }
    }
}
