package org.net9.search.lucene.index;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.service.ServiceModule;

import com.google.inject.Guice;
import com.j2bb.common.search.index.AbstractIndexBuilder;

public abstract class GenericIndexBuilder extends AbstractIndexBuilder {

	static Log log = LogFactory.getLog(GenericIndexBuilder.class);

	public static Integer INDEX_RUNTIME_LIMIT = 1000;

	public GenericIndexBuilder(File indexDir) {
		super(indexDir);
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

}
