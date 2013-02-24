package org.net9.search.lucene.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.bbs.Topic;

import com.j2bb.common.search.index.AbstractIndexBuilder;

public class DummyTopicIndexBuilder extends AbstractIndexBuilder {

	static Log log = LogFactory.getLog(DummyTopicIndexBuilder.class);

	public DummyTopicIndexBuilder(File indexDir) {
		super(indexDir);
	}

	@SuppressWarnings("unchecked")
	public void createIndex() {
		IndexWriter iw = null;
		try {
			iw = this.getIndexWriter();
			List topicList = new ArrayList<Topic>();
			for (int i = 0; i < 10; i++) {
				Topic model = new Topic();
				model.setTopicTitle("test" + i);
				model.setTopicAuthor("gladstone");
				model.setTopicContent("content_" + i);
				model.setTopicId(100 + i);
				model.setTopicBoardId(1);
				model.setTopicTime(StringUtils.getTimeStrByNow());
				topicList.add(model);
			}
			//中文
			for (int i = 20; i < 30; i++) {
				Topic model = new Topic();
				model.setTopicTitle("站内sousuo" );
				model.setTopicAuthor("小秘书");
				model.setTopicContent("工口相谈" + i);
				model.setTopicId(100 + i);
				model.setTopicBoardId(1);
				model.setTopicTime(StringUtils.getTimeStrByNow());
				topicList.add(model);
			}
			this.createIndexAutomaticly(topicList, iw,Topic.class);
			int numOfDoc = iw.maxDoc();
			iw.optimize();
			log.info(numOfDoc + " documents added to "
					+ this.indexDir.getCanonicalPath());
			iw.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (iw != null) {
					iw.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	@Override
	protected Document convertModelToDocument(Object arg0) {
		return null;
	}
}
