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
package memo.domain.test.service.ns;

import junit.framework.Test;
import junit.framework.TestSuite;

import memo.domain.dao.model.core.MountPoint;
import memo.domain.dao.model.core.Resource;
import memo.domain.dao.model.core.ns.INameSpace;
import memo.domain.dao.service.core.ResourceService;
import memo.domain.dao.service.core.ns.Name;
import memo.domain.dao.service.core.ns.NameSpace;
import memo.domain.test.BaseTest;

public class NameSpaceTest extends BaseTest {

	private ResourceService resourceService;
	private Resource rootNode;
	private INameSpace ns;

	public NameSpaceTest(String testName) {
		super(testName);

		resourceService = getBean("resourceService");
	}

	@Override
	protected void setUp() {
		super.setUp();
		rootNode = resourceService.createRootNode();
		ns = new NameSpace(resourceService, rootNode, null);
	}

	@Override
	protected void tearDown() {
		resourceService.delete(rootNode.getId());
		super.tearDown();
	}

	public void testBasics() {
		log("Reserving: /My Documents/Images");
		INameSpace imagesCtx = ns.createPath(new Name("My Documents", "Images"));
		log("imagesCtx: " + imagesCtx);

		log("Reserving: /My Documents/Videos");
		INameSpace videosCtx = ns.createPath(new Name("My Documents", "Videos"));
		log("videosCtx: " + videosCtx);

		log("Binding several values");
		imagesCtx.rebind(new Name("an image.jpg"),      new MockPoint("file#1"));
		imagesCtx.rebind(new Name("another image.jpg"), new MockPoint("file#2"));
		videosCtx.rebind(new Name("a video.mpg"),       new MockPoint("file#3"));
		videosCtx.rebind(new Name("another video.mpg"), new MockPoint("file#4"));
		ns.rebind(new Name("My Projects", "Foo", "Bar", "koko"), new MockPoint("file#5"));
		ns.rebind(new Name("My Projects", "Foo", "Bar", "lolo"), new MockPoint("file#6"));

		ns.rebind(new Name("My Documents", "Images", "another image.jpg"), new MockPoint("new-file#8"));
		ns.rebind(new Name("My Documents", "Videos", "another video.mpg"), new MockPoint("new-file#9"));

		System.out.println(ns.toTreeString());
	}

	public static class MockPoint implements MountPoint {

		private Resource resource;
		private String name;

		public MockPoint(String name) {
			this.name = name;
		}

		@Override
		public Resource getResource() {
			return resource;
		}

		@Override
		public void setResource(Resource resource) {
			this.resource = resource;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static Test suite() {
		return new TestSuite(NameSpaceTest.class);
	}
}
