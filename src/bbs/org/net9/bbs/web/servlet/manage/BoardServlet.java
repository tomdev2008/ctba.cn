package org.net9.bbs.web.servlet.manage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.ImageUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.core.User;

/**
 * 版面Servlet
 * 
 * @author gladstone
 * 
 */
@WebModule("editBoard")
@SuppressWarnings("serial")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class BoardServlet extends BusinessCommonServlet {

    private static Log log = LogFactory.getLog(BoardServlet.class);

    /**
     * 删除版面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @ReturnUrl(rederect = true, url = "manage.do?method=listBoards")
    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String boardIdStr = request.getParameter("boardId");
        int boardId = Integer.parseInt(boardIdStr);
        boardService.deleteBoard(boardId);
    }

    /**
     * 保存版面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void save(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filePath = "";
        Map map;
        try {
            map = getMultiFile(request, "face");
            filePath = (String) map.get(FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String bidStr = getParameter("boardId");
        String boardName = getParameter("boardName");
        String boardInfo = getParameter("boardInfo");
        String boardParentStr = getParameter("boardParent");
        log.debug(boardParentStr);
        int boardLevel = 1;
        int boardOption = 0;
        int boardParent = -1;
        if (CommonUtils.isNotEmpty(boardParentStr)) {
            boardParent = new Integer(boardParentStr);
        }
        int boardState = 127;
        if (StringUtils.isNotEmpty(bidStr)) {
            int boardId = Integer.parseInt(bidStr);
            if (CommonUtils.isNotEmpty(boardParentStr)) {
                boardParent = new Integer(boardParentStr);
            }
            Board board = boardService.getBoard(boardId);
            board.setBoardInfo(boardInfo);
            board.setBoardLevel(boardLevel);
            board.setBoardName(boardName);
            board.setBoardOption(boardOption);
            board.setBoardParent(boardParent);
            board.setBoardState(boardState);

            if (CommonUtils.isNotEmpty(filePath)) {
                board.setBoardFace(filePath);
                ImageUtils.getDefaultImage(request.getSession().getServletContext().getRealPath(
                        SystemConfigVars.UPLOAD_DIR_PATH + File.separator + filePath), false);
                ImageUtils.getMiniImage(request.getSession().getServletContext().getRealPath(
                        SystemConfigVars.UPLOAD_DIR_PATH + File.separator + filePath), false);
            }
            boardService.saveBoard(board, true);
        } else {
            int boardCnt = boardService.getBoardCnt(null, null);
            String boardCode = "00000" + boardCnt;
            if (boardCode.length() > 5) {
                boardCode = boardCode.substring(boardCode.length() - 5);
            }
            Board model = new Board();
            model.setBoardRecom(0);
            if (CommonUtils.isNotEmpty(filePath)) {
                model.setBoardFace(filePath);
                ImageUtils.getDefaultImage(request.getSession().getServletContext().getRealPath(
                        SystemConfigVars.UPLOAD_DIR_PATH + File.separator + filePath), false);
                ImageUtils.getMiniImage(request.getSession().getServletContext().getRealPath(
                        SystemConfigVars.UPLOAD_DIR_PATH + File.separator + filePath), false);
            }
            model.setBoardInfo(boardInfo);
            model.setBoardLevel(boardLevel);
            model.setBoardName(boardName);
            model.setBoardOption(boardOption);
            model.setBoardParent(boardParent);
            model.setBoardState(boardState);
            model.setBoardTodayNum(0);
            model.setBoardTopicNum(0);
            model.setBoardReNum(0);
            model.setBoardNewTopic(0l);
            model.setBoardCode(boardCode);
            model.setBoardType(0);
            boardService.saveBoard(model, false);
        }
        response.sendRedirect("manage.shtml?method=listBoards");
    }

    /**
     * 提供表單
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @ReturnUrl(rederect = false, url = "/manage/bbs/editBoard.jsp")
    public void form(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("boardForm");
        String bid = request.getParameter("boardId");
        List boardList = boardService.findTopBoards(0,
                BusinessConstants.MAX_INT);
        log.debug(boardList.size());
        request.setAttribute("topBoards", boardList);
        if (CommonUtils.isNotEmpty(bid)) {
            request.setAttribute("model", boardService.getBoard(Integer.valueOf(bid)));
        }
    }

    /**
     * 处理版主
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @ReturnUrl(rederect = true, url = "manage/manage.do?method=bms")
    public void manager(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String boardIdStr = request.getParameter("boardId");
        int boardId = Integer.parseInt(boardIdStr);
        User user = userService.getUser(null, userName);
        Integer userBoardOption = boardService.checkUserRole(user.getUserId(),
                boardId);
        if (userBoardOption != null && userBoardOption >= UserSecurityType.OPTION_LEVEL_MANAGE) {
            log.debug("delete bm:" + userName);
            boardService.delBoradManager(userName, boardId);
            // TODO:确认在其他版面没有担任版主的话，就恢复为Constant.OPTION_LEVEL_USER
            List<Board> boards = boardService.findBoards(null, null, 0,
                    BusinessConstants.MAX_INT);
            boolean off = true;
            for (Board b : boards) {
                if (boardService.isBM(user.getUserName(), b)) {
                    off = false;
                    break;
                }
            }
            if (off) {
                user.setUserOption(UserSecurityType.OPTION_LEVEL_USER);
                userService.saveUser(user, true);
            }
        } else {
            boardService.newBoradManager(userName, boardId);
            int userOption = UserSecurityType.OPTION_LEVEL_MANAGE;
            user.setUserOption(userOption);
        }
        userService.saveUser(user, true);
    }
}
