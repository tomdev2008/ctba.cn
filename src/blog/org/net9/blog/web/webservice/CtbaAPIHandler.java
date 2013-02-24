package org.net9.blog.web.webservice;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.net9.common.util.Md5Utils;
import org.net9.common.util.StringUtils;
import org.net9.core.mail.MailSender;
import org.net9.core.types.MessageBoxType;
import org.net9.core.types.SharePrivateStateType;
import org.net9.core.types.ShareType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.UserHelper;
import org.net9.core.util.ValidateHelper;
import org.net9.core.wrapper.MobileUserEventWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Message;
import org.net9.domain.model.core.Notice;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;

/**
 * ctba webservice api
 * 
 * @author Gladstone Chen
 */
public class CtbaAPIHandler extends BaseAPIHandler {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(CtbaAPIHandler.class);

	/**
	 * 删除一条分享
	 * 
	 * @param postid
	 * @param userid
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean deleteShare(String postid, String userid, String password)
			throws Exception {

		logger.debug("editPost() Called ========[ SUPPORTED ]=====");
		logger.debug("     PostId: " + postid);
		logger.debug("     UserId: " + userid);

		if (validateUser(userid, password)) {
			try {
				Share model = shareService.getShare(Integer.valueOf(postid));
				shareService.deleteShare(model);
				return true;
			} catch (Exception e) {
				String msg = "ERROR in CtbaAPIHandler.deleteShare";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return false;
	}

	/**
	 * 获得用户的分享
	 * 
	 * @param userid
	 * @param password
	 * @param numposts
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public Object getShares(String userid, String password, int numposts)
			throws Exception {

		logger.debug("getShares() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		logger.debug("     Number: " + numposts);
		if (validateUser(userid, password)) {
			try {
				Vector results = new Vector();

				List<Share> list = shareService.findShares(null, null,
						SharePrivateStateType.PUBLIC.getValue(), 0, numposts);

				for (Share s : list) {
					Hashtable result = new Hashtable();
					result.put("content", s.getLabel());
					result.put("url", s.getUrl());
					result.put("commentCnt", this.shareService
							.getShareCommentCnt(null, s.getId()));

					result.put("dateCreated", s.getUpdateTime());
					result.put("userid", userid);
					results.add(result);
				}
				return results;
			} catch (Exception e) {
				String msg = "ERROR in CtbaAPIHandler.getShares";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return null;
	}

	/**
	 * 获得用户的系统提醒
	 * 
	 * @param userid
	 * @param password
	 * @param numposts
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getNotices(String userid, String password, int numposts)
			throws Exception {

		logger.debug("getNotices() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		logger.debug("     Number: " + numposts);
		if (validateUser(userid, password)) {
			try {
				Vector results = new Vector();

				List<Notice> list = this.userExtService.findNotices(userid,
						null, null, 0, numposts);

				for (Notice model : list) {
					Hashtable result = new Hashtable();
					result.put("content", model.getTitle());
					result.put("url", model.getRefererURL());
					result.put("expired", model.getExpired());

					result.put("dateCreated", model.getUpdateTime());
					result.put("userid", userid);
					results.add(result);
				}
				return results;
			} catch (Exception e) {
				String msg = "ERROR in CtbaAPIHandler.getNotices";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return null;
	}

	/**
	 * 得到用户通用信息
	 * 
	 * <li>信件数
	 * <li>系统提醒数
	 * <li>分享数
	 * <li>站内动态数
	 * <li>用户图片url
	 * 
	 * @param userid
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getCommonInfo(String userid, String password)
			throws Exception {

		logger.debug("getCommonInfo() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		if (validateUser(userid, password)) {
			try {
				User user = this.userService.getUser(null, userid);
				Hashtable result = new Hashtable();
				result.put("isEditor", user.getUserIsEditor());
				Integer msgCnt = userService.getMsgsCntByUser(user
						.getUserName(), MessageBoxType.MSG_TYPE_RECEIVE
						.getValue(), true, null);
				result.put("messageCnt", msgCnt);
				Integer noticeCnt = userExtService.getNoticeCnt(user
						.getUserName(), 0, null);

				result.put("noticeCnt", noticeCnt);
				Integer shareCnt = this.shareService.getShareCnt(user
						.getUserName(), null, null);
				result.put("shareCnt", shareCnt);
				result.put("userFace", user.getUserFace());
				result.put("timelineCnt", userExtService.getFriendsUserlogsCnt(
						user.getUserName(), null,
						UserEventWrapper.DEFAULT_WANTED_TYPES));
				return result;
			} catch (Exception e) {
				String msg = "ERROR in CtbaAPIHandler.getCommonInfo";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return null;
	}

	/**
	 * 得到用户站内信件
	 * 
	 * @param userid
	 * @param password
	 * @param numposts
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getMessages(String userid, String password, int numposts)
			throws Exception {

		logger.debug("getMessages() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		logger.debug("     Number: " + numposts);
		if (validateUser(userid, password)) {
			try {
				Vector results = new Vector();
				boolean read = false;
				Boolean marked = false;
				Integer boxType = MessageBoxType.MSG_TYPE_RECEIVE.getValue();
				List<Message> list = userService.findMsgsByUser(userid,
						boxType, read, marked, 0, numposts);
				for (Message model : list) {
					try {
						Hashtable result = new Hashtable();
						result.put("content", model.getMsgContent());
						result.put("from", model.getMsgFrom());
						result.put("read", model.getMsgRead());
						result.put("dateCreated", model.getMsgTime());
						result.put("title", model.getMsgTitle());
						results.add(result);
					} catch (Exception ee) {
						ee.printStackTrace();
						continue;
					}
				}
				return results;
			} catch (Exception e) {
				e.printStackTrace();
				String msg = "ERROR in CtbaAPIHandler.getMessages";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return null;
	}

	/**
	 * 得到站内动态
	 * 
	 * @param userid
	 * @param password
	 * @param numposts
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getTimeline(String userid, String password, int numposts)
			throws Exception {

		logger.debug("getTimeline() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		logger.debug("     Number: " + numposts);
		if (validateUser(userid, password)) {
			try {
				Vector results = new Vector();
				List<UserLog> logs = userExtService.findFriendsUserlogs(userid,
						null, 0, numposts,
						UserEventWrapper.DEFAULT_WANTED_TYPES);
				for (UserLog log : logs) {
					Hashtable result = new Hashtable();
					result.put("dateCreated", log.getUpdateTime());
					result.put("userid", userid);
					try {
						String eventStr = MobileUserEventWrapper.getInstance()
								.wrappeEvent(log);
						result.put("eventStr", eventStr);
						if (StringUtils.isNotEmpty(eventStr)) {
							results.add(result);
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
						continue;
					}
				}
				return results;
			} catch (Exception e) {
				e.printStackTrace();
				String msg = "ERROR in CtbaAPIHandler.getTimeline";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return null;
	}

	/**
	 * 发表新分享
	 * 
	 * @param userid
	 * @param password
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public boolean newShare(String userid, String password, String content)
			throws Exception {

		logger.debug("newShare() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		logger.debug("    Content:\n " + content);

		if (validateUser(userid, password)) {
			try {
				Integer type = ShareType.OTHER_OUTTER.getValue();
				Integer state = SharePrivateStateType.PUBLIC.getValue();
				Share model = new Share();
				model.setLabel(content);
				model.setType(type);
				model.setUpdateTime(StringUtils.getTimeStrByNow());
				model.setUsername(userid);
				model.setUrl(null);
				model.setState(state);
				model.setType(ShareType.OTHER_OUTTER_WORDS_ONLY.getValue());
				shareService.saveShare(model);
				if (model.getState().equals(
						SharePrivateStateType.PUBLIC.getValue())) {
					model = this.shareService.getNewestShareByUsername(userid);
					if (model != null) {
						userService.trigeEvent(
								this.userService.getUser(userid), String
										.valueOf(model.getId()),
								UserEventType.SHARE_NEW_MOBILE);
					}
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				String msg = "ERROR in CtbaAPIHandler.newShare";
				throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
			}
		}
		return false;
	}

	/**
	 * 用户注册
	 * 
	 * @param userid
	 * @param password
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public boolean register(String userid, String password, String email)
			throws Exception {

		logger.debug("register() Called ===========[ SUPPORTED ]=====");
		logger.debug("     UserId: " + userid);
		logger.debug("    password:\n " + password);

		try {
			// 简单认证用户名等
			ValidateHelper.validateSingleField(userid, "regUsername");
			ValidateHelper.validateSingleField(password, "regPassword");
			ValidateHelper.validateSingleField(email, "email");

			password = Md5Utils.getMD5(password);
			MainUser user = userService.getUser(userid);
			// check the username
			if (user != null) {
				return false;
			}
			user = UserHelper.populateMainUser(userid, password, email);
			user = userService.saveMainUser(user);
			User model = UserHelper.populateUser(userid, user.getId());
			userService.saveUser(model, false);

			user.setLoginIp("127.0.0.1");
			user.setLoginTime(StringUtils.getTimeStrByNow());
			userService.saveMainUser(user);
			// 最后发送注册信
			try {
				MailSender.getInstance().sendRegMail(user);
			} catch (Exception e) {
				logger.error(e);
			}
			userService.trigeEvent(this.userService.getUser(userid), userid,
					UserEventType.REGISTER);

			return true;
		} catch (Exception e) {
			String msg = "ERROR in CtbaAPIHandler.register";
			throw new XmlRpcException(UNKNOWN_EXCEPTION, msg);
		}
	}

}
