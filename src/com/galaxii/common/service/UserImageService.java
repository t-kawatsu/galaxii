package com.galaxii.common.service;

import java.net.URL;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dto.ImageMeta;
import com.galaxii.common.entity.ImageSize;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserImageSize;
import com.galaxii.common.entity.UserImageType;
import com.galaxii.front.form.AbstractImageContentsForm;

@Scope("prototype")
@Service
public class UserImageService extends AbstractUserImageService {

	@Transactional
	public User deleteImage(User user, Integer userImageId) {
		processDeleteImage(user, userImageId);
	   if(userImageId.equals(user.getUserImageId())) {
		   user.setUserImageId(null);
		   userDao.update(user);
	   }
	   return user;
	}
	
	@Transactional
	public User createFromUrl(User user, URL url) throws Exception {
		String fileId = storeTmpImageFromUrl(url, user.getId());   	
		Integer imageId = processCreateImage(fileId, user.getId());
		user.setUserImageId(imageId);
		userDao.update(user);
		return user;
	}

	@Transactional
	public User create(User user, AbstractImageContentsForm form) throws Exception {
		List<ImageMeta> imageMetas = form.getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			return user;
		}
		Integer imageId = null;
		for(ImageMeta im : imageMetas) {
			imageId = processCreateImage(im.getId(), user.getId());
		}
		user.setUserImageId(imageId);
		userDao.update(user);
		return user;
	}

	@Override
	UserImageType getUserImageType() {
		return UserImageType.PERSON;
	}
	
	@Override
	ImageSize[] getImageSizes() {
		return UserImageSize.class.getEnumConstants();
	}
}
