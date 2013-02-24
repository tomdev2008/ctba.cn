package org.net9.search.lucene.index;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.net9.bbs.service.TopicService;
import org.net9.core.types.TopicType;
import org.net9.domain.model.bbs.Topic;

import com.google.inject.Inject;

public class BbsTopicIndexBuilder extends GenericIndexBuilder {

	static Log log = LogFactory.getLog(BbsTopicIndexBuilder.class);

	@Inject
	TopicService bbsTopicService;

	public BbsTopicIndexBuilder(File indexDir) {
		super(indexDir);
	}

	@Override
	protected Document convertModelToDocument(Object model) {
		Document doc = new Document();
		Topic topic = (Topic) model;
		doc.add(new Field("topicId", String.valueOf(topic.getTopicId()),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("topicTitle", topic.getTopicTitle(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("topicAuthor", topic.getTopicAuthor(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("topicTime", topic.getTopicTime(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		return doc;
	}

	@SuppressWarnings("unchecked")
	public void createIndex() {
		IndexWriter iw = null;
		try {
			iw = this.getIndexWriter();
			Integer topicsCnt = this.bbsTopicService.getTopicCnt(null,
					TopicType.NORMAL.getValue());
			log.info("We got " + topicsCnt + " in the db");
			Integer start = 0;
			while (start < topicsCnt) {
				log.info("deal topic start with: " + start);
				List topicList = this.bbsTopicService.findTopics(null, start,
						INDEX_RUNTIME_LIMIT, TopicType.NORMAL.getValue());
				this.createIndexCustermed(topicList, iw);
				start += INDEX_RUNTIME_LIMIT;

			}
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

}
