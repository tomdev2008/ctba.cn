package org.net9.search.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.net9.blog.service.EntryService;
import org.net9.domain.model.blog.BlogEntry;

import com.google.inject.Inject;
import com.j2bb.common.search.index.IndexBuilder;

public class BlogEntryIndexBuilder extends GenericIndexBuilder {

	static Log log = LogFactory.getLog(IndexBuilder.class);

	@Inject
	EntryService blogEntryService;

	public BlogEntryIndexBuilder(File indexDir) {
		super(indexDir);
	}

	@Override
	protected Document convertModelToDocument(Object model) {
		Document doc = new Document();
		BlogEntry entry = (BlogEntry) model;
		doc.add(new Field("id", String.valueOf(entry.getId()), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("title", entry.getTitle(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("author", entry.getAuthor(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("date", entry.getDate(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		return doc;
	}

	@SuppressWarnings("unchecked")
	public void createIndex() {
		IndexWriter iw = null;
		try {
			iw = this.getIndexWriter();
			Integer entriesCnt = blogEntryService.getEntriesCnt(0, 0,
					EntryService.EntryType.NORMAL.getType());
			log.info("We got " + entriesCnt + " entries in the db");
			Integer start = 0;
			while (start < entriesCnt) {
				log.info("deal blog entry start with: " + start);
				List entries = blogEntryService.findEntries(0, 0,
						EntryService.EntryType.NORMAL.getType(), start,
						INDEX_RUNTIME_LIMIT);
				try {
					this.createIndexCustermed(entries, iw);
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
