package org.net9.domain.dao.gallery;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.gallery.ImageGallery;

@EntityClass(value = ImageGallery.class)
public class ImageGalleryDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<ImageGallery> findAll() {
		EntityManagerHelper.log("finding all ImageGallery instances",
				Level.INFO, null);
		try {
			String queryString = "select model from ImageGallery model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ImageGallery findById(Integer id) {
		EntityManagerHelper.log("finding ImageGallery instance with id: " + id,
				Level.INFO, null);
		try {
			ImageGallery instance = getEntityManager().find(ImageGallery.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}