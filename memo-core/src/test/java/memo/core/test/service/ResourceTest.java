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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import mojo.core.Repository;
import mojo.core.spec.Delete;
import mojo.core.spec.Insert;

import memo.core.model.core.Permission;
import memo.core.model.core.Resource;
import memo.core.service.core.PermissionService;
import memo.core.service.core.ResourceService;
import memo.core.test.BaseTest;

public class ResourceTest extends BaseTest {

	private PermissionService permissionService;
	private ResourceService resourceService;
	private Resource rootNode;

	protected ResourceTest(String name) {
		super(name);

		permissionService = getBean("permissionService");
		resourceService = getBean("resourceService");
	}

	@Override
	protected void setUp() {
		super.setUp();

		rootNode = new Resource();
		rootNode.setCode("root");

		TransactionTemplate template = createTransactionTemplate();
		template.execute(new TransactionCallback<Object>() {

			public Object doInTransaction(TransactionStatus status) {
				Repository<Resource> resourceRepository = resourceService.getRepository();
				resourceRepository.insert(new Insert<Resource>(rootNode));
				return null;
			}
		});
	}

	@Override
	protected void tearDown() {
		TransactionTemplate template = createTransactionTemplate();
		template.execute(new TransactionCallback<Object>() {

			public Object doInTransaction(TransactionStatus status) {
				Repository<Resource> resourceRepository = resourceService.getRepository();
				resourceRepository.delete(new Delete<Resource>(rootNode.getId()));
				return null;
			}
		});

		super.tearDown();
	}

	public void testRootCRUD() {
		log("Creating rootNode");
		Resource node = new Resource();
		node.setCode("alt-root-node");
		resourceService.insert(node);

		log("Retrieving rootNode #" + node.getId());
		Resource loadedNode = resourceService.findById(node.getId());
		assertEqualNodes(node, loadedNode);

		log("Modifying rootNode #" + node.getId());
		node.setCode("modified-alt-root-node");
		Resource updatedNode = resourceService.update(node);
		assertEqualNodes(node, updatedNode);

		log("Deleting rootNode #" + node.getId());
		resourceService.delete(node.getId());
		node = resourceService.findById(node.getId());
		assertNull("not null node after delete", node);
	}

	public void testNodeCRUD() {
		log("Creating node");
		Resource node = rootNode.createChildNode("child-node");
		node = resourceService.insert(node);
		assertValidNode(node);

		log("Retrieving node #" + node.getId());
		Resource loadedNode = resourceService.findById(node.getId());
		assertEqualNodes(node, loadedNode);

		log("Modifying node #" + node.getId());
		node.setCode("modified-child-node");
		Resource updatedNode = resourceService.update(node);
		assertEqualNodes(node, updatedNode);

		log("Deleting node #" + node.getId());
		resourceService.delete(node.getId());
		node = resourceService.findById(node.getId());
		assertNull("not null node after delete", node);
	}

	public void testNodeRules() {
		log("Creating folder without permissions");
		Resource folder = rootNode.createChildNode("folder");
		folder = resourceService.insert(folder);

		log("Creating file without permissions");
		Resource file = folder.createChildNode("file");
		file = resourceService.insert(file);

		log("Creating file permission: none");
		Permission fileRule = file.createPermission();
		fileRule.setUserRole(getDefaultUserRole(0));
		fileRule.setPermission(Permission.NONE);
		permissionService.insert(fileRule);

		Exception exception = null;

		try {
			log("Attempting to modify file #" + file.getId());
			resourceService.update(file);
		}
		catch (Exception e) {
			exception = e;
		}
		finally {
			assertNotNull("file modification shouldn't be allowed", exception);
			exception = null;
		}

		try {
			log("Attempting to modify folder #" + folder.getId());
			resourceService.update(folder);
		}
		catch (Exception e) {
			exception = e;
		}
		finally {
			if (exception != null) logger.error("ERROR", exception);
			assertNull("folder modification should be allowed", exception);
			exception = null;
		}

		try {
			log("Attempting to delete file #" + file.getId());
			resourceService.delete(file.getId());
		}
		catch (Exception e) {
			exception = e;
		}
		finally {
			assertNotNull("file deletion shouldn't be allowed", exception);
			exception = null;
		}

		try {
			log("Attempting to delete folder #" + folder.getId());
			resourceService.delete(folder.getId());
		}
		catch (Exception e) {
			exception = e;
		}
		finally {
			if (exception != null) logger.error("ERROR", exception);
			assertNull("folder deletion should be allowed", exception);
			exception = null;
		}
	}

	/**
	 * Experimental code ...
	 */
	public void testIntrospection() {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(Resource.class);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			log("Bean: " + Resource.class);

			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String read = descriptor.getReadMethod() != null ? "yes" : "no";
				String write = descriptor.getWriteMethod() != null ? "yes" : "no";
				log("Property: " + descriptor.getName() + ", read: " + read + ", write: " + write);
			}
		}
		catch (IntrospectionException ex) {
			log(ex.getMessage());
		}
	}

	protected static void assertValidNode(Resource node) {
		assertNotNull("null node", node);
		assertNotNull("null node.id", node.getId());
	}

	protected static void assertEqualNodes(Resource exp, Resource act) {
		if (exp == null) {
			assertNull("not null node", act);
		}
		else {
			assertNotNull("null node", act);
			assertEquals("incorrect node.id", exp.getId(), act.getId());
			assertEquals("incorrect node.code", exp.getCode(), act.getCode());
			assertEqualEntities(exp.getParentNode(), act.getParentNode());
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new ResourceTest("testRootCRUD"));
		suite.addTest(new ResourceTest("testNodeCRUD"));
		suite.addTest(new ResourceTest("testNodeRules"));
		// suite.addTest(new NodeTest("testIntrospection"));
		return suite;
	}
}
