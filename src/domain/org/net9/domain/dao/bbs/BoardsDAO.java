package org.net9.domain.dao.bbs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.bbs.Board;

@EntityClass(value = Board.class)
public class BoardsDAO extends CachedJpaDAO {

	static Log log = LogFactory.getLog(BoardsDAO.class);

	public static String[] decodeBoardState(int boardState) {
		String[] reval = new String[7];
		for (int i = 0; i < 7; i++)
			reval[i] = "";
		int test = 0;
		test = boardState & 1;
		if (test != 0) {
			reval[0] = "on";
		}
		test = boardState & 2;
		if (test != 0) {
			reval[1] = "on";
		}
		test = boardState & 4;
		if (test != 0) {
			reval[2] = "on";
		}
		test = boardState & 8;
		if (test != 0) {
			reval[3] = "on";
		}
		test = boardState & 16;
		if (test != 0) {
			reval[4] = "on";
		}
		test = boardState & 32;
		if (test != 0) {
			reval[5] = "on";
		}
		test = boardState & 64;
		if (test != 0) {
			reval[6] = "on";
		}
		return reval;
	}

	public static int encodeBoardState(String[] values) {
		if (values.length < 7)
			return 0;
		String top10 = values[0];
		String show = values[1];
		String post = values[2];
		String good = values[3];
		String vote = values[4];
		String read = values[5];
		String attach = values[6];

		int boardState = 0;
		if (top10 != null && top10.equals("on"))
			boardState += 1;
		if (show != null && show.equals("on"))
			boardState += 2;
		if (post != null && post.equals("on"))
			boardState += 4;
		if (good != null && good.equals("on"))
			boardState += 8;
		if (vote != null && vote.equals("on"))
			boardState += 16;
		if (read != null && read.equals("on"))
			boardState += 32;
		if (attach != null && attach.equals("on"))
			boardState += 64;
		return boardState;
	}

}
