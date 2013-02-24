package cn.ctba.app.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.dao.ctba.OrderModelDAO;
import org.net9.domain.model.ctba.OrderModel;

import com.google.inject.Inject;

public class OrderService extends BaseService {

	private static final long serialVersionUID = 4696501070531311966L;
	@Inject
	private OrderModelDAO orderModelDAO;

	public void deleteOrder(OrderModel model) {
		orderModelDAO.remove(model);
	}

	@SuppressWarnings("unchecked")
	public List<OrderModel> findOrderModels(String username, Integer start,
			Integer limit) {
		if (StringUtils.isEmpty(username)) {
			String jpql = "select model from OrderModel model order by id desc";
			return orderModelDAO.findByStatement(jpql, start, limit);
		} else {
			String jpql = "select model from OrderModel model where model.username='"
					+ username + "' order by model.id desc";
			return orderModelDAO.findByStatement(jpql, start, limit);
		}
	}

	public OrderModel getOrderModel(Integer id) {
		return orderModelDAO.findById(id);
	}

	public OrderModel getNewestOrderModelByUsername(String username) {
		List<OrderModel> l = this.findOrderModels(username, 0, 1);
		return l.size() > 0 ? l.get(0) : null;
	}

	public Integer getOrderModelsCnt(String username) {
		if (StringUtils.isEmpty(username)) {
			String jpql = "select count(model) from OrderModel model ";
			return orderModelDAO.getCntByStatement(jpql);
		} else {
			String jpql = "select count(model) from OrderModel model where model.username='"
					+ username + "'";
			return orderModelDAO.getCntByStatement(jpql);
		}

	}

	public void saveOrderModel(OrderModel model) {
		if (model.getId() == null) {
			orderModelDAO.save(model);
		} else {
			orderModelDAO.update(model);
		}
	}

}
