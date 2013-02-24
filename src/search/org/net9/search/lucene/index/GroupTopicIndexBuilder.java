package org.net9.search.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.net9.domain.model.group.GroupTopic;
import org.net9.group.service.GroupTopicService;

import com.google.inject.Inject;

public class GroupTopicIndexBuilder extends GenericIndexBuilder {
	static Log log = LogFactory.getLog(GroupTopicIndexBuilder.class);

	@Inject
	GroupTopicService groupTopicService;

	public GroupTopicIndexBuilder(File indexDir) {
		super(indexDir);
	}

	@Override
	protected Document convertModelToDocument(Object model) {
		Document doc = new Document();
		GroupTopic entry = (GroupTopic) model;
		doc.add(new Field("id", String.valueOf(entry.getId()), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("title", entry.getTitle(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("author", entry.getAuthor(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("createTime", entry.getCreateTime(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		return doc;
	}

	@SuppressWarnings("unchecked")
	public void createIndex() {
		IndexWriter iw = null;
		try {
			iw = this.getIndexWriter();
			Integer topicCnt = groupTopicService.getGroupTopicsCnt(null, null,
					false);
			log.info("We got " + topicCnt + " entries in the db");
			Integer start = 0;
			while (start < topicCnt) {
				log.info("deal group topic start with: " + start);
				List topics = groupTopicService.findTopics(null, null, false,
						null, start, INDEX_RUNTIME_LIMIT);
				try {
					this.createIndexCustermed(topics, iw);
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
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
