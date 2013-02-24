package org.net9.blog.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.service.BaseService;
import org.net9.domain.model.blog.BlogAddress;

/**
 * address service
 * 
 * @author gladstone
 * 
 */
public class AddressService extends BaseService {
	private static final long serialVersionUID = 1L;
	static Log log = LogFactory.getLog(AddressService.class);

	public void deleteAddress(Integer aid) {
		BlogAddress model = getAddress(aid);
		if (model != null) {
			blogAddressDAO.remove(model);
		}
	}

	@SuppressWarnings("unchecked")
	public List findAddress(Integer start, Integer limit) {
		return blogAddressDAO
				.findByStatement("select model from BlogAddress model order by model.id desc");
	}

	@SuppressWarnings("unchecked")
	public List<BlogAddress> findAddressByUser(String username) {
		return blogAddressDAO
				.findByStatement("select model from BlogAddress model where model.username='"
						+ username + "' order by model.id desc");
	}

	public BlogAddress getAddress(Integer aid) {
		return blogAddressDAO.findById(aid);
	}

	public BlogAddress getAddressByUserAndUrl(String username, String url) {
		return (BlogAddress) blogAddressDAO
				.findSingleByStatement("select model from BlogAddress model where model.username='"
						+ username
						+ "' and model.url='"
						+ url
						+ "' order by model.id desc");
	}

	public Integer getAddressCnt() {
		return blogAddressDAO
				.getCntByStatement("select count(model) from BlogAddress model");
	}

	public Integer getAddressCnt(String username) {
		return blogAddressDAO
				.getCntByStatement("select count(model) from BlogAddress model where model.username='"
						+ username + "'");
	}

	public void saveAddress(BlogAddress model) {
		if (model.getId() == null) {
			blogAddressDAO.save(model);
		} else {
			blogAddressDAO.update(model);
		}
	}
}
