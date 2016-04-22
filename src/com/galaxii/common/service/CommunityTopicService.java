package com.galaxii.common.service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.dao.CommunityTopicImageDao;
import com.galaxii.common.dto.ImageMeta;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityImageSize;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.entity.CommunityTopicImage;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.entity.Status;
import com.galaxii.common.entity.User;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SessionUtil;
import com.galaxii.front.form.CommunityTopicForm;

@Scope("prototype")
@Service
public class CommunityTopicService extends ImageService {
	
	@Resource
	private CommunityTopicDao communityTopicDao;
	@Resource
	private CommunityTopicImageDao communityTopicImageDao;
	
    protected static final String IMAGE_DIR = "/community-topic";

	public DbSelectMorePaginator<CommunityTopic> factorySessionPaginator(
			SessionUtil sessUtil, Integer communityId, 
			int limit, CommunityContentsCategory communityContentsCategory) {
		DbSelectMorePaginator<CommunityTopic> p = new DbSelectMorePaginator<CommunityTopic>(
						sessUtil, communityTopicDao, communityId);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addAssociation( Association.join("communityTopicImage", JoinType.LEFT_OUTER_JOIN) );
		p.addOrder(Order.desc("id"));
		p.addCriterion(Restrictions.eq("communityId", communityId));
		return p;
	}
	
	public void deleteAllImageByBaseId(Integer id) {
		List<CommunityTopicImage> communityTopicImages = 
				communityTopicImageDao.findByCommunityTopicId(id);
		if(communityTopicImages == null || communityTopicImages.isEmpty()) {
			return;
		}
		for(CommunityTopicImage row : communityTopicImages) {
			communityTopicImageDao.delete(row);
			getImageDir(id, row.getId()).delete();
		}
	}
	
	public void reflectForm(
			CommunityTopicForm communityTopicForm, CommunityTopic communityTopic) throws IllegalAccessException, InvocationTargetException {
    	BeanUtils.copyProperties(communityTopicForm, communityTopic);
    	communityTopicForm.setImageMetas(
    			temporarizeExistImage(communityTopic.getCommunityTopicImages()) );
	}
	
	public CommunityTopic create(
			Integer communityId, CommunityTopicForm form, User user) 
			throws Exception {
		
		// create community
		CommunityTopic topic = new CommunityTopic();
		topic.setTitle(form.getTitle());
		topic.setDescription(form.getDescription());
		topic.setUserId(user.getId());
		topic.setCommunityId(communityId);
		topic.setLikeCnt(0);
		topic.setCommentCnt(0);
		topic.setStatus(Status.LIVE);
		int id = (Integer) communityTopicDao.save(topic);
		
		
		// create community image
		List<ImageMeta> imageMetas = form.getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			return topic;
		}

		Integer communityTopicImageId = null;
		for(ImageMeta im : imageMetas) {
			CommunityTopicImage image = new CommunityTopicImage();
			image.setCommunityTopicId(id);
			image.setPath(im.getId());
			image.setType(im.getImageType());
			image.setUserId(user.getId());
			image.setVendorData(im.getVendorData());
			//image.setLikeCnt(0);
			image.setStatus(Status.LIVE);
			image.setTitle(form.getTitle());
			image.setDescription(form.getDescription());
			Integer imageId = (Integer)communityTopicImageDao.save(image);

			try {
				substantiateTmpImage(id, imageId, im.getId(), user.getId());
			} catch (Exception e) {
				communityTopicImageDao.delete(image);
			}
			communityTopicImageId = imageId;
		}
		
		// update community
		topic.setCommunityTopicImageId(communityTopicImageId);
		communityTopicDao.update(topic);
		
		return topic;
	}
	
	public CommunityTopic update(
			CommunityTopic communityTopic, CommunityTopicForm form, User user) 
			throws Exception {
		
		// create community
		communityTopic.setDescription(form.getDescription());
		communityTopicDao.update(communityTopic);
		
		deleteAllImageByBaseId(communityTopic.getId());
		
		// create community image
		List<ImageMeta> imageMetas = form.getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			communityTopic.setCommunityTopicImageId(null);
			communityTopicDao.update(communityTopic);
			return communityTopic;
		}

		Integer communityTopicImageId = null;
		for(ImageMeta im : imageMetas) {
			CommunityTopicImage image = new CommunityTopicImage();
			image.setCommunityTopicId(communityTopic.getId());
			image.setPath(im.getId());
			image.setType(im.getImageType());
			image.setUserId(user.getId());
			image.setVendorData(im.getVendorData());
			//image.setLikeCnt(0);
			image.setStatus(Status.LIVE);
			image.setTitle(communityTopic.getTitle());
			image.setDescription(form.getDescription());
			Integer imageId = (Integer)communityTopicImageDao.save(image);

			try {
				substantiateTmpImage(
						communityTopic.getId(), imageId, im.getId(), user.getId());
			} catch (Exception e) {
				communityTopicImageDao.delete(image);
			}
			communityTopicImageId = imageId;
		}
		
		// update community
		communityTopic.setCommunityTopicImageId(communityTopicImageId);
		communityTopicDao.update(communityTopic);
		
		return communityTopic;
	}
	
    public void substantiateTmpImage(
    		Integer baseId, Integer imageId, String fileId, Integer userId) throws Exception {
    	File dir = getImageDir(baseId, imageId);
    	if(!dir.isDirectory()) {
    		dir.mkdirs();
    	}
    	for(CommunityImageSize is : CommunityImageSize.values()) {
    		File tmp = getTmpImageFile(fileId, is, userId);
    		File dest = getImageFile(baseId, imageId, is);
    		if(!tmp.renameTo(dest)) {
    			throw new Exception();
    		}
        }
    	clearTmpImageDir(userId);
    }
    
    public List<ImageMeta> temporarizeExistImage(
    		List<CommunityTopicImage> communityTopicImages) {
    	if(communityTopicImages == null || communityTopicImages.isEmpty()) {
    		return null;
    	}
    	List<ImageMeta> imageMetas = new ArrayList<ImageMeta>();
    	for(CommunityTopicImage row : communityTopicImages) {
    	  try {
    		ImageMeta im = new ImageMeta();
    		im.setId(row.getPath());
    		im.setImageType(row.getType());
    		im.setType(row.getType().toString());
    		im.setVendorData(row.getVendorData());

    		File dir = getTmpImageDir(im.getId(), row.getUserId());
	        if(!dir.isDirectory()) {
	    	   dir.mkdirs();
	        }
	        for(CommunityImageSize is : CommunityImageSize.values()) {
	        	File dest = getTmpImageFile(im.getId(), is, row.getUserId());
	        	dest.deleteOnExit();
	        	File org = getImageFile(
	        			row.getCommunityTopicId(), row.getId(), is);
	        	FileUtils.copyFile(org, dest);
	        }
	        imageMetas.add(im);
    	  } catch(Exception e) {
    		  continue;
    	  }
    	}
    	return imageMetas;
    }

    public static String getImageSubDir(int baseId, Integer imageId) {
        int uid1000 = (int)Math.floor( baseId / 1000 ) * 1000;
        int uid100  = (int)Math.floor( (baseId - uid1000) / 100 ) * 100 + uid1000;

        return IMAGE_DIR + "/" + uid1000 + "/" + uid100 + "/" + baseId + "/" + imageId;
    }
    
    private File getImageDir(Integer id, Integer imageId) {
    	return new File(getBaseAbsolutePath() + getImageSubDir(id, imageId));
    }

    private File getImageFile(Integer id, Integer imageId, CommunityImageSize is) {
        return new File(getImageDir(id, imageId).toString() + "/" + is.getName() + ".jpg");
    }
    
    public Map<String, Object> formatTmpCreatedData(
    		String fileId, ImageType imageType, String vendorData) {
    	Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("fileId", fileId);
        ret.put("type", imageType.toString());
        ret.put("vendorData", vendorData);
        for(CommunityImageSize is : CommunityImageSize.values()) {
        	ret.put(
        		is.getName() + "Src", 
        		"/community-topic-image/read-tmp/"+fileId+"/" + is.getName());
        }
        return ret;
    }

}
