package com.galaxii.common.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.dto.ImageMeta;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.CommunityImageSize;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.entity.Status;
import com.galaxii.common.entity.User;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SessionUtil;
import com.galaxii.front.form.CommunityImageForm;

@Scope("prototype")
@Service
public class CommunityImageService extends ImageService {
	
	@Resource
	private CommunityImageDao communityImageDao;
	@Resource
	private CommunityDao communityDao;

    protected static final String IMAGE_DIR = "/community";
    
    public void delete(CommunityImage image) {
    	Community community = communityDao.findById(image.getCommunityId());
    	communityImageDao.delete(image);
    	if(image.getId().equals(community.getId())) {
    		community.setCommunityImageId(null);
    		communityDao.update(community);
    	}
    }
    
    public CommunityImage update(
    		CommunityImage communityImage, CommunityImageForm form, User user) {
    	communityImage.setTitle(form.getTitle());
    	communityImage.setDescription(form.getDescription());
    	communityImageDao.update(communityImage);
    	return communityImage;
    }
    
	public CommunityImage create(
			Integer communityId, CommunityImageForm form, User user) 
			throws Exception {
		
		// create community image
		List<ImageMeta> imageMetas = form.getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			return null;
		}

		for(ImageMeta im : imageMetas) {
			CommunityImage communityImage = new CommunityImage();
			communityImage.setCommunityId(communityId);
			communityImage.setPath(im.getId());
			communityImage.setLink(im.getVendorData());
			communityImage.setType(im.getImageType());
			communityImage.setUserId(user.getId());
			communityImage.setVendorData(im.getVendorData());
			communityImage.setLikeCnt(0);
			communityImage.setCommentCnt(0);
			communityImage.setStatus(Status.LIVE);
			communityImage.setTitle(form.getTitle());
			communityImage.setDescription(form.getDescription());
			Integer id = (Integer)communityImageDao.save(communityImage);

			try {
				substantiateTmpImage(communityId, id, im.getId(), user.getId());
			} catch (Exception e) {
				communityImageDao.delete(communityImage);
			}
			return communityImage;
		}
		return null;
	}
	
    public List<ImageMeta> temporarizeExistImage(
    		List<CommunityImage> communityImages) {
    	if(communityImages == null || communityImages.isEmpty()) {
    		return null;
    	}
    	List<ImageMeta> imageMetas = new ArrayList<ImageMeta>();
    	for(CommunityImage row : communityImages) {
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
	        			row.getCommunityId(), row.getId(), is);
	        	FileUtils.copyFile(org, dest);
	        }
	        imageMetas.add(im);
    	  } catch(Exception e) {
    		  continue;
    	  }
    	}
    	return imageMetas;
    }
			
	
	public DbSelectMorePaginator<CommunityImage> factorySessionPaginator(
			SessionUtil sessUtil, Integer communityId, 
			int limit, CommunityContentsCategory communityContentsCategory) {

		DbSelectMorePaginator<CommunityImage> p = new DbSelectMorePaginator<CommunityImage>(
						sessUtil, communityImageDao, communityId);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addOrder(Order.desc("id"));
		p.addCriterion(Restrictions.eq("communityId", communityId));
		if(communityContentsCategory == CommunityContentsCategory.IMAGE) {
			p.addCriterion(Restrictions.or(
					Restrictions.eq("type", ImageType.IMAGE),
					Restrictions.eq("type", ImageType.AMAZON)));
		} else if(communityContentsCategory == CommunityContentsCategory.MOVIE) {
			p.addCriterion(Restrictions.eq("type", ImageType.YOUTUBE));
		}
		return p;
	}
    
    public void substantiateTmpImage(
    		Integer id, Integer imageId, String fileId, Integer userId) throws Exception {
    	File dir = getImageDir(id, imageId);
    	if(!dir.isDirectory()) {
    		dir.mkdirs();
    	}
    	for(CommunityImageSize is : CommunityImageSize.values()) {
    		File tmp = getTmpImageFile(fileId, is, userId);
    		File dest = getImageFile(id, imageId, is);
    		if(!tmp.renameTo(dest)) {
    			throw new Exception();
    		}
        }
    	clearTmpImageDir(userId);
    }
    
	public void deleteAllImageByBaseId(Integer communityId) {
		List<CommunityImage> communityImages = 
				communityImageDao.findByCommunityId(communityId);
		if(communityImages == null || communityImages.isEmpty()) {
			return;
		}
		for(CommunityImage row : communityImages) {
			communityImageDao.delete(row);
			getImageDir(communityId, row.getId()).delete();
		}
	}
    
    public static String getImageSubDir(int id, Integer imageId) {
        int uid1000 = (int)Math.floor( id / 1000 ) * 1000;
        int uid100  = (int)Math.floor( (id - uid1000) / 100 ) * 100 + uid1000;

        return IMAGE_DIR + "/" + uid1000 + "/" + uid100 + "/" + id + "/" + imageId;
    }
    
    public File getImageDir(Integer id, Integer imageId) {
    	return new File(getBaseAbsolutePath() + getImageSubDir(id, imageId));
    }

    public File getImageFile(Integer id, Integer imageId, CommunityImageSize is) {
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
        		"/community-image/read-tmp/"+fileId+"/" + is.getName());
        }
        return ret;
    }

}
