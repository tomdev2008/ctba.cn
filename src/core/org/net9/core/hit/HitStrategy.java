package org.net9.core.hit;

import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;

import com.google.inject.ImplementedBy;

/**
 * #904 关于帖子缓存更新策略的调整检讨
 * 
 * @author chenchangren
 * 
 */
@ImplementedBy(SmartHitStrategy.class)
public interface HitStrategy {
	
	public void hitBlog(Blog model);

	public void hitBlogEntry(BlogEntry model);

	public void hitGroup(GroupModel model);

	public void hitGroupTopic(GroupTopic model);

	public void hitTopic(Topic model);
}
