package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.blog.service.EntryService;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.NewsStateType;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.ListWrapper;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;

import cn.ctba.equipment.EquipmentStateType;

/**
 * <li> 鸿毛羽球俱乐部->178 ;CT羽球党->71; 羽球装备团购->190
 * <li> news(羽球进行时)->6
 * 
 * @author chenchangren
 * 
 */
@WebModule("badmintonPlugin")
@SuppressWarnings("serial")
public class BadmintonPluginServlet extends BusinessCommonServlet {

	private static final int CORE_NEWS_ID = 6;
	private static final int CORE_BLOG_ID = 870;// 灰主流羽球测评
	private static final int CORE_SHOP_ID = 9;// 装备控 的店铺 - 羽球装备团购
	private static final int CORE_GROUP_ID = 178;
	// private static final int EQUIPMENT_GROUP_ID = 190;
	private static final Integer[] CORE_GROUP_IDS = new Integer[] { 178, 71,
			190 };

	// public static final String CORE_GROUP_TAG = "羽毛球";

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/apps/badminton/indexPage.jsp")
	public void indexPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// ==========group==========>>>
		// 最近更新小组文章
		List<GroupTopic> updatedGroupTopics = groupTopicService
				.findTopicsByGroupIds(CORE_GROUP_IDS, false, null, 0, 15);
		request.setAttribute("updatedGroupTopicsMap", ListWrapper.getInstance()
				.formatGroupTopic(updatedGroupTopics, false));
		// 最热小组文章
		List<GroupTopic> hotGroupTopics = groupTopicService
				.findTopicsByGroupIds(CORE_GROUP_IDS, false, "hits", 0, 15);
		request.setAttribute("hotGroupTopicsMap", ListWrapper.getInstance()
				.formatGroupTopic(hotGroupTopics, false));

		// <<<==========group==========

		// activity
		List<ActivityModel> activityList = this.activityService.findActivities(
				CORE_GROUP_ID, null, null, null, 0, 10);
		request.setAttribute("activityList", activityList);

		List<Equipment> eList = this.equipmentService.findEquipmentsByShopId(
				EquipmentStateType.OK, CORE_SHOP_ID, 0, 15);
		request.setAttribute("equipmentList", eList);

		// 博客
		List<BlogEntry> entryList = this.entryService.findEntries(CORE_BLOG_ID,
				null, EntryService.EntryType.NORMAL.getType(), 0, 10);
		request.setAttribute("entryList", entryList);

		// 新闻
		List<NewsEntry> nList = this.newsService.findNewses(true,
				NewsStateType.OK.getValue(), CORE_NEWS_ID, 0, 10);

		request.setAttribute("newsList", nList);
	}
}
