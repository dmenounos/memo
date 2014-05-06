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
package memo.core.test.service;

import junit.framework.Test;
import junit.framework.TestSuite;

import mojo.core.spec.Select;

import memo.core.model.core.Resource;
import memo.core.service.core.ResourceService;
import memo.core.test.BaseTest;

public class PostTest extends BaseTest {

	private ResourceService resourceService;

	private String postTitle;
	// private String postContent;

	public PostTest(String testName) {
		super(testName);

		resourceService = getBean("resourceService");
	}

	@Override
	protected void setUp() {
		super.setUp();

		postTitle = "Lorem Ipsum";

		StringBuilder sb = new StringBuilder();
		sb.append("Lorem ipsum dolor sit amet, consectetur adipisicing elit, ");
		sb.append("sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");
		sb.append("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
		sb.append("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ");
		sb.append("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

		// postContent = sb.substring(0, 255);
		// postContent = sb.toString();
	}

	public void testCRUD() {
		log("Creating post");
		Resource post = resourceService.insert(createPost());
		assertValidPost(post);

		log("Retrieving post #" + post.getId());
		Select<Resource> select = new Select<Resource>().join("createUser");
		Resource loadedPost = resourceService.select(select).unique();
		assertEqualPosts(post, loadedPost);

		log("Deleting post #" + post.getId());
		resourceService.delete(post.getId());
		post = resourceService.findById(post.getId());
		assertNull("not null post after delete", post);
	}

	private Resource createPost() {
		Resource post = new Resource();
		post.setCode(postTitle);
		return post;
	}

	protected static void assertValidPost(Resource post) {
		assertNotNull("null post", post);
		assertNotNull("null post.id", post.getId());
	}

	protected static void assertEqualPosts(Resource exp, Resource act) {
		if (exp == null) {
			assertNull("not null post", act);
		}
		else {
			assertNotNull("null post", act);
			assertEquals("incorrect post.id", exp.getId(), act.getId());
			assertEquals("incorrect post.code", exp.getCode(), act.getCode());
			BaseTest.assertEqualEntities(exp.getCreateUser(), act.getCreateUser());
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new PostTest("testCRUD"));
		return suite;
	}
}
