package org.net9.core.hit;

import java.util.Random;

import org.net9.bbs.service.TopicService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.EntryService;
import org.net9.common.util.CommonUtils;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;

import com.google.inject.Inject;

/**
 * #904 关于帖子缓存更新策略的调整检讨
 * 
 * @author chenchangren
 * 
 */
public class DirectHitStrategy implements HitStrategy {

	@Inject
	private TopicService topicService;
	@Inject
	private EntryService entryService;
	@Inject
	private BlogService blogService;
	@Inject
	private GroupService groupService;
	@Inject
	private GroupTopicService groupTopicService;

	@Override
	public void hitBlog(Blog model) {
		int hitPlus = new Random().nextInt(5) + 1;
		model.setHits(model.getHits() == null ? 0 : model.getHits() + hitPlus);
		blogService.updateBlog(model);

	}

	@Override
	public void hitBlogEntry(BlogEntry model) {
		model.setHits(model.getHits() + CommonUtils.getHitPlus());
		entryService.updateEntry(model);
	}

	@Override
	public void hitGroup(GroupModel model) {
		int hitPlus = new Random().nextInt(5) + 1;
		Integer hit = model.getHits();
		if (hit == null) {
			hit = 0;
		}
		model.setHits(hit + hitPlus);
		groupService.saveGroup(model);

	}

	@Override
	public void hitGroupTopic(GroupTopic model) {
		if (model.getHits() == null) {
			model.setHits(1);
		} else {
			int hitPlus = CommonUtils.getHitPlus();
			model.setHits(model.getHits() + hitPlus);
		}
		groupTopicService.saveTopic(model);
	}

	@Override
	public void hitTopic(Topic model) {
		model.setTopicHits(model.getTopicHits() + CommonUtils.getHitPlus());
		topicService.saveTopic(model, true);
	}

}
