package org.net9.search.lucene.index;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.net9.core.types.NewsStateType;
import org.net9.domain.model.news.NewsEntry;
import org.net9.news.service.NewsService;

import com.google.inject.Inject;

public class NewsIndexBuilder extends GenericIndexBuilder {
	static Log log = LogFactory.getLog(NewsIndexBuilder.class);

	@Inject
	NewsService newsService;

	public NewsIndexBuilder(File indexDir) {
		super(indexDir);
	}

	@Override
	protected Document convertModelToDocument(Object model) {
		Document doc = new Document();
		NewsEntry entry = (NewsEntry) model;
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
			Integer topicCnt = newsService.getNewsCnt(true, NewsStateType.OK
					.getValue(), null);
			log.info("We got " + topicCnt + " entries in the db");
			Integer start = 0;
			while (start < topicCnt) {
				log.info("deal new entry start with: " + start);
				List topics = newsService.findNewses(true, NewsStateType.OK
						.getValue(), null, start, INDEX_RUNTIME_LIMIT);
				this.createIndexCustermed(topics, iw);
				start += INDEX_RUNTIME_LIMIT;
			}
			int numOfDoc = iw.maxDoc();
			iw.optimize();
			log.info(numOfDoc + " documents added to "
					+ this.indexDir.getCanonicalPath());
			iw.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
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
