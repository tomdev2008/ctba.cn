package org.net9.blog.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.core.types.ImageOptionType;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.gallery.service.ImageService;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class ImageServiceTest extends TestBase {

	ImageService imageService;

	UserService userService;

	@Before
	public void setUp() throws Exception {
		imageService = Guice.createInjector(new ServiceModule()).getInstance(
				ImageService.class);

		userService = Guice.createInjector(new ServiceModule()).getInstance(
				UserService.class);
	}

	@Test
	public void testDelImageImageGallery() {
		ImageGallery model = imageService.getImageGallery(null);
		if (model != null) {
			imageService.delImageGallery(model);
		}
	}

	@Test
	public void testFindImageGalleries() {
		List<ImageGallery> list = imageService.findGalleries(
				ImageOptionType.PUBLIC.getValue(), null, 0, 10);
		System.out.println(list.size());
	}

	@Test
	public void testGetImageGallery() {
		ImageGallery model = imageService.getImageGallery(null);
		System.out.println(model != null);
	}

	@Test
	public void testGetImageGalleryCnt() {
		Integer cnt = imageService.getGalleryCnt(ImageOptionType.PUBLIC
				.getValue(), "gladstone");
		System.out.println(cnt);
		Assert.assertTrue(cnt >= 0);

	}

	@Test
	public void testGetImagesCnt() {
		Integer cnt = imageService.getImagesCnt(null, "gladstone");
		System.out.println(cnt);
		Assert.assertTrue(cnt >= 0);
	}

	@Test
	public void testSaveImageGallery() {
		ImageGallery model = new ImageGallery();
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setDescription("gladstone 's images");
		model.setHits(10);
		model.setName("gladstone9");

		model.setPlace("osaka");

		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setViewOption(ImageOptionType.PUBLIC.getValue());
		model.setUsername("gladstone");
		imageService.saveImageGallery(model);
	}

}
