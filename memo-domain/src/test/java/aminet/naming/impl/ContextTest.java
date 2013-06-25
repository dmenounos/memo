package aminet.naming.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import aminet.naming.Context;

public class ContextTest extends TestCase {
    
    public ContextTest(String testName) {
        super(testName);
    }
    
    public void testBasics() {
        System.out.println("Testing [bind, rebind, unbind, lookup, reserveCtx, traverseCtx]");
        System.out.println();
        
        System.out.println("Creating context ctx");
        Context ctx = new ContextImpl();
        
        System.out.println("Reserving subcontext 'My Documents / Images'");
        Context imagesCtx = ctx.reserveCtx(new NameImpl("My Documents", "Images"));
        
        System.out.println("Reserving subcontext 'My Documents / Videos'");
        Context videosCtx = ctx.reserveCtx(new NameImpl("My Documents", "Videos"));
        
        System.out.println("Binding several values");
        imagesCtx.bind(new NameImpl("An image.jpg"), "file#1");
        imagesCtx.bind(new NameImpl("Another image.jpg"), "file#2");
        videosCtx.bind(new NameImpl("A video.mpg"), "file#3");
        videosCtx.bind(new NameImpl("Another video.mpg"), "file#4");
        ctx.bind(new NameImpl("My Projects", "aminet", "naming", "IName.java"), "file#5");
        ctx.bind(new NameImpl("My Projects", "aminet", "naming", "IContext.java"), "file#6");
        ctx.bind(new NameImpl("My Projects", "aminet", "naming", "NamingException.java"), "file#7");
        System.out.println("ctx: " + ctx);
        
        System.out.println("Rebinding some values");
        ctx.rebind(new NameImpl("My Documents", "Images", "Another image.jpg"), "file#8");
        ctx.rebind(new NameImpl("My Documents", "Videos", "Another video.mpg"), "file#9");
        System.out.println("ctx: " + ctx);
        
        System.out.println();
        System.out.println();
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(ContextTest.class);
        return suite;
    }
}
