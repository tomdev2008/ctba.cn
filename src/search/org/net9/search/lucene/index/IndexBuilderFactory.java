package org.net9.search.lucene.index;

import java.io.File;

import com.j2bb.common.search.index.IndexBuilder;

public class IndexBuilderFactory {

	public static IndexBuilder createBbsIndexBuilder(File indexDir) {
		return new BbsTopicIndexBuilder(indexDir);
	}

	public static IndexBuilder createGroupIndexBuilder(File indexDir) {
		return new GroupTopicIndexBuilder(indexDir);
	}

	public static IndexBuilder createNewsIndexBuilder(File indexDir) {
		return new NewsIndexBuilder(indexDir);
	}

	public static IndexBuilder createBlogIndexBuilder(File indexDir) {
		return new BlogEntryIndexBuilder(indexDir);
	}	
	
	public static IndexBuilder createSubjectIndexBuilder(File indexDir) {
		return new SubjectIndexBuilder(indexDir);
	}
}
