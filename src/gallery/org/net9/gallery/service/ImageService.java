package org.net9.gallery.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.dao.gallery.ImageGalleryDAO;
import org.net9.domain.model.gallery.ImageComment;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.gallery.ImageModel;

import com.google.inject.Inject;

/**
 * image service
 * 
 * @author gladstone
 * 
 */
public class ImageService extends BaseService {

	private static final long serialVersionUID = 3269898271311524715L;
	@Inject
	ImageGalleryDAO imageGalleryDAO;

	static public boolean isGroupImage(ImageModel model) {
		return "g".equalsIgnoreCase(model.getType());
	}

	/**
	 * delete ImageModel
	 * 
	 * @param model
	 */
	public void delImage(ImageModel model) {
		imageModelDAO.remove(model);
	}

	/**
	 * 删除评论
	 * 
	 * @param model
	 */
	public void delImageComment(ImageComment model) {
		imageCommentDAO.remove(model);
	}

	/**
	 * delete ImageGallery
	 * 
	 * @param model
	 */
	public void delImageGallery(ImageGallery model) {
		imageGalleryDAO.remove(model);
	}

	@SuppressWarnings("unchecked")
	public List findFaceImagesByUser(String username, Integer start,
			Integer limit) {
		String jpql = "select model from ImageModel model where model.id>0  and model.username='"
				+ username + "' ";
		jpql += " and model.type='f'";
		jpql += " order by model.id desc";
		return imageModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * get a list of ImageGallery
	 * 
	 * @param name
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageGallery> findGalleries(Integer viewOption,
			String username, Integer start, Integer limit) {
		String jpql = "select model from ImageGallery model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username = '" + username + "'";
		}
		if (viewOption != null) {
			jpql += " and model.viewOption=" + viewOption;
		}
		jpql += " order by model.id desc";
		return imageGalleryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据用户群查看相册
	 * 
	 * @param viewOption
	 * @param usernames
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageGallery> findGalleriesByUsers(Integer viewOption,
			String[] usernames, Integer start, Integer limit) {
		String jpql = "select model from ImageGallery model where model.id>0 ";
		jpql += " and model.username in( ";
		for (String username : usernames) {
			jpql += " '" + username + "',";
		}
		jpql = jpql.substring(0, jpql.length() - 1) + ")";

		if (viewOption != null) {
			jpql += " and model.viewOption=" + viewOption;
		}
		jpql += " order by model.id desc";
		return imageGalleryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到相册列表
	 * 
	 * @param username
	 *            可为空
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageModel> findGalleryImages(String username, Integer start,
			Integer limit) {
		String jpql = "select model from ImageModel model where model.type= 'u' ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username = '" + username + "'";
		}
		jpql += " order by model.id desc";
		return imageModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 查找热门相册
	 * 
	 * @param viewOption
	 * @param username
	 *            可为空
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageGallery> findHotGalleries(Integer viewOption,
			String username, Integer start, Integer limit) {
		String jpql = "select model from ImageGallery model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username = '" + username + "'";
		}
		if (viewOption != null) {
			jpql += " and model.viewOption=" + viewOption;
		}
		jpql += " order by model.hits desc";
		return imageGalleryDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 查找最热相片
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageModel> findHotGalleryImages(String username,
			Integer start, Integer limit) {
		String jpql = "select model from ImageModel model where model.type= 'u' ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username = '" + username + "'";
		}
		jpql += " order by model.hits desc";
		return imageModelDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ImageComment> findImageComments(Integer imageId,
			boolean reverse, Integer start, Integer limit) {
		String jpql = "select model from ImageComment model where model.id>0 ";
		if (imageId != null) {
			jpql += " and model.imageModel.id=" + imageId;
		}
		if (reverse) {
			jpql += " order by model.id desc";
		} else {
			jpql += " order by model.id asc";
		}
		return imageCommentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ImageComment> findGalleryImageComments(boolean reverse,
			Integer start, Integer limit) {
		String jpql = "select model from ImageComment model where model.id>0 and model.imageModel.type='u' ";
		if (reverse) {
			jpql += " order by model.id desc";
		} else {
			jpql += " order by model.id asc";
		}
		return imageCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据相册ID得到评论
	 * 
	 * @param galleryId
	 * @param reverse
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageComment> findImageCommentsByGalleryId(Integer galleryId,
			boolean reverse, Integer start, Integer limit) {
		String jpql = "select model from ImageComment model where model.id>0 ";
		jpql += " and model.imageModel.galleryId=" + galleryId;
		if (reverse) {
			jpql += " order by model.id desc";
		} else {
			jpql += " order by model.id asc";
		}
		return imageCommentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ImageComment> findImageCommentsByGalleryOwner(String username,
			boolean reverse, Integer start, Integer limit) {
		String jpql = "select model from ImageComment model where model.id>0 and model.imageModel.type='u' ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.imageModel.username='" + username + "'";
		}
		if (reverse) {
			jpql += " order by model.id desc";
		} else {
			jpql += " order by model.id asc";
		}
		return imageCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * get a list of ImageModel
	 * 
	 * @param name
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageModel> findImages(String type, Integer groupId,
			Integer start, Integer limit) {
		String jpql = "select model from ImageModel model where model.id>0 ";
		if (groupId != null) {
			jpql += " and model.groupId=" + groupId;
		}
		jpql += " and model.type='" + type + "' order by model.id desc";
		return imageModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * find images by gallery
	 * 
	 * @param galleryId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageModel> findImagesByGallery(Integer galleryId,
			Integer start, Integer limit) {
		String jpql = "select model from ImageModel model where model.galleryId="
				+ galleryId + " order by model.id desc";
		return imageModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * find ImageModel by user
	 * 
	 * @param groupId
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findImagesByUser(Integer groupId, String username,
			Integer start, Integer limit) {
		String jpql = "select model from ImageModel model where model.id>0  and model.username='"
				+ username + "' ";
		if (groupId != null) {
			jpql += " and model.groupId=" + groupId;
		}
		jpql += " order by model.id desc";
		return imageModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * TODO:写的更通用
	 */
	public void flushGalleryCache() {
		this.imageGalleryDAO.flushCache();
	}

	/**
	 * TODO:写的更通用
	 */
	public void flushImageCache() {
		this.imageModelDAO.flushCache();
	}

	/**
	 * get the count of ImageGallery
	 * 
	 * @param name
	 * @return
	 */
	public Integer getGalleryCnt(Integer viewOption, String username) {
		String jpql = "select count(model) from ImageGallery model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username = '" + username + "'";
		}
		if (viewOption != null) {
			jpql += " and model.viewOption=" + viewOption;
		}
		return imageGalleryDAO.getCntByStatement(jpql);
	}

	/**
	 * 根据用户得到相册数目
	 * 
	 * @param viewOption
	 * @param usernames
	 * @return
	 */
	public Integer getGalleryCntByUsername(String username) {
		String jpql = "select count(model) from ImageGallery model where model.id>0 ";
		jpql += " and model.username = '" + username + "'";
		return imageGalleryDAO.getCntByStatement(jpql);
	}

	/**
	 * get a ImageModel
	 * 
	 * @param id
	 *            可以为空，如果为空，返回最新的图片模型
	 * @return
	 */
	public ImageModel getImage(Integer id) {
		if (id == null) {
			List<ImageModel> imgList = findImages("g", null, 0, 1);
			return imgList.size() > 0 ? (ImageModel) imgList.get(0) : null;
		} else {
			return imageModelDAO.findById(id);
		}

	}

	public ImageComment getImageComment(Integer id) {
		return (ImageComment) imageCommentDAO.getByPrimaryKey(
				ImageComment.class, id);
	}

	public Integer getImageCommentCnt(Integer imageId) {
		String jpql = "select count(model) from ImageComment model where model.id>0 ";
		if (imageId != null) {
			jpql += " and model.imageModel.id=" + imageId;
		}
		return imageCommentDAO.getCntByStatement(jpql);
	}

	/**
	 * get a ImageGallery
	 * 
	 * @param id
	 *            可以为空，如果为空，返回最新
	 * @return
	 */
	public ImageGallery getImageGallery(Integer id) {
		if (id == null) {
			List<ImageGallery> list = findGalleries(null, null, 0, 1);
			return list.size() > 0 ? (ImageGallery) list.get(0) : null;
		} else {
			return imageGalleryDAO.findById(id);
		}
	}

	/**
	 * get the count of ImageModel
	 * 
	 * @param name
	 * @return
	 */
	public Integer getImagesCnt(Integer groupId, String username) {
		String jpql = "select count(model) from ImageModel model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username = '" + username + "'";
		}
		if (groupId != null) {
			jpql += " and model.groupId=" + groupId;
		}
		jpql += " order by model.id desc";
		return imageModelDAO.getCntByStatement(jpql);
	}

	/**
	 * 根据相册ID得到照片数量
	 * 
	 * @param galleryId
	 * @return
	 */
	public Integer getImagesCntByGallery(Integer galleryId) {
		String jpql = "select count(model) from ImageModel model where model.galleryId="
				+ galleryId;
		return imageModelDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到相册照片的全部数量
	 * 
	 * @return
	 */
	public Integer getGalleryImageCnt() {
		String jpql = "select count(model) from ImageModel model where model.type= 'u' ";
		return imageModelDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到下一张照片
	 * 
	 * @param id
	 * @param galleryId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ImageModel getNextImage(Integer id, Integer galleryId) {
		String jpql = "select model from ImageModel model where model.id >"
				+ id + " and model.galleryId=" + galleryId
				+ " order by model.id asc";
		List<ImageModel> l = this.imageModelDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? l.get(0) : null;
	}

	/**
	 * 得到上一张照片
	 * 
	 * @param id
	 * @param galleryId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ImageModel getPrevImage(Integer id, Integer galleryId) {
		String jpql = "select model from ImageModel model where model.id <"
				+ id + " and model.galleryId=" + galleryId
				+ " order by model.id desc";
		List<ImageModel> l = this.imageModelDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? l.get(0) : null;
	}

	/**
	 * 得到小组的下一张照片
	 * 
	 * @param id
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ImageModel getNextImageInGroup(Integer id, Integer groupId) {
		String jpql = "select model from ImageModel model where model.id >"
				+ id + " and model.groupId=" + groupId
				+ " order by model.id asc";
		List<ImageModel> l = this.imageModelDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? l.get(0) : null;
	}

	/**
	 * 得到小组的上一张照片
	 * 
	 * @param id
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ImageModel getPrevImageInGroup(Integer id, Integer groupId) {
		String jpql = "select model from ImageModel model where model.id <"
				+ id + " and model.groupId=" + groupId
				+ " order by model.id desc";
		List<ImageModel> l = this.imageModelDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? l.get(0) : null;
	}

	/**
	 * save ImageModel
	 * 
	 * @param model
	 */
	public void saveImage(ImageModel model) {
		if (model.getId() == null) {
			imageModelDAO.save(model);
		} else {
			imageModelDAO.update(model);
		}
	}

	/**
	 * 保存评论
	 * 
	 * @param model
	 */
	public void saveImageComment(ImageComment model) {
		if (model.getId() == null) {
			imageCommentDAO.save(model);
		} else {
			imageCommentDAO.update(model);
		}
	}

	/**
	 * save ImageGallery
	 * 
	 * @param model
	 */
	public void saveImageGallery(ImageGallery model) {
		if (model.getId() == null) {
			imageGalleryDAO.save(model);
		} else {
			imageGalleryDAO.update(model);
		}
	}
}
