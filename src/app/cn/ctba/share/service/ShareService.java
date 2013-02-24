package cn.ctba.share.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.core.types.SharePrivateStateType;
import org.net9.domain.dao.core.ShareDAO;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.ShareComment;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * share service
 * 
 * @author gladstone
 * @since 2008-10-1
 */
@SessionScoped
public class ShareService extends BaseService {

	private static final long serialVersionUID = -1289947127352251728L;

	static Log log = LogFactory.getLog(ShareService.class);

	@Inject
	private ShareDAO shareDAO;

	/**
	 * delete an Share
	 * 
	 * @param model
	 */
	public void deleteShare(Share model) {
		shareDAO.remove(model);
	}

	public void deleteShareComment(ShareComment model) {
		shareCommentDAO.remove(model);
	}

	@SuppressWarnings("unchecked")
	public List findAllShares(Integer type, Integer start, Integer limit) {
		String jpql = "select model from Share model  where model.id>0 and model.state="
				+ SharePrivateStateType.PUBLIC.getValue();
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		jpql += " order by model.id desc ";
		return shareDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ShareComment> findShareComments(String username,
			Integer shareId, boolean reverse, Integer start, Integer limit) {
		String jpql = "select model from ShareComment model  where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (shareId != null) {
			jpql += " and model.share.id =" + shareId;
		}
		if (reverse) {
			jpql += " order by model.id desc ";
		} else {
			jpql += " order by model.id asc ";
		}

		return shareCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据分享者得到评论
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShareComment> findShareCommentsByOwner(String username,
			Integer start, Integer limit) {
		String jpql = "select model from ShareComment model  where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.share.username ='" + username + "'";
		}
		jpql += " order by model.id desc ";
		return shareCommentDAO.findByStatement(jpql, start, limit);
	}

	public Integer getShareCommentsCntByOwner(String username) {
		String jpql = "select count(model) from ShareComment model  where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.share.username ='" + username + "'";
		}
		return shareCommentDAO.getCntByStatement(jpql);
	}

	/**
	 * 根据用户得到分享列表
	 * 
	 * @param username
	 * @param type
	 * @param state
	 *            注意，根据用户查看的话，需要指定分享公开/不公开状态 See: SharePrivateStateType
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findShares(String username, Integer type, Integer state,
			Integer start, Integer limit) {
		String jpql = "select model from Share model  where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		if (state != null) {
			jpql += " and model.state =" + state;
		}
		jpql += " order by model.id desc ";
		return shareDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List findSharesByUsers(List<String> usernames, Integer type,
			Integer start, Integer limit) {
		String jpql = "select model from Share model  where model.id>0 and model.state="
				+ SharePrivateStateType.PUBLIC.getValue();
		jpql += " and model.username in(";
		for (String username : usernames) {
			jpql += " '" + username + "',";
		}
		jpql = jpql.substring(0, jpql.length() - 1) + ")";

		if (type != null) {
			jpql += " and model.type =" + type;
		}
		jpql += " order by model.id desc ";
		return shareDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public Share getNewestShareByUsername(String username) {
		List<Share> list = this.findShares(username, null,
				SharePrivateStateType.PUBLIC.getValue(), 0, 1);
		return list.size() > 0 ? (Share) list.get(0) : null;
	}

	/**
	 * selete a Share
	 * 
	 * @param sid
	 * @return
	 */
	public Share getShare(Integer sid) {
		return (Share) shareDAO.getByPrimaryKey(Share.class, sid);
	}

	/**
	 * get the count of Shares
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public int getShareCnt(String username, Integer type, Integer state) {
		String jpql = "select count(model) from Share model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		if (state != null) {
			jpql += " and model.state =" + state;
		}
		return shareDAO.getCntByStatement(jpql);
	}

	public ShareComment getShareComment(Integer sid) {
		return (ShareComment) shareCommentDAO.getByPrimaryKey(
				ShareComment.class, sid);
	}

	public int getShareCommentCnt(String username, Integer shareId) {
		String jpql = "select count(model) from ShareComment model  where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (shareId != null) {
			jpql += " and model.share.id =" + shareId;
		}
		return shareCommentDAO.getCntByStatement(jpql);
	}

	/**
	 * save an Share
	 * 
	 * @param model
	 * @param update
	 */
	public void saveShare(Share model) {
		if (model.getId() != null && model.getId() > 0) {
			shareDAO.update(model);
		} else {
			shareDAO.save(model);
		}
	}

	public void saveShareComment(ShareComment model) {
		if (model.getId() != null && model.getId() > 0) {
			shareCommentDAO.update(model);
		} else {
			shareCommentDAO.save(model);
		}
	}
}
