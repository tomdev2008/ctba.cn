/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.gallery.ImageGalleryDAO;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class ImageGalleryDAOTest extends DAOTestBase {

	ImageGalleryDAO instance;

	@Before
	public void setUp() {
		instance = (ImageGalleryDAO) Guice.createInjector(new ServiceModule())
				.getInstance(ImageGalleryDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ImageGallery> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		ImageGallery result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<ImageGallery> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

	@Test
	public void testRemove() {
		List<ImageGallery> list = instance.findAll();
		assertTrue(list.size() > 0);
		ImageGallery model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<ImageGallery> list = instance.findAll();
		assertTrue(list.size() > 0);
		ImageGallery model = new ImageGallery();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<ImageGallery> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<ImageGallery> list = instance.findAll();
		assertTrue(list.size() > 0);
		ImageGallery model = list.get(0);
		model.setDescription("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getDescription().equals(model.getDescription()));
	}

}