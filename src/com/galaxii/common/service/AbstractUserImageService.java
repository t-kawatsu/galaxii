package com.galaxii.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.dao.UserImageDao;
import com.galaxii.common.entity.ImageSize;
import com.galaxii.common.entity.Status;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserImage;
import com.galaxii.common.entity.UserImageType;
import com.galaxii.common.util.ImageUtil;
import com.galaxii.front.form.AbstractImageContentsForm;

@Service
abstract public class AbstractUserImageService {
//<E extends Enum<E> & ImageSize> {
	
	@Resource
	protected UserDao userDao;
	@Resource
	protected UserImageDao userImageDao;
	
	protected final ImageUtil imageUtil = new ImageUtil();

	protected final String IMAGE_BASE_URI = "/assets/user/images";
    protected final String TMP_IMAGE_DIR = "/tmp-user";
    protected final String IMAGE_DIR = "/user";
   
    @Value("#{appProperties[ 'app.image.baseDir' ]}")
    protected String imageBaseAbsolutePath;
    
    public String storeTmpImageFromUrl(URL url, Integer userId)
        	throws Exception {
    	File dest = File.createTempFile(userId +"-"+ url.getPath(),"tmp");
        dest.deleteOnExit();
        InputStream in = url.openStream();
        OutputStream out = new FileOutputStream(dest);
        try {
        	byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) > 0) {
            	out.write(buf, 0, len);  
            }
            out.flush();
        } finally {  
            out.close();
            in.close();
        }  
        return storeTmpImage(dest, userId);
   }
    
   public String storeTmpImage(File file, Integer userId) throws Exception {
	   file.deleteOnExit();
       String fileId = String.valueOf( System.currentTimeMillis() );
       File dir = getTmpImageDir(fileId, userId);
       if(!dir.isDirectory()) {
    	   dir.mkdirs();
       }
       for(ImageSize is : getImageSizes()) {
    	   imageUtil.trimResize(
    			   file, getTmpImageFile(fileId, is, userId),
    			   is.getWidth(), is.getHeight());
       }
       return fileId;
   }
   
   public void substantiateTmpImage(
	   		Integer userId, Integer imageId, String fileId) throws Exception {
		File dir = getImageDir(userId, imageId);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		for (ImageSize is : getImageSizes()) {
			File tmp = getTmpImageFile(fileId, is, userId);
			File dest = getImageFile(userId, imageId, is);
			if (!tmp.renameTo(dest)) {
				throw new Exception();
			}
		}
		clearTmpImageDir(userId);
   }
   
   protected String getBaseAbsolutePath() {
	   if(imageBaseAbsolutePath == null) {
		   this.imageBaseAbsolutePath = ServletActionContext
				   .getServletContext().getRealPath(IMAGE_BASE_URI);
	   }
	   return this.imageBaseAbsolutePath;
   }
   
   public File getTmpImageDir(String fileId, Integer userId) {   
	   return new File(
			   getBaseAbsolutePath() + TMP_IMAGE_DIR + "/" + userId + "/" + fileId);
   }
   
   public File getTmpImageFile(String fileId, ImageSize is, Integer userId) {   
	   return new File(
			   getBaseAbsolutePath() + TMP_IMAGE_DIR + "/" + userId + "/" + fileId + "/" + is.getName() + ".jpg");
   }

   public boolean clearTmpImageDir(Integer userId) {
	   File dir = new File(
			   getBaseAbsolutePath() + TMP_IMAGE_DIR + "/" + userId);
	   if(dir.isDirectory()) {
		   return dir.delete();
	   }
	   return true;
   }
   
   public String getImageSubDir(int id, Integer imageId) {
       int uid1000 = (int)Math.floor( id / 1000 ) * 1000;
       int uid100  = (int)Math.floor( (id - uid1000) / 100 ) * 100 + uid1000;

       return IMAGE_DIR + "/" + uid1000 + "/" + uid100 + "/" + id + "/" + imageId;
   }
   
   protected File getImageDir(Integer id, Integer imageId) {
	   return new File(
			   getBaseAbsolutePath() + getImageSubDir(id, imageId));
   }

   protected File getImageFile(Integer id, Integer imageId, ImageSize is) {
       return new File(
    		   getBaseAbsolutePath() + getImageSubDir(id, imageId) + "/" + is.getName() + ".jpg");
   }
   
   public Map<String, Object> formatTmpCreatedData(String fileId) {
	   Map<String, Object> ret = new HashMap<String, Object>();
       ret.put("fileId", fileId);
       ret.put("type", getUserImageType());
       ret.put("vendorData", null);
       for(ImageSize is : getImageSizes()) {
       	ret.put(
       		is.getName() + "Src", 
       		"/user-image/read-tmp/"+fileId+"/" + is.getName());
       }
       return ret;
   }
   
   protected Integer processCreateImage(String fileId, Integer userId) 
		   throws Exception {
		UserImage image = new UserImage();
		image.setPath(fileId);
		image.setType(getUserImageType());
		image.setUserId(userId);
		image.setLikeCnt(0);
		image.setStatus(Status.LIVE);
		Integer imageId = (Integer)userImageDao.save(image);

		try {
			substantiateTmpImage(userId, imageId, fileId);
		} catch (Exception e) {
			userImageDao.delete(image);
			throw e;
		} 
		return imageId;
   }
   
   protected void processDeleteImage(User user, Integer userImageId) {
	   if(userImageId == null) {
		   return;
	   }
	   UserImage userImage = userImageDao.findById(userImageId);
	   if(userImage == null) {
		   return;
	   }
	   if(!userImage.getUserId().equals(user.getId())) {
		   return;
	   }
	   userImageDao.delete(userImage);
       for(ImageSize is : getImageSizes()) {
    	   getImageFile(user.getId(), userImageId, is).delete();
       }
   }
   
   abstract UserImageType getUserImageType();
   abstract ImageSize[] getImageSizes();
   abstract public User deleteImage(User user, Integer userImageId);
   abstract public User createFromUrl(User user, URL url)
		   throws Exception;
   abstract public User create(User user, AbstractImageContentsForm form)
		   throws Exception;
   
}
