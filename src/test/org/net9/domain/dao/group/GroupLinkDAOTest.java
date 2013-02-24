/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.group;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.group.GroupLink;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class GroupLinkDAOTest extends DAOTestBase {

	GroupLinkDAO instance;

	@Before
	public void setUp() {
		instance = (GroupLinkDAO) Guice.createInjector(new ServiceModule())
				.getInstance(GroupLinkDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<GroupLink> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<GroupLink> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupLink model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<GroupLink> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupLink model = new GroupLink();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<GroupLink> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<GroupLink> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupLink model = list.get(0);
		model.setLabel("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getLabel().equals(model.getLabel()));
	}

}