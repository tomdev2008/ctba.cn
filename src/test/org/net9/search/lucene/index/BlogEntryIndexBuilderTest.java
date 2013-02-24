package org.net9.search.lucene.index;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.net9.test.TestBase;

public class BlogEntryIndexBuilderTest extends TestBase {

	@Before
	public void setUp() throws Exception {
	}

	 @Test
	public void testCreateIndex() throws Exception {
		IndexBuilderFactory.createBlogIndexBuilder(new File("TestRoot/test_index/blog/"))
				.createIndex();
	}

}
