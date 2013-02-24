package org.net9.domain.dao.gallery;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.gallery.ImageComment;

@EntityClass(value = ImageComment.class)
public class ImageCommentDAO extends CachedJpaDAO {
}