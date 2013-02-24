package org.net9.core.web.struts.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.hit.HitManager;
import org.net9.core.hit.SmartHitStrategy;
import org.net9.core.types.SysBlacklistType;
import org.net9.core.util.HttpUtils;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Forbidden;
import org.net9.domain.model.bbs.Reply;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.bbs.Userboard;
import org.net9.domain.model.core.SysBlacklist;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.ctba.Vote;
import org.net9.search.lucene.SearchConst;
import org.net9.search.lucene.index.BuilderThread;
import org.net9.search.lucene.index.IndexBuilderFactory;

import com.google.inject.Inject;
import com.j2bb.common.search.index.IndexBuilder;
import com.whirlycott.cache.CacheDecorator;
import com.whirlycott.cache.CacheManager;

/**
 * BBS管理Action
 * 
 * @author gladstone
 * @since 2008-8-3
 */
public class ManageAction extends BusinessDispatchAction {

	static Log log = LogFactory.getLog(ManageAction.class);

	@Inject
	private HitManager hitManager;

	/**
	 * board managers
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward bms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<Userboard> users = userService.listBms(null, start, limit);
		List models = new ArrayList();
		if (users != null) {
			for (Userboard ub : users) {
				User u = userService.getUser(ub.getUser_id(), null);
				Board b = boardService.getBoard(ub.getBoard_id());
				Map m = new HashMap();
				m.put("user", u);
				m.put("board", b);
				m.put("ub", ub);
				models.add(m);
			}
		}
		request.setAttribute("models", models);
		request.setAttribute("count", userService.getBmsCnt(null));
		return mapping.findForward("manage.bms");
	}

	public ActionForward cacheInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("blogEntryHitMapCnt",
				SmartHitStrategy.blogEntryHitMap.entrySet().size());
		request.setAttribute("blogHitMapCnt", SmartHitStrategy.blogHitMap
				.entrySet().size());
		request.setAttribute("groupHitMapCnt", SmartHitStrategy.groupHitMap
				.entrySet().size());
		request.setAttribute("groupTopicHitMapCnt",
				SmartHitStrategy.groupTopicHitMap.entrySet().size());
		request.setAttribute("topicHitMapCnt", SmartHitStrategy.topicHitMap
				.entrySet().size());
		request.setAttribute("cacheEfficiencyReport",
				((CacheDecorator) CacheManager.getInstance().getCache())
						.getEfficiencyReport());
		return mapping.findForward("manage.cacheinfo");
	}

	/**
	 * deal with topics , including delete,top or...
	 * 
	 * 加上转移文章
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward dealTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dealType = request.getParameter("dealType");
		String translateToBoard = request.getParameter("translateToBoard");
		log.debug("get dealType:" + dealType);
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			if (name.indexOf("check_") >= 0) {
				String id = name.substring("check_".length());
				log.debug("get id:" + id);
				if ("re".equals(dealType)) {
					topicService.dealRe(new Integer(id));
				} else if ("delete".equals(dealType)) {
					topicService.deleteTopic(topicService
							.getTopic((new Integer(id))));
				} else if ("top".equals(dealType)) {
					topicService.dealTop(new Integer(id));
				} else if ("remind".equals(dealType)) {
					topicService.dealRemind(new Integer(id));
				} else if ("prime".equals(dealType)) {
					topicService.dealPrimer(new Integer(id));
				} else if (StringUtils.isNotEmpty(translateToBoard)) {
					Board tarBoard = boardService.getBoard(new Integer(
							translateToBoard));
					Topic topic = topicService.getTopic(Integer.valueOf(id));
					log.debug("translate topic: " + topic.getTopicTitle());
					Board srcBoard = boardService.getBoard(topic
							.getTopicBoardId());
					topic.setTopicBoardId(tarBoard.getBoardId());
					topicService.saveTopic(topic, true);
					// TODO: Change this performance
					// TODO: 配合BoardAction整理
					tarBoard.setBoardTopicNum(tarBoard.getBoardTopicNum() + 1);
					srcBoard.setBoardTopicNum(srcBoard.getBoardTopicNum() - 1);
					List<Reply> replies = replyService.findReplys(topic
							.getTopicId().intValue(), 0,
							BusinessConstants.MAX_INT);
					for (Reply r : replies) {
						r.setTopicBoardId(tarBoard.getBoardId());
						replyService.saveReply(r);
						tarBoard.setBoardReNum(tarBoard.getBoardReNum() + 1);
						srcBoard.setBoardReNum(srcBoard.getBoardReNum() - 1);
					}
					boardService.saveBoard(tarBoard, true);
					boardService.saveBoard(srcBoard, true);
				}
			}
		}
		return new ActionForward("/manage/manage.do?method=listTopics", true);
	}

	/**
	 * 删除黑名单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteBlack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		SysBlacklist model = systemService.getSysBlacklist(Integer.valueOf(id));
		if (model != null) {
			systemService.deleteSysBlacklist(model);
		}
		return new ActionForward("manage.shtml?method=listBlacks", true);
	}

	/**
	 * 删除回复
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String tid = request.getParameter("tid");
		Reply model = replyService.getReply(Integer.valueOf(id));
		replyService.delReply(model);
		String url = "/manage/manage.do?method=listReplies";
		if (StringUtils.isNotEmpty(tid)) {
			url += "&tid=" + tid;
		}
		return new ActionForward(url, true);
	}

	public ActionForward deleteUserLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		UserLog model = this.userService.getUserLog(Integer.valueOf(id));
		if (model != null) {
			this.userService.deleteUserLog(model);
		}
		return new ActionForward("manage.shtml?method=timeline", true);
	}

	/**
	 * 刪除投票
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		Vote model = voteService.getVote(new Integer(vid));
		voteService.delVote(model);
		return new ActionForward("manage.shtml?method=listVotes", true);
	}

	/**
	 * delete a forbidden
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delForbidden(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fbnIdStr = request.getParameter("fbnId");
		boardService.delForbedden(new Integer(fbnIdStr));
		return new ActionForward("/manage/manage.do?method=listForbiddens",
				true);
	}

	/**
	 * get the content of a theme
	 * 
	 * @param pathStr
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getThemeContentStr(String pathStr, HttpServletRequest request) {
		String content = "";
		File f = new File(request.getSession().getServletContext().getRealPath(
				"theme/" + pathStr + "/general.css"));
		if (f.exists()) {
			try {
				FileReader fr = new FileReader(f.getAbsolutePath());
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				while (line != null) {
					content += line + "\n";
					line = br.readLine();
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("index rebuildding...");
		long startMill = System.currentTimeMillis();
		IndexBuilder[] builders = new IndexBuilder[] {
				IndexBuilderFactory.createBbsIndexBuilder(new File(
						SearchConst.INDEXPATH_TOPIC)),
				IndexBuilderFactory.createGroupIndexBuilder(new File(
						SearchConst.INDEXPATH_GROUP)),
				IndexBuilderFactory.createBlogIndexBuilder(new File(
						SearchConst.INDEXPATH_BLOG)),
				IndexBuilderFactory.createNewsIndexBuilder(new File(
						SearchConst.INDEXPATH_NEWS)),
				IndexBuilderFactory.createSubjectIndexBuilder(new File(
						SearchConst.INDEXPATH_SUBJECT)) };
		List<Thread> threadList = new ArrayList<Thread>();
		for (IndexBuilder builder : builders) {
			Thread t = new Thread(new BuilderThread(builder));
			t.start();
			threadList.add(t);
		}
		// for (Object t : threadList.toArray()) {
		// try {
		// ((Thread) t).join();
		// } catch (InterruptedException e) {
		// log.error(e.getMessage());
		// e.printStackTrace();
		// }
		// }
		log
				.info("this build cost： "
						+ (System.currentTimeMillis() - startMill));
		return new ActionForward("/manage/manage.do?method=listTopics", true);
	}

	/**
	 * black list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listBlacks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		String ip = "";
		String username = "";
		if (HttpUtils.isMethodPost(request)) {
			ip = request.getParameter("ip");
			username = request.getParameter("username");
			request.getSession().setAttribute("ip", ip);
			request.getSession().setAttribute("username", username);
		} else {
			ip = (String) request.getSession().getAttribute("ip");
			username = (String) request.getSession().getAttribute("username");
		}
		List<SysBlacklist> blackList = systemService.findSysBlacklist(username,
				ip, start, limit);
		Integer cnt = systemService.getSysBlacklistCnt(username, ip);
		request.setAttribute("blackList", blackList);
		request.setAttribute("count", cnt);
		return mapping.findForward("manage.blackList");
	}

	/**
	 * list boards
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listBoards(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String searchKey;
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		if (HttpUtils.isMethodPost(request)) {
			searchKey = request.getParameter("searchKey");
			request.getSession().setAttribute("boardsearckKey", searchKey);
		} else {
			searchKey = (String) request.getSession().getAttribute(
					"boardsearckKey");
		}
		request.setAttribute("searchKey", searchKey);
		List l = boardService.findBoards(null, searchKey, start, limit);
		request.setAttribute("models", l);
		request
				.setAttribute("count", boardService
						.getBoardCnt(null, searchKey));
		return mapping.findForward("manage.listBoards");
	}

	/**
	 * list the forbiddens
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listForbiddens(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String boardId = request.getParameter("bid");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		Integer bid = null;
		if (CommonUtils.isNotEmpty(boardId)
				&& !"null".equalsIgnoreCase(boardId)) {
			bid = new Integer(boardId);
		}
		List list = boardService.findForbiddens(bid, start, limit);
		List models = new ArrayList<Map>();
		if (list != null) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Forbidden model = (Forbidden) it.next();
				Board board = boardService.getBoard(model.getFbnBoardId());
				Map map = new HashMap();
				map.put("forbidden", model);
				map.put("board", board);
				models.add(map);
			}
		}
		request.setAttribute("models", models);
		request.setAttribute("count", boardService.getForbiddenCnt(bid));
		return mapping.findForward("manage.forbiddens");
	}

	/**
	 * list replies
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listReplies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tid = request.getParameter("tid");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List topics;
		if (StringUtils.isNotEmpty(tid)) {
			request.setAttribute("parentTopic", topicService.getTopic(Integer
					.valueOf(tid)));
			topics = replyService
					.findReplys(Integer.valueOf(tid), start, limit);
			request.setAttribute("count", replyService
					.getRepliesCntByTopic(Integer.valueOf(tid)));
		} else {
			topics = replyService.findAllReplys(start, limit);
			request.setAttribute("count", replyService.getRepliesCnt());
		}
		List models = new ArrayList();
		if (topics != null) {
			Iterator it = topics.iterator();
			while (it.hasNext()) {
				Reply model = (Reply) it.next();
				Board board = boardService.getBoard(model.getTopicBoardId());
				Topic t = topicService.getTopic(model.getTopicOriginId());
				if (board == null || t == null) {
					continue;
				}
				Map map = new HashMap();
				map.put("topic", t);
				map.put("reply", model);
				map.put("board", board);
				models.add(map);
			}
		}

		request.setAttribute("models", models);
		return mapping.findForward("manage.listReplies");
	}

	/**
	 * 文章列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String boardIdStr = request.getParameter("bid");
		Integer boardId = null;
		if (CommonUtils.isNotEmpty(boardIdStr)) {
			boardId = new Integer(boardIdStr);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List topics = topicService.findTopics(boardId, start, limit, null);
		List models = new ArrayList();
		if (topics != null) {
			Iterator it = topics.iterator();
			while (it.hasNext()) {
				Topic model = (Topic) it.next();
				Board board = boardService.getBoard(model.getTopicBoardId());
				if (board == null) {
					continue;
				}
				Map map = new HashMap();
				map.put("topicTag", "../images/mark/remind.gif");
				map.put("topic", model);
				map.put("board", board);
				models.add(map);
			}
		}

		int topicCnt = topicService.getTopicCnt(boardId, null);
		request.setAttribute("models", models);
		request.setAttribute("count", topicCnt);

		List<Board> boards = boardService.findBoards(null, null, 0,
				BusinessConstants.MAX_INT);
		List<Board> boardsWithParent = new ArrayList<Board>();
		for (Board b : boards) {
			if (b.getBoardParent() != null && b.getBoardParent() > 0) {
				boardsWithParent.add(b);
			}
		}
		request.setAttribute("boards", boardsWithParent);

		return mapping.findForward("manage.listTopics");
	}

	/**
	 * 管理投票
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listVotes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bidStr = request.getParameter("bid");
		Integer bid = null;
		if (CommonUtils.isNotEmpty(bidStr)) {
			bid = new Integer(bidStr);
			Board board = boardService.getBoard(bid);
			request.setAttribute("board", board);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		Integer count = voteService.getVotesCnt(bid, null, null);
		List<Vote> models = voteService.findVotes(bid, null, null, null, start,
				limit);

		List<Map> voteMapList = new ArrayList<Map>();
		for (Vote v : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			User u = userService.getUser(null, v.getUsername());
			m.put("vote", v);
			m.put("user", u);
			voteMapList.add(m);
		}
		request.setAttribute("voteMapList", voteMapList);
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		return mapping.findForward("vote.list");
	}

	/**
	 * admin logout
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().removeAttribute(BusinessConstants.ADMIN_NAME);
		request.getSession().invalidate();
		return mapping.findForward("manage.login");
	}

	public ActionForward refreshHitMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		hitManager.refreshAll();
		return new ActionForward("/manage/manage.do?method=cacheInfo", true);
	}

	/**
	 * 保存黑名单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveBlack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String ip = request.getParameter("ip");
		String username = request.getParameter("username");
		String reason = request.getParameter("reason");
		if (StringUtils.isNotEmpty(username) || StringUtils.isNotEmpty(ip)) {
			SysBlacklist model = new SysBlacklist();
			model.setCreateTime(StringUtils.getTimeStrByNow());
			if (Integer.valueOf(type).intValue() == SysBlacklistType.IP
					.getValue()) {
				model.setIp(ip);
				model.setType(SysBlacklistType.IP.getValue());
			} else {
				model.setUsername(username);
				model.setType(SysBlacklistType.USER.getValue());
			}
			model.setReason(reason);
			systemService.saveSysBlacklist(model);
		}
		return new ActionForward("manage.shtml?method=listBlacks", true);
	}

	/**
	 * set a theme content
	 * 
	 * @param pathStr
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private void setThemeContentStr(String pathStr, String content,
			HttpServletRequest request) {
		File f = new File(request.getSession().getServletContext().getRealPath(
				"theme/" + pathStr + "/general.css"));
		if (f.exists()) {
			f.delete();
		}
		try {
			FileWriter fw = new FileWriter(f.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * #848 (后台增加新鲜事管理)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward timeline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<UserLog> logs = userService.findUserlogs(null, null, null, start,
				limit, UserEventWrapper.DEFAULT_WANTED_TYPES);
		request.setAttribute("models", ListWrapper.getInstance()
				.formatUserLogList(logs));
		request.setAttribute("count", userService.getUserlogsCnt(null, null,
				null, UserEventWrapper.DEFAULT_WANTED_TYPES));
		return mapping.findForward("manage.timeline.list");
	}

	/**
	 * manage the users ,including raising a board manager. the list is the same
	 * as the search frame
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward users(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("key");
		String optionType = request.getParameter("userOption");
		Integer option = null;
		if (CommonUtils.isNotEmpty(optionType)) {
			option = new Integer(optionType);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<User> users = userService
				.findUsers(option, username, start, limit);
		List<Map> userMapList = new ArrayList<Map>();
		for (User u : users) {
			Map m = new HashMap<String, Object>();
			m.put("user", u);
			m.put("mainUser", userService.getUser(u.getUserName()));
			// #852 (CTBA社区财富(CTB))
			Integer baseScore = this.calculateBaseScore(u.getUserName());
			Integer score = u.getUserScore() == null ? 0 : u.getUserScore();
			m.put("userScore", baseScore + score);
			userMapList.add(m);
		}
		request.setAttribute("models", users);
		request.setAttribute("userMapList", userMapList);
		request.setAttribute("key", username);
		request.setAttribute("count", userService.getUserCnt(option, username));
		return mapping.findForward("manage.users");
	}

}
