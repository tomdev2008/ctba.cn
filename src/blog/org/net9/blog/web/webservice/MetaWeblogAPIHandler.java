/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.net9.blog.web.webservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.net9.blog.service.EntryService;
import org.net9.common.util.DateUtils;
import org.net9.common.util.FileUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.BlogEntryStateType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.ImageUtils;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogEntry;

/**
 * Roller XML-RPC Handler for the MetaWeblog API.
 * 
 * MetaWeblog API spec can be found at http://www.xmlrpc.com/metaWeblogApi
 * 
 * @author David M Johnson
 * @author Gladstone Chen
 */
public class MetaWeblogAPIHandler extends BloggerAPIHandler {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(MetaWeblogAPIHandler.class);

	/**
	 * Authenticates a user and returns the categories available in the website
	 * 
	 * @param blogid
	 *            Dummy Value for Roller
	 * @param userid
	 *            Login for a MetaWeblog user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @throws Exception
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getCategories(String blogid, String userid, String password)
			throws Exception {

		logger.debug("getCategories() Called =====[ SUPPORTED ]=====");
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);

		validate(blogid, userid, password);
		Blog blogModel = this.blogService.getBlog(Integer.valueOf(blogid));
		try {
			// Hashtable result = new Hashtable();
			Vector results = new Vector();
			List<BlogCategory> cats = this.blogService.findCategories(Integer
					.valueOf(blogid), 0, BusinessConstants.MAX_INT);
			for (BlogCategory category : cats) {
				Hashtable struct = new Hashtable();
				struct.put("description", category.getName());
				struct.put("htmlUrl", this.getBlogUrl(blogModel) + "/"
						+ category.getId());
				struct.put("rssUrl", this.commonService.getConfig()
						.getDomainRoot()
						+ "/rss.shtml?type=blog&bgid="
						+ blogModel.getId()
						+ "&cid=" + category.getId());
				results.add(struct);
			}
			return results;
		} catch (Exception e) {
			String msg = "ERROR in MetaWeblogAPIHandler.getCategories";
			logger.error(msg, e);
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

	/**
	 * Edits a given post. Optionally, will publish the blog after making the
	 * edit
	 * 
	 * @param postid
	 *            Unique identifier of the post to be changed
	 * @param userid
	 *            Login for a MetaWeblog user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param struct
	 *            Contents of the post
	 * @param publish
	 *            If true, the blog will be published immediately after the post
	 *            is made
	 * @throws org.apache.xmlrpc.XmlRpcException
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean editPost(String postid, String userid, String password,
			Hashtable struct, int publish) throws Exception {
		return editPost(postid, userid, password, struct, publish > 0);
	}

	@SuppressWarnings("unchecked")
	public boolean editPost(String postid, String userid, String password,
			Hashtable struct, boolean publish) throws Exception {

		logger.debug("editPost() Called ========[ SUPPORTED ]=====");
		logger.debug("     PostId: " + postid);
		logger.debug("     UserId: " + userid);
		logger.debug("    Publish: " + publish);
		BlogEntry model = this.entryService.getEntry(Integer.valueOf(postid));
		validate(String.valueOf(model.getBlogBlog().getId()), userid, password);
		Hashtable postcontent = struct;
		String description = (String) postcontent.get("description");
		String title = (String) postcontent.get("title");
		if (title == null) {
			title = "";
		}
		String cat = null;
		if (postcontent.get("categories") != null) {
			Vector cats = (Vector) postcontent.get("categories");
			if (cats != null && cats.size() > 0) {
				cat = (String) cats.elementAt(0);
			}
		}

		try {
			if (StringUtils.isNotEmpty(cat)) {
				BlogCategory c = blogService.getCategoryByName(model
						.getBlogBlog().getId(), cat);
				model.setCategoryId(c.getId());
			}
			// update
			model.setBody(description);
			model.setPublishDate(DateUtils.getNow());
			if (!Boolean.valueOf(publish).booleanValue()) {
				model.setType(EntryService.EntryType.DRAFT.getType());
				entryService.updateEntry(model);
			} else {
				// 如果是草稿转来的，删掉重建（更新id）
				if (EntryService.EntryType.DRAFT.getType() == model.getType()
						.intValue()) {
					BlogEntry newModel = new BlogEntry();
					try {
						PropertyUtil.copyProperties(newModel, model);
					} catch (Exception ex) {
						logger.error(ex.getMessage());
					}
					newModel.setId(null);
					newModel.setDate(StringUtils.getTimeStrByNow());
					newModel.setType(EntryService.EntryType.NORMAL.getType());
					newModel.setPublishDate(DateUtils.getNow());
					entryService.newEntry(newModel);
					entryService.delEntry(model);
					List<BlogEntry> entries = entryService.findEntries(model
							.getBlogBlog().getId(), null,
							EntryService.EntryType.NORMAL.getType(), 0, 1);
					if (entries.size() > 0) {
						userService.trigeEvent(
								this.userService.getUser(userid), entries
										.get(0).getId()
										+ "", UserEventType.ENTRY_NEW);
					}
				} else {
					model.setType(EntryService.EntryType.NORMAL.getType());
					entryService.updateEntry(model);
					userService.trigeEvent(this.userService.getUser(userid),
							model.getId() + "", UserEventType.ENTRY_EDIT);
				}
			}
			return true;
		} catch (Exception e) {
			String msg = "ERROR in BlooggerAPIHander.editPost";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}

	}

	/**
	 * Makes a new post to a designated blog. Optionally, will publish the blog
	 * after making the post
	 * 
	 * @param blogid
	 *            Unique identifier of the blog the post will be added to
	 * @param userid
	 *            Login for a MetaWeblog user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param struct
	 *            Contents of the post
	 * @param publish
	 *            If true, the blog will be published immediately after the post
	 *            is made
	 * @throws org.apache.xmlrpc.XmlRpcException
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String newPost(String blogid, String userid, String password,
			Hashtable struct, int publish) throws Exception {
		return newPost(blogid, userid, password, struct, publish > 0);
	}

	@SuppressWarnings("unchecked")
	public String newPost(String blogid, String userid, String password,
			Hashtable struct, boolean publish) throws Exception {

		logger.debug("newPost() Called ===========[ SUPPORTED ]=====");
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("    Publish: " + publish);

		validate(blogid, userid, password);
		Blog blog = blogService.getBlog(new Integer(blogid));

		try {

			Hashtable postcontent = struct;
			String description = (String) postcontent.get("description");
			String title = (String) postcontent.get("title");
			if (StringUtils.isEmpty(title) && StringUtils.isEmpty(description)) {
				throw new XmlRpcException(BLOGGERAPI_INCOMPLETE_POST,
						"Must specify title or description");
			}
			if (StringUtils.isEmpty(title)) {
				title = StringUtils.getShorterStr(description, 15);
			}
			BlogCategory c = null;
			if (postcontent.get("categories") != null) {
				Vector cats = (Vector) postcontent.get("categories");
				if (cats != null && cats.size() > 0) {
					for (int i = 0; i < cats.size(); i++) {
						String cat = (String) cats.get(i);
						logger.debug("cat: " + cat);
						c = blogService.getCategoryByName(blog.getId(), cat);
					}
				}
			}
			if (c == null) {
				c = this.prepareCategory(blog);
			}

			BlogEntry model = new BlogEntry();
			model.setAuthor(userid);
			model.setBlogBlog(blog);
			model.setBody(description);
			model.setCategoryId(c.getId());
			model.setCommentsEnabled(Short.valueOf("1"));
			model.setDate(DateUtils.getNow());
			model.setPublishDate(DateUtils.getNow());
			model.setHits(0);
			model.setSubtitle(title);
			model.setTags("");
			model.setTitle(title);
			model.setState(BlogEntryStateType.ALL.getValue());
			model.setPermalink(model.getCategoryId() + "/"
					+ StringUtils.createFileName("") + "_"
					+ java.util.UUID.randomUUID().toString() + ".html");

			if (Boolean.valueOf(publish).booleanValue()) {
				model.setType(EntryService.EntryType.NORMAL.getType());
			} else {
				model.setType(EntryService.EntryType.DRAFT.getType());
			}

			entryService.newEntry(model);

			if (Boolean.valueOf(publish).booleanValue()) {
				List<BlogEntry> entries = entryService.findEntries(
						blog.getId(), null, EntryService.EntryType.NORMAL
								.getType(), 0, 1);
				if (blog.getEntryCnt() == null) {
					blog.setEntryCnt(1);
				} else {
					blog.setEntryCnt(blog.getEntryCnt() + 1);
				}
				userService.trigeEvent(this.userService.getUser(userid),
						entries.get(0).getId() + "", UserEventType.ENTRY_NEW);
				blogService.updateBlog(blog);
				return String.valueOf(entries.get(0).getId());
			} else {
				List<BlogEntry> entries = entryService.findEntries(
						blog.getId(), null, EntryService.EntryType.DRAFT
								.getType(), 0, 1);
				return String.valueOf(entries.get(0).getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			String msg = "ERROR in BlooggerAPIHander.newPost";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

	/**
	 * 
	 * @param postid
	 * @param userid
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPost(String postid, String userid, String password)
			throws Exception {

		logger.debug("getPost() Called =========[ SUPPORTED ]=====");
		logger.debug("     PostId: " + postid);
		logger.debug("     UserId: " + userid);

		BlogEntry entry = this.entryService.getEntry(Integer.valueOf(postid));
		validate(String.valueOf(entry.getBlogBlog().getId()), userid, password);
		try {
			Hashtable struct = this.convertEntry(entry);
			return struct;
		} catch (Exception e) {
			String msg = "ERROR in MetaWeblogAPIHandler.getPost";
			logger.error(msg, e);
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

	/**
	 * 
	 * Allows user to post a binary object, a file, to Roller. If the file is
	 * allowed by the RollerConfig file-upload settings, then the file will be
	 * placed in the user's upload diretory.
	 */
	@SuppressWarnings("unchecked")
	public Object newMediaObject(String blogid, String userid, String password,
			Hashtable struct) throws Exception {

		logger.debug("newMediaObject() Called =[ SUPPORTED ]=====");
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("   Password: *********");

		validate(blogid, userid, password);
		try {
			String name = (String) struct.get("name");
			name = name.replaceAll("/", "_");
			String type = (String) struct.get("type");
			logger.debug("newMediaObject name: " + name);
			logger.debug("newMediaObject type: " + type);

			byte[] bits = (byte[]) struct.get("bits");

			if (name.startsWith("/")) {
				name = name.substring(1);
			}
			try {
				name = ImageUtils.wrapImageNameByTime(name);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			// Try to save file
			saveFile(name, type, bits.length, new ByteArrayInputStream(bits));

			String staticRoot = SystemConfigUtils
					.getProperty("url.static.root");
			if (StringUtils.isEmpty(staticRoot)) {
				staticRoot = this.commonService.getConfig().getDomainRoot()
						+ "/" + SystemConfigVars.UPLOAD_DIR_PATH;
			}
			String fileLink = staticRoot + "/" + name;
			Hashtable returnStruct = new Hashtable(1);
			returnStruct.put("url", fileLink);
			return returnStruct;
		} catch (Exception e) {
			e.printStackTrace();

			String msg = "ERROR in MetaWeblogAPIHandler.newMediaObject";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

	/**
	 * Get a list of recent posts for a category
	 * 
	 * @param blogid
	 *            Unique identifier of the blog the post will be added to
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param numposts
	 *            Number of Posts to Retrieve
	 * @throws XmlRpcException
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getRecentPosts(String blogid, String userid, String password,
			int numposts) throws Exception {

		logger.debug("getRecentPosts() Called ===========[ SUPPORTED ]=====");
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("     Number: " + numposts);

		validate(blogid, userid, password);
		try {
			Vector results = new Vector();
			List<BlogEntry> eList = this.entryService.findEntries(Integer
					.valueOf(blogid), null, null, 0, numposts);
			for (BlogEntry entry : eList) {
				Hashtable struct = convertEntry(entry);
				results.add(struct);
			}
			return results;
		} catch (Exception e) {
			String msg = "ERROR in BlooggerAPIHander.getRecentPosts";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}

	}

	@SuppressWarnings("unchecked")
	private Hashtable convertEntry(BlogEntry entry) {
		String permalink = "";
		Hashtable struct = new Hashtable();
		struct.put("title", entry.getTitle());
		struct.put("link", this.commonService.getConfig().getDomainRoot() + "/"
				+ UrlConstants.BLOG_ENRTY + entry.getId());
		struct.put("description", entry.getBody());
		struct.put("pubDate", entry.getPublishDate());
		struct.put("dateCreated", entry.getDate());
		struct.put("guid", entry.getId());
		struct.put("permaLink", permalink);
		struct.put("postid", entry.getId());
		struct.put("userid", entry.getAuthor());
		struct.put("author", entry.getAuthor());
		Vector catArray = new Vector();
		catArray.addElement(this.getBlogUrl(entry.getBlogBlog()) + "/"
				+ entry.getCategoryId());
		struct.put("categories", catArray);
		return struct;
	}

	/**
	 * 保存文件
	 * 
	 * @param filename
	 * @param contentType
	 * @param size
	 * @param is
	 * @throws IOException
	 */
	private void saveFile(String filename, String contentType, long size,
			InputStream is) throws IOException {
		File dirPath = new File(SystemConfigVars.REAL_CONTEXT + File.separator
				+ SystemConfigVars.UPLOAD_DIR_PATH);
		File saveFile = new File(dirPath.getAbsolutePath() + File.separator
				+ filename);
		FileUtils.checkDir(saveFile.getAbsolutePath(), false);
		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(saveFile);
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			logger.debug("The file has been written to ["
					+ saveFile.getAbsolutePath() + "]");
		} catch (Exception e) {
			throw new IOException();
			// throw new IOException(e);
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (Exception ignored) {
			}
		}

	}
}
