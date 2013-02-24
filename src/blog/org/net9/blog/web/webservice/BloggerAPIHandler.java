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

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.net9.blog.service.EntryService;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.core.types.BlogEntryStateType;
import org.net9.core.types.UserEventType;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.MainUser;

/**
 * Roller XML-RPC Handler for the Blogger v1 API.
 * 
 * Blogger API spec can be found at http://plant.blogger.com/api/index.html See
 * also http://xmlrpc.free-conversant.com/docs/bloggerAPI
 * 
 * @author David M Johnson
 * @author Gladstone Chen
 */
public class BloggerAPIHandler extends BaseAPIHandler {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(BloggerAPIHandler.class);

	/**
	 * Delete a Post
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param postid
	 *            Unique identifier of the post to be changed
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param publish
	 *            Ignored
	 * @throws XmlRpcException
	 * @return
	 */
	public boolean deletePost(String appkey, String postid, String userid,
			String password, boolean publish) throws Exception {

		logger.debug("deletePost() Called =====[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     PostId: " + postid);
		logger.debug("     UserId: " + userid);

		BlogEntry model = this.entryService.getEntry(Integer.valueOf(postid));
		validate(String.valueOf(model.getBlogBlog().getId()), userid, password);

		try {
			// delete the entry
			this.entryService.delEntry(model);
		} catch (Exception e) {
			String msg = "ERROR in blogger.deletePost: "
					+ e.getClass().getName();
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}

		return true;
	}

	/**
	 * Edits the main index template of a given blog. Roller only support
	 * updating the main template, the default template of your weblog.
	 * 
	 * 不支持这个功能
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param blogid
	 *            Unique identifier of the blog the post will be added to
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param template
	 *            The text for the new template (usually mostly HTML).
	 * @param templateType
	 *            Determines which of the blog's templates is to be set.
	 * @throws XmlRpcException
	 * @return
	 */
	public boolean setTemplate(String appkey, String blogid, String userid,
			String password, String templateData, String templateType)
			throws Exception {

		logger.debug("setTemplate() Called =====[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("   Template: " + templateData);
		logger.debug("       Type: " + templateType);

		validate(blogid, userid, password);

		throw new XmlRpcException(UNSUPPORTED_EXCEPTION,
				UNSUPPORTED_EXCEPTION_MSG);
	}

	/**
	 * 
	 * 不支持这个功能
	 * 
	 * Returns the main or archive index template of a given blog
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param blogid
	 *            Unique identifier of the blog the post will be added to
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param templateType
	 *            Determines which of the blog's templates will be returned.
	 *            Currently, either "main" or "archiveIndex"
	 * @throws XmlRpcException
	 * @return
	 */
	public String getTemplate(String appkey, String blogid, String userid,
			String password, String templateType) throws Exception {

		logger.debug("getTemplate() Called =====[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("       Type: " + templateType);

		validate(blogid, userid, password);

		throw new XmlRpcException(UNSUPPORTED_EXCEPTION,
				UNSUPPORTED_EXCEPTION_MSG);
	}

	/**
	 * Authenticates a user and returns basic user info (name, email, userid,
	 * etc.)
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @throws XmlRpcException
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getUserInfo(String appkey, String userid, String password)
			throws Exception {

		logger.debug("getUserInfo() Called =====[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     UserId: " + userid);
		validateUser(userid, password);
		MainUser mainUser = this.userService.getUser(userid);
		try {
			// parses full name into two strings, firstname and lastname
			String firstname = "", lastname = "";

			// populates user information to return as a result
			Hashtable result = new Hashtable();
			result.put("nickname", mainUser.getUsername());
			result.put("userid", mainUser.getUsername());
			result.put("email", mainUser.getEmail());
			result.put("lastname", lastname);
			result.put("firstname", firstname);
			return result;
		} catch (Exception e) {
			String msg = "ERROR in BlooggerAPIHander.getInfo";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

	/**
	 * Returns information on all the blogs a given user is a member of
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @throws XmlRpcException
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getUsersBlogs(String appkey, String userid, String password)
			throws Exception {

		logger.debug("getUsersBlogs() Called ===[ SUPPORTED ]=======");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     UserId: " + userid);

		Vector result = new Vector();
		if (validateUser(userid, password)) {
			try {
				MainUser mainUser = this.userService.getUser(userid);
				Blog blogModel = this.blogService.getBlogByUser(mainUser
						.getUsername());
				// get list of user's enabled websites
				Hashtable blog = new Hashtable(3);
				blog.put("url", this.getBlogUrl(blogModel));
				blog.put("blogid", String.valueOf(blogModel.getId()));
				blog.put("blogName", blogModel.getName());
				result.add(blog);
			} catch (Exception e) {
				String msg = "ERROR in BlooggerAPIHander.getUsersBlogs";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return result;
	}

	/**
	 * Edits a given post. Optionally, will publish the blog after making the
	 * edit
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param postid
	 *            Unique identifier of the post to be changed
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param content
	 *            Contents of the post
	 * @param publish
	 *            If true, the blog will be published immediately after the post
	 *            is made
	 * @throws XmlRpcException
	 * @return
	 */
	public boolean editPost(String appkey, String postid, String userid,
			String password, String content, boolean publish) throws Exception {

		logger.debug("editPost() Called ========[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     PostId: " + postid);
		logger.debug("     UserId: " + userid);
		logger.debug("    Publish: " + publish);
		logger.debug("     Content:\n " + content);

		if (validateUser(userid, password)) {
			try {
				BlogEntry model = this.entryService.getEntry(Integer
						.valueOf(postid));
				// update
				model.setBody(content);
				model.setPublishDate(DateUtils.getNow());
				if (!Boolean.valueOf(publish).booleanValue()) {
					model.setType(EntryService.EntryType.DRAFT.getType());
					entryService.updateEntry(model);
				} else {
					// 如果是草稿转来的，删掉重建（更新id）
					if (EntryService.EntryType.DRAFT.getType() == model
							.getType().intValue()) {
						BlogEntry newModel = new BlogEntry();
						try {
							PropertyUtil.copyProperties(newModel, model);
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}
						newModel.setId(null);
						newModel.setDate(StringUtils.getTimeStrByNow());
						newModel.setType(EntryService.EntryType.NORMAL
								.getType());
						newModel.setPublishDate(DateUtils.getNow());
						entryService.newEntry(newModel);
						entryService.delEntry(model);
						List<BlogEntry> entries = entryService.findEntries(
								model.getBlogBlog().getId(), null,
								EntryService.EntryType.NORMAL.getType(), 0, 1);
						if (entries.size() > 0) {
							userService.trigeEvent(this.userService
									.getUser(userid), entries.get(0).getId()
									+ "", UserEventType.ENTRY_NEW);
						}
					} else {
						model.setType(EntryService.EntryType.NORMAL.getType());
						userService.trigeEvent(this.userService.getUser(userid),
								model.getId() + "", UserEventType.ENTRY_EDIT);
						entryService.updateEntry(model);
					}
				}

				this.entryService.updateEntry(model);
				return true;
			} catch (Exception e) {
				String msg = "ERROR in BlooggerAPIHander.editPost";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return false;
	}

	/**
	 * Makes a new post to a designated blog. Optionally, will publish the blog
	 * after making the post
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
	 * @param blogid
	 *            Unique identifier of the blog the post will be added to
	 * @param userid
	 *            Login for a Blogger user who has permission to post to the
	 *            blog
	 * @param password
	 *            Password for said username
	 * @param content
	 *            Contents of the post
	 * @param publish
	 *            If true, the blog will be published immediately after the post
	 *            is made
	 * @throws XmlRpcException
	 * @return
	 */
	public String newPost(String appkey, String blogid, String userid,
			String password, String content, boolean publish) throws Exception {

		logger.debug("newPost() Called ===========[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("    Publish: " + publish);
		logger.debug("    Content:\n " + content);

		validate(blogid, userid, password);
		Blog blog = blogService.getBlog(new Integer(blogid));

		// extract the title from the content
		String title = "";

		if (content.indexOf("<title>") != -1) {
			title = content.substring(content.indexOf("<title>") + 7, content
					.indexOf("</title>"));
			// content = StringUtils.replace(content, "<title>" + title
			// + "</title>", "");
		}
		if (StringUtils.isEmpty(title)) {
			title = StringUtils.getShorterStr(content, 15);
		}

		try {
			BlogCategory c = prepareCategory(blog);
			BlogEntry model = new BlogEntry();
			model.setAuthor(userid);
			model.setBlogBlog(blog);
			model.setBody(content);
			model.setCategoryId(c.getId());
			model.setCommentsEnabled(Short.valueOf("1"));
			model.setDate(DateUtils.getNow());
			model.setPublishDate(DateUtils.getNow());
			model.setHits(0);
			model.setSubtitle(title);
			model.setTags(title);
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
			List<BlogEntry> entries = entryService.findEntries(blog.getId(),
					null, EntryService.EntryType.NORMAL.getType(), 0, 1);
			userService.trigeEvent(this.userService.getUser(userid), entries
					.get(0).getId()
					+ "", UserEventType.ENTRY_NEW);
			if (blog.getEntryCnt() == null) {
				blog.setEntryCnt(1);
			} else {
				blog.setEntryCnt(blog.getEntryCnt() + 1);
			}
			blogService.updateBlog(blog);
			return String.valueOf(entries.get(0).getId());
		} catch (Exception e) {
			String msg = "ERROR in BlooggerAPIHander.newPost";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

	protected BlogCategory prepareCategory(Blog blog) {
		BlogCategory c = blogService.getLiveCategory(blog.getId());
		if (c == null) {
			String catName = I18nMsgUtils.getInstance().getMessage(
					"category.live");
			BlogCategory model = new BlogCategory();
			model.setBlogBlog(blog);
			model.setName(catName);
			model.setTags(catName);
			Integer idx = blogService.getMaxCategoryIdx(blog);
			if (idx == null) {
				idx = 1;
			}
			idx++;
			model.setIdx(idx);
			blogService.newCategory(model);
			c = blogService.getLiveCategory(blog.getId());
		}
		return c;
	}

	/**
	 * This method was added to the Blogger 1.0 API via an Email from Evan
	 * Williams to the Yahoo Group bloggerDev, see the email message for details -
	 * http://groups.yahoo.com/group/bloggerDev/message/225
	 * 
	 * @param appkey
	 *            Unique identifier/passcode of the application sending the post
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
	 * @return Vector of Hashtables, each containing dateCreated, userid,
	 *         postid, content
	 */
	@SuppressWarnings("unchecked")
	public Object getRecentPosts(String appkey, String blogid, String userid,
			String password, int numposts) throws Exception {

		logger.debug("getRecentPosts() Called ===========[ SUPPORTED ]=====");
		logger.debug("     Appkey: " + appkey);
		logger.debug("     BlogId: " + blogid);
		logger.debug("     UserId: " + userid);
		logger.debug("     Number: " + numposts);

		validate(blogid, userid, password);
		try {
			Vector results = new Vector();
			List<BlogEntry> eList = this.entryService.findEntries(Integer
					.valueOf(blogid), null, null, 0, numposts);

			for (BlogEntry entry : eList) {
				Hashtable result = new Hashtable();
				if (entry.getPublishDate() != null) {
					result.put("dateCreated", entry.getPublishDate());
				} else {
					result.put("dateCreated", entry.getDate());
				}
				result.put("userid", userid);
				result.put("postid", entry.getId());
				result.put("content", entry.getBody());
				results.add(result);
			}
			return results;
		} catch (Exception e) {
			String msg = "ERROR in BlooggerAPIHander.getRecentPosts";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}
}
