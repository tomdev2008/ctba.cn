package org.net9.core.hit;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.EntryService;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;

import com.google.inject.Inject;

public class HitManager implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(HitManager.class);

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

	public  void refreshAll() {
		log.info("Run HitRefreshTimerTask ...");
		refreshTopicHit();
		refreshGroupTopicHit();
		refreshGroupcHit();
		refreshBlogHit();
		refreshBlogEntryHit();
	}

	private  void refreshBlogEntryHit() {
		Map<Integer, Integer> blogEntryHitMap = SmartHitStrategy.blogEntryHitMap;
		log.debug("blogEntryHitMap size:" + blogEntryHitMap.size());

		Object[] keys = blogEntryHitMap.keySet().toArray();
		for (Object key : keys) {
			try {
				Integer id = (Integer) key;
				Integer hit = blogEntryHitMap.get(id);
				this.entryService.updateEntryHit(id, hit);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		// Set<Integer> keySet = blogEntryHitMap.keySet();
		// for (Iterator<Integer> it = keySet.iterator(); it.hasNext();) {
		// try {
		// Integer id = it.next();
		// Integer hit = blogEntryHitMap.get(id);
		// this.entryService.updateEntryHit(id, hit);
		// } catch (Exception e) {
		// e.printStackTrace();
		// continue;
		// }
		// }
		SmartHitStrategy.blogEntryHitMap.clear();
	}

	private  void refreshBlogHit() {
		Map<Integer, Integer> blogHitMap = SmartHitStrategy.blogHitMap;
		log.debug("blogHitMap size:" + blogHitMap.size());

		Object[] keys = blogHitMap.keySet().toArray();
		for (Object key : keys) {
			try {
				Integer id = (Integer) key;
				Integer hit = blogHitMap.get(id);
				this.blogService.updateBlogHit(id, hit);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		// Set<Integer> keySet = blogHitMap.keySet();
		// for (Iterator<Integer> it = keySet.iterator(); it.hasNext();) {
		// try {
		// Integer id = it.next();
		// Integer hit = blogHitMap.get(id);
		// this.blogService.updateBlogHit(id, hit);
		// } catch (Exception e) {
		// e.printStackTrace();
		// continue;
		// }
		// }
		SmartHitStrategy.blogHitMap.clear();
	}

	private  void refreshGroupcHit() {
		Map<Integer, Integer> groupHitMap = SmartHitStrategy.groupHitMap;
		log.debug("groupHitMap size:" + groupHitMap.size());

		Object[] keys = groupHitMap.keySet().toArray();
		for (Object key : keys) {
			try {
				Integer id = (Integer) key;
				Integer hit = groupHitMap.get(id);
				this.groupService.updateGroupHit(id, hit);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		// Set<Integer> keySet = groupHitMap.keySet();
		// for (Iterator<Integer> it = keySet.iterator(); it.hasNext();) {
		// try {
		// Integer id = it.next();
		// Integer hit = groupHitMap.get(id);
		// this.groupService.updateTopicHit(id, hit);
		// } catch (Exception e) {
		// e.printStackTrace();
		// continue;
		// }
		// }
		SmartHitStrategy.groupHitMap.clear();
	}

	private  void refreshGroupTopicHit() {
		Map<Integer, Integer> groupTopicHitMap = SmartHitStrategy.groupTopicHitMap;
		log.debug("groupTopicHitMap size:" + groupTopicHitMap.size());
		Object[] keys = groupTopicHitMap.keySet().toArray();
		for (Object key : keys) {
			try {
				Integer id = (Integer) key;
				Integer hit = groupTopicHitMap.get(id);
				this.groupTopicService.updateTopicHit(id, hit);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		// Set<Integer> keySet = groupTopicHitMap.keySet();
		// for (Iterator<Integer> it = keySet.iterator(); it.hasNext();) {
		// try {
		// Integer id = it.next();
		// Integer hit = groupTopicHitMap.get(id);
		// this.groupTopicService.updateTopicHit(id, hit);
		// } catch (Exception e) {
		// e.printStackTrace();
		// continue;
		// }
		// }
		SmartHitStrategy.groupTopicHitMap.clear();
	}

	private  void refreshTopicHit() {
		Map<Integer, Integer> topicHitMap = SmartHitStrategy.topicHitMap;
		log.debug("topicHitMap size:" + topicHitMap.size());

		Object[] keys = topicHitMap.keySet().toArray();
		for (Object key : keys) {
			try {
				Integer id = (Integer) key;
				Integer hit = topicHitMap.get(id);
				this.topicService.updateTopicHit(id, hit);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		// Set<Integer> keySet = topicHitMap.keySet();
		// for (Iterator<Integer> it = keySet.iterator(); it.hasNext();) {
		// try {
		// Integer id = it.next();
		// Integer hit = topicHitMap.get(id);
		// this.topicService.updateTopicHit(id, hit);
		// } catch (Exception e) {
		// e.printStackTrace();
		// continue;
		// }
		// }
		SmartHitStrategy.topicHitMap.clear();
	}
}
