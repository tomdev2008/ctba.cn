package org.net9.search.lucene.index;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * 图片索引
 * 
 * TODO: complete this
 * 
 * @author ChenChangRen
 * 
 */
public class ImageIndexBuilder extends GenericIndexBuilder {

	static Log log = LogFactory.getLog(ImageIndexBuilder.class);

	public ImageIndexBuilder(File indexDir) {
		super(indexDir);
	}

	@Override
	protected Document convertModelToDocument(Object model) {
		Document doc = new Document();
		doc.add(new Field("dummy", "dummy", Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		return doc;
	}

	@SuppressWarnings("unchecked")
	public void createIndex() {
	}

}
