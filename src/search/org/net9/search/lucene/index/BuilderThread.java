package org.net9.search.lucene.index;

import java.io.File;

import org.net9.search.lucene.SearchConst;

import com.j2bb.common.search.index.IndexBuilder;

public class BuilderThread implements Runnable {

	IndexBuilder builder;
	
	public BuilderThread(IndexBuilder builder){
		this.builder = builder;
	}
	
	public void run() {
		if(builder==null){
			runAll();
		}else{
			builder.createIndex();
		}
	}

	private void runAll(){
		IndexBuilderFactory.createBbsIndexBuilder(
				new File(SearchConst.INDEXPATH_TOPIC)).createIndex();
		IndexBuilderFactory.createGroupIndexBuilder(
				new File(SearchConst.INDEXPATH_GROUP)).createIndex();
		IndexBuilderFactory.createBlogIndexBuilder(
				new File(SearchConst.INDEXPATH_BLOG)).createIndex();
		IndexBuilderFactory.createNewsIndexBuilder(
				new File(SearchConst.INDEXPATH_NEWS)).createIndex();
		IndexBuilderFactory.createSubjectIndexBuilder(
				new File(SearchConst.INDEXPATH_SUBJECT)).createIndex();
	}
}
