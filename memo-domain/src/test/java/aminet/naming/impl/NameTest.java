package aminet.naming.impl;

import junit.framework.*;

import aminet.naming.Name;
import aminet.naming.impl.NameImpl;

public class NameTest extends TestCase {
    
    public NameTest(String testName) {
        super(testName);
    }
    
    public void testBasics() {
        System.out.println("Testing [append, insert, remove, getElement, getSize, iterator]");
        System.out.println();
        
        System.out.println("Creating name na");
        NameImpl na = new NameImpl();
        na.append("00");
        na.append("01");
        na.append("04");
        na.insert(2, "02");
        na.insert(3, "03");
        System.out.println("na: " + na);
        assertEquals("03", na.getElement(3));
        assertEquals(5, na.getSize());
        
        System.out.println("Creating name nb");
        NameImpl nb = new NameImpl();
        nb.append("00");
        nb.append("01");
        nb.append("02");
        nb.append("error");
        nb.append("03");
        nb.append("04");
        nb.append("error");
        nb.remove(3);
        nb.remove(5);
        System.out.println("nb: " + nb);
        assertEquals("03", na.getElement(3));
        assertEquals(5, na.getSize());
        
        System.out.println();
        System.out.println();
    }
    
    public void testRest() {
        System.out.println("Testing [createPrefix, createSuffix, startsWith, endsWith]");
        System.out.println();
        
        System.out.println("Creating name na");
        NameImpl na = new NameImpl("00", "01", "02", "03", "04");
        System.out.println("na: " + na);
        
        System.out.println("Creating prefix nb from na");
        Name nb = na.createPrefix(2);
        System.out.println("nb: " + nb);
        
        if (!na.startsWith(nb)) {
            fail("nb is not a prefix of na.");
        }
        
        System.out.println("Creating suffix nb from na");
        nb = na.createSuffix(2);
        System.out.println("nb: " + nb);
        
        if (!na.endsWith(nb)) {
            fail("nb is not a suffix of na.");
        }
        
        System.out.println();
        System.out.println();
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(NameTest.class);
        return suite;
    }
}
