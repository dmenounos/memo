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
package memo.domain.test.service.ctx;

import junit.framework.Test;
import junit.framework.TestSuite;

import memo.domain.dao.model.core.Resource;
import memo.domain.dao.model.core.ctx.IContext;
import memo.domain.dao.service.core.ResourceService;
import memo.domain.dao.service.core.ctx.Context;
import memo.domain.dao.service.core.ctx.Name;
import memo.domain.test.BaseTest;

public class ContextTest extends BaseTest {

	private ResourceService resourceService;
	private Resource rootNode;
	private IContext context;

	public ContextTest(String testName) {
		super(testName);

		resourceService = getBean("resourceService");
	}

	@Override
	protected void setUp() {
		super.setUp();
		rootNode = resourceService.createRootNode();
		context = new Context(resourceService, rootNode);
	}

	@Override
	protected void tearDown() {
		resourceService.delete(rootNode.getId());
		super.tearDown();
	}

    public void testBasics() {
        log("Testing [bind, rebind, unbind, lookup, reserveCtx, traverseCtx]");
        
        log("Reserving subcontext 'My Documents / Images'");
        IContext imagesCtx = context.reserveCtx(new Name("My Documents", "Images"));
        
        log("Reserving subcontext 'My Documents / Videos'");
        IContext videosCtx = context.reserveCtx(new Name("My Documents", "Videos"));
        
        log("Binding several values");
//        imagesCtx.bind(new NameImpl("An image.jpg"), "file#1");
//        imagesCtx.bind(new NameImpl("Another image.jpg"), "file#2");
//        videosCtx.bind(new NameImpl("A video.mpg"), "file#3");
//        videosCtx.bind(new NameImpl("Another video.mpg"), "file#4");
//        ctx.bind(new NameImpl("My Projects", "aminet", "naming", "IName.java"), "file#5");
//        ctx.bind(new NameImpl("My Projects", "aminet", "naming", "IContext.java"), "file#6");
//        ctx.bind(new NameImpl("My Projects", "aminet", "naming", "NamingException.java"), "file#7");
        log("context: " + context);
        
//        log("Rebinding some values");
//        ctx.rebind(new NameImpl("My Documents", "Images", "Another image.jpg"), "file#8");
//        ctx.rebind(new NameImpl("My Documents", "Videos", "Another video.mpg"), "file#9");
//        log("ctx: " + ctx);
    }
    
    public static Test suite() {
        return new TestSuite(ContextTest.class);
    }
}
