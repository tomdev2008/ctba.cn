package org.net9.core.util.image;

import org.junit.Assert;
import org.junit.Test;
import org.net9.common.util.image.ImageCutter;
import org.net9.common.util.image.JMagickImageCutter;
import org.net9.common.util.image.SimpleImageCutter;

public class ImageCutterTest {

	@Test
	public void testJMagickGetImage() {
		try {
			System.out.println(System.getProperty("java.library.path"));
			ImageCutter cutter = new JMagickImageCutter();

			cutter.getImage("TestRoot/images/1.jpg",
					"TestRoot/images/dest_2.jpg", 100d, 100d);

			cutter.getImage("TestRoot/images/test.jpg",
					"TestRoot/images/dest_test_2.jpg", 100d, 100d);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSimpleGetImage() throws Exception {
		ImageCutter cutter = new SimpleImageCutter();
		cutter.getImage("TestRoot/images/1.jpg", "TestRoot/images/dest_1.jpg",
				100d, 100d);
		cutter.getImage("TestRoot/images/test.jpg",
				"TestRoot/images/dest_test_1.jpg", 100d, 100d);
	}

}
