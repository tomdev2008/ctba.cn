package org.net9.bbs.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.service.BaseService;
import org.net9.core.types.UserSecurityType;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Forbidden;
import org.net9.domain.model.bbs.Userboard;
import org.net9.domain.model.core.User;
import org.net9.domain.model.view.ViewHotUser;

import com.google.inject.servlet.SessionScoped;

/**
 * 板块的服务
 * 
 * @author gladstone
 * @since 2008-9-7
 */
@SessionScoped
public class BoardService extends BaseService {

	private static final long serialVersionUID = 1L;

	static Log log = LogFactory.getLog(BoardService.class);

	public static final String BM_SPLITTER = ",";

	public static String bmList2Str(List<String> list) {
		if (list == null || list.size() < 1) {
			return "";
		}
		String reval = "";
		for (int i = 0; i < list.size(); i++) {
			reval += list.get(i) + BM_SPLITTER;
		}
		reval = reval.substring(0, reval.lastIndexOf(BM_SPLITTER));
		return reval;
	}

	public static List<String> bmStr2List(String str) {
		List<String> reval = new ArrayList<String>();
		if (StringUtils.isEmpty(str)) {
			return reval;
		}
		String strs[] = str.split(BM_SPLITTER);
		for (int i = 0; i < strs.length; i++) {
			reval.add(strs[i]);
		}
		return reval;
	}

	/**
	 * 查看用户的状态：是否已经加入，如果加入，权限
	 * 
	 * @param userId
	 * @param boardId
	 * @return 如果返回空的话，表示没有此项
	 */
	public Integer checkUserRole(int userId, int boardId) {
		Integer role = null;
		Userboard model = getUserBoard(userId, boardId);
		if (model != null) {
			role = model.getRole();
		}
		return role;
	}

	/**
	 * del master
	 * 
	 * @param userName
	 * @param boardId
	 * @return
	 */
	public void delBoradManager(String userName, int boardId) {
		Board board = getBoard(boardId);
		User user = userDAO.getUser(userName);
		Userboard userBoard = getUserBoard(user.getUserId(), boardId);
		List<String> bmList = bmStr2List(board.getBoardMaster1());
		for (int i = 0; i < bmList.size(); i++) {
			String s = bmList.get(i);
			if (s.equals(userName)) {
				bmList.remove(i);
				break;
			}
		}
		board.setBoardMaster1(bmList2Str(bmList));
		boardDAO.update(board);
		userBoard.setRole(UserSecurityType.OPTION_LEVEL_USER);
		userboardDAO.update(userBoard);
	}

	public void deleteBoard(Integer boardId) {
		Board model = getBoard(boardId);
		if (model != null) {
			boardDAO.remove(model);
		}
	}

	/**
	 * delete a forbidden
	 * 
	 * @param fbnId
	 * @return
	 */
	public void delForbedden(Integer fbnId) {
		Forbidden model = getForbidden(fbnId);
		if (model != null) {
			forbiddensDAO.remove(model);
		}
	}

	/**
	 * 
	 * @param parentId
	 *            这里是父板块的ID
	 * @param key
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findBoards(Integer parentId, String key, Integer start,
			Integer limit) {
		String jpql = "select b from Board b  where b.boardId>0 ";
		if (CommonUtils.isNotEmpty(key)) {
			jpql += " and b.boardName like '%" + key + "%' ";
		}
		if (parentId != null) {
			jpql += " and b.boardParent='" + parentId + "'";
		}
		jpql += " order by b.boardCode ,b.boardId";
		return boardDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到下面的板块
	 * 
	 * @param parentId
	 * @param key
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Board> findBottomBoards(Integer start, Integer limit) {
		String jpql = "select b from Board b  where b.boardId>0 ";
		jpql += " and b.boardParent>0 ";
		jpql += " order by b.boardCode ,b.boardId";
		return boardDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * list forbiddens
	 * 
	 * @param boardId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findForbiddens(Integer boardId, Integer start, Integer limit) {
		String jpql = "select model from Forbidden model";
		if (boardId != null) {
			jpql += " where model.fbnBoardId=" + boardId;
		}
		List list = forbiddensDAO.findByStatement(jpql, start, limit);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ViewHotUser> findHotUsersForIndex() {
		// List reval = null;
		// reval = userDAO.findByStatement(
		// "select l.username,count(l.id) from UserLog l where l.updateTime>'"
		// + Utility.getDateFromTodayOn(-7)
		// + "' group by l.username order by count(l.id)", 0, 7);

		// reval = userDAO
		// .findByNativeStatement("SELECT
		// u.userId,u.userFace,u.userName,COUNT(l.id) as cnt FROM BBS_USERLOGS l
		// INNER JOIN BBS_USERS u WHERE l.updateTime>'"
		// + Utility.getDateFromTodayOn(-7)
		// + "' AND u.username=l.username GROUP BY l.username ORDER BY cnt DESC
		// LIMIT 0,7");
		// if (reval.size() == 0) {
		// reval = userDAO
		// .findByNativeStatement("SELECT
		// u.userId,u.userFace,u.userName,COUNT(l.id) as cnt FROM BBS_USERLOGS l
		// INNER JOIN BBS_USERS u WHERE u.username=l.username GROUP BY
		// l.username ORDER BY cnt DESC LIMIT 0,7");
		// }
		return this.viewHotUserDAO.findByStatement(
				"select model from ViewHotUser model ", 0, 7);
	}

	/**
	 * top boards
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Board> findTopBoards(Integer start, Integer limit) {
		List l = findBoards(BusinessConstants.TOP_BOARD_PARENT, null, start,
				limit);
		Board empty = new Board();
		empty.setBoardId(-1);
		empty.setBoardName("无");
		l.add(0, empty);
		return l;
	}

	/**
	 * 得到板块
	 * 
	 * @param boardId
	 * @return
	 */
	public Board getBoard(Integer boardId) {
		return (Board) boardDAO.getByPrimaryKey(Board.class, boardId);
	}

	/**
	 * get count of boards
	 * 
	 * @param parentId
	 * @param key
	 * @return
	 */
	public int getBoardCnt(Integer parentId, String key) {
		String jpql = "select count(b) from Board b  where b.boardId>0 ";
		if (CommonUtils.isNotEmpty(key)) {
			jpql += " and b.boardName like '%" + key + "%' ";
		}
		if (parentId != null) {
			jpql += " and b.boardParent=" + parentId;
		}
		return boardDAO.getCntByStatement(jpql);
	}

	/**
	 * get board count acording to industry
	 * 
	 * @param areaId
	 * @return
	 */
	public int getBoardsCntByParent(Integer boardParent) {
		String jpql = "select count(b) from Board b where b.boardParent="
				+ boardParent + " order by b.boardId ";
		return boardDAO.getCntByStatement(jpql);
	}

	/**
	 * get a single forbidden
	 * 
	 * @param fbnId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Forbidden getForbidden(Integer fbnId) {
		String jpql = "select  model from Forbidden model where model.fbnId="
				+ fbnId;
		List<Forbidden> list = forbiddensDAO.findByStatement(jpql, 0, 1);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * get the count of forbiddens
	 * 
	 * @param boardId
	 * @return
	 */
	public int getForbiddenCnt(Integer boardId) {
		String jpql = "select count(model) from Forbidden model";
		if (boardId != null) {
			jpql += " where model.fbnBoardId=" + boardId;
		}
		return forbiddensDAO.getCntByStatement(jpql);
	}

	//
	// /**
	// * get count
	// *
	// * @return
	// */
	// public Integer getForbiddenUsersCntInSite() {
	// String jpql = "select count(model) from User model where
	// model.userIsForbidden>0";
	// return userDAO.getCntByStatement(jpql);
	// }

	// /**
	// * 获取黑名单中与<code>username</code>相似的用户数量
	// *
	// * @param username
	// * @return
	// */
	// public Integer getForbiddenUsersCntLikeName(String username) {
	// String jpql = "select count(model) from User model where
	// model.userIsForbidden>0 and model.userName like '%"
	// + username + "%'";
	// return userDAO.getCntByStatement(jpql);
	// }

	/**
	 * top count
	 * 
	 * @return
	 */
	public Integer getTopBoardsCnt() {
		return getBoardCnt(BusinessConstants.TOP_BOARD_PARENT, null);
	}

	/**
	 * get userboard model
	 * 
	 * @param id
	 * @return
	 */
	public Userboard getUserBoard(int id) {
		return (Userboard) userboardDAO.getByPrimaryKey(Userboard.class, id);
	}

	/**
	 * get userboard model
	 * 
	 * @param userId
	 * @param boardId
	 * @return
	 */
	public Userboard getUserBoard(Integer userId, Integer boardId) {
		String jpql = "select model from Userboard model "
				+ " where model.user_id ='" + userId + "' and model.board_id="
				+ boardId;
		return (Userboard) userboardDAO.findSingleByStatement(jpql);
	}

	/**
	 * check if the user is a manager
	 * 
	 * @param username
	 * @param board
	 * @return
	 */
	public boolean isBM(String username, Board board) {
		List<String> bmList = bmStr2List(board.getBoardMaster1());
		for (String s : bmList) {
			if (s.equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * see a user is forbidden or not
	 * 
	 * @param boardId
	 *            could be null
	 * @param uid
	 *            this could be null while username is not
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isForbiddened(Integer boardId, Integer uid, String username) {
		if (uid != null) {
			User user = (User) userDAO.getByPrimaryKey(User.class, uid);
			username = user.getUserName();
		} else {
			if (CommonUtils.isEmpty(username)) {
				return false;
			}
		}
		String jpql = "select model from Forbidden model ";
		jpql += " where model.fbnUser='" + username + "' ";
		if (boardId != null) {
			jpql += " and  model.fbnBoardId=" + boardId;
		}
		List list = forbiddensDAO.findByStatement(jpql, 0, 1);
		return list != null && list.size() > 0;
	}

	/**
	 * add master
	 * 
	 * @param userName
	 * @param boardId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void newBoradManager(String userName, int boardId) {
		Board board = getBoard(boardId);
		User user = userDAO.getUser(userName);
		Userboard userBoard = getUserBoard(user.getUserId(), boardId);
		List bmList = bmStr2List(board.getBoardMaster1());
		bmList.add(userName);
		board.setBoardMaster1(bmList2Str(bmList));
		boardDAO.update(board);
		if (userBoard != null) {
			userBoard.setRole(UserSecurityType.OPTION_LEVEL_MANAGE);
			userboardDAO.update(userBoard);
		} else {
			userBoard = new Userboard();
			userBoard.setRole(UserSecurityType.OPTION_LEVEL_MANAGE);
			userBoard.setBoard_id(boardId);
			userBoard.setCreate_time(StringUtils.getTimeStrByNow());
			userBoard.setTopic_cnt(0);
			userBoard.setUser_id(user.getUserId());
			userboardDAO.save(userBoard);
		}
	}

	/**
	 * 保存版面
	 * 
	 * @param model
	 * @param update
	 */
	public void saveBoard(Board model, boolean update) {
		if (update) {
			boardDAO.update(model);
		} else {
			boardDAO.save(model);
		}
	}

	/**
	 * save a forbidden
	 * 
	 * @param model
	 * @param update
	 */
	public void saveForbedden(Forbidden model) {
		if (model.getFbnId() != null) {
			forbiddensDAO.update(model);
		} else {
			forbiddensDAO.save(model);
		}
	}

	/**
	 * save userboard model
	 * 
	 * @param model
	 * @param update
	 */
	public void saveUserBoard(Userboard model, boolean update) {
		if (update) {
			userboardDAO.update(model);
		} else {
			userboardDAO.save(model);
		}
	}
}
