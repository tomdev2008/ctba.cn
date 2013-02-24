package org.net9.core.hit;

import java.util.HashMap;
import java.util.Map;

import org.net9.common.util.CommonUtils;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;

/**
 * #904 关于帖子缓存更新策略的调整检讨
 * 
 * @author chenchangren
 * 
 */
public class SmartHitStrategy implements HitStrategy {

	public static Map<Integer, Integer> groupHitMap = new HashMap<Integer, Integer>();
	public static Map<Integer, Integer> groupTopicHitMap = new HashMap<Integer, Integer>();
	public static Map<Integer, Integer> topicHitMap = new HashMap<Integer, Integer>();
	public static Map<Integer, Integer> blogHitMap = new HashMap<Integer, Integer>();
	public static Map<Integer, Integer> blogEntryHitMap = new HashMap<Integer, Integer>();

	@Override
	public void hitBlog(Blog model) {

		Integer hit = blogHitMap.get(model.getId());
		if (hit == null) {
			hit = model.getHits();
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setHits(hit + hitPlus);
		blogHitMap.put(model.getId(), hit + hitPlus);

	}

	@Override
	public void hitBlogEntry(BlogEntry model) {

		Integer hit = blogEntryHitMap.get(model.getId());
		if (hit == null) {
			hit = model.getHits();
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setHits(hit + hitPlus);
		blogEntryHitMap.put(model.getId(), hit + hitPlus);

	}

	@Override
	public void hitGroup(GroupModel model) {
		Integer hit = groupHitMap.get(model.getId());
		if (hit == null) {
			hit = model.getHits();
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setHits(hit + hitPlus);
		groupHitMap.put(model.getId(), hit + hitPlus);
	}

	@Override
	public void hitGroupTopic(GroupTopic model) {
		Integer hit = groupTopicHitMap.get(model.getId());
		if (hit == null) {
			hit = model.getHits();
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setHits(hit + hitPlus);
		groupTopicHitMap.put(model.getId(), hit + hitPlus);

	}

	@Override
	public void hitTopic(Topic model) {
		Integer hit = topicHitMap.get(model.getTopicId());
		if (hit == null) {
			hit = model.getTopicHits();
		}
		int hitPlus = CommonUtils.getHitPlus();
		model.setTopicHits(hit + hitPlus);
		topicHitMap.put(model.getTopicId(), hit + hitPlus);

	}

}
