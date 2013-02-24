package org.net9.blog.rss;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.blog.service.AddressService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.EntryService;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.Md5Utils;
import org.net9.core.rss.reader.BaseRssReader;
import org.net9.core.types.BlogEntryStateType;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogAddress;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogEntry;

import com.google.inject.Inject;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * Blog rss reader
 * 
 * @author gladstone
 * @since 2008/06/19
 */
public class BlogRssReader extends BaseRssReader {

	private static final long serialVersionUID = -3732442723600001324L;
	private static Log log = LogFactory.getLog(BlogRssReader.class);
    @Inject
    AddressService vestService;
    @Inject
    BlogService blogService;
    @Inject
    EntryService entryService;

    /**
     * build 4 a single rss url
     *
     * @param m
     */
    @SuppressWarnings("unchecked")
    public void buildRSSContent(Map m) throws Exception {

        String url = (String) m.get("url");
        int cnt = 0;
        if (!url.startsWith(BlogRssReader.SUFFIX_PROTOCAL)) {
            url = BlogRssReader.SUFFIX_PROTOCAL + "" + url;
        }
        log.debug("Get rss from " + url);
        URL feedUrl = null;
        feedUrl = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        List entries = feed.getEntries();
        for (int j = entries.size() - 1; j >= 0; j--) {
            SyndEntry e = (SyndEntry) entries.get(j);
            cnt += storeEntry(e, (String) m.get("username"));
        }
        Integer gotentriescnt = ((Integer) m.get("gotentriescnt")).intValue();
        BlogAddress model = vestService.getAddress(Integer.valueOf("" + m.get("id")));
        model.setGotEntriesCnt(gotentriescnt + cnt);
        vestService.saveAddress(model);
        Blog blog = model.getCat().getBlogBlog();
        if (blog.getEntryCnt() == null) {
            blog.setEntryCnt(1);
        } else {
            blog.setEntryCnt(blog.getEntryCnt() + cnt);
        }
        blogService.updateBlog(blog);
    }

    /**
     * 保存日志
     *
     * @param e
     * @param username
     * @return
     */
    private int storeEntry(SyndEntry e, String username) {
        String title = e.getTitle();
        String link = e.getLink();
        // String updateDate = e.getUpdatedDate().toString();
        // String publishDate = e.getPublishedDate().toString();
        log.debug(e);
        // String categoryIdStr = request.getParameter("categoryId");
        String body = "<img src='images/icons/article.png' align='absmiddle' alt='原文链接'>&nbsp;<a href='" + link + "'>原文链接</a>" + "<br/><br/>\r\n" + e.getDescription().getValue();
        Blog blog = blogService.getBlogByUser(username);
        if (blog == null) {
            return 0;
        }
        BlogCategory c = blogService.getRssCategory(blog.getId());
        if (c == null) {
            String catName = I18nMsgUtils.getInstance().getMessage(
                    "category.rss");
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
            c = blogService.getRssCategory(blog.getId());
        }
        String permalink = //blog.getUrl().substring(
                //blog.getUrl().lastIndexOf("/") + 1)
                "/" + c.getId() + "/" + "rss_" + Md5Utils.getMD5(title) + ".html";
        Integer commentsEnabled = 1;
        BlogEntry model = entryService.getEntryByPermalink(permalink);
        if (model != null) {
            return 0;
        }
        // insert
        model = new BlogEntry();
        model.setAuthor(username);
        model.setBlogBlog(blog);
        model.setBody(body);
        model.setCategoryId(c.getId());
        model.setCommentsEnabled(commentsEnabled.shortValue());
        model.setDate(DateUtils.getNow());
        model.setHits(0);
        model.setSubtitle(title);
        model.setTags("");
        model.setTitle(title);
        model.setPermalink(permalink);
        model.setState(BlogEntryStateType.ALL.getValue());
        model.setType(EntryService.EntryType.NORMAL.getType());
        entryService.newEntry(model);
        return 1;
    }
}
