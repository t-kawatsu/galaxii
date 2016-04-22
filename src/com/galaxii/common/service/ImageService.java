package com.galaxii.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.galaxii.common.entity.CommunityImageSize;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.util.ImageUtil;

abstract public class ImageService {
	
	protected final ImageUtil imageUtil = new ImageUtil();
	
	protected final String IMAGE_BASE_URI = "/assets/user/images";
    protected final String TMP_IMAGE_DIR = "/tmp-community";
    protected final String IMAGE_DIR = "/user";
   
    @Value("#{appProperties[ 'app.image.baseDir' ]}")
    protected String imageBaseAbsolutePath;

    public String storeTmpImageFromUrl(URL url, Integer userId)
        	throws Exception {
    	File dest = File.createTempFile(userId +"-"+ url.getPath().hashCode(),"tmp");
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
    
   public String storeTmpImage(File file, Integer userId) 
        	throws Exception {
	   //boolean isSuccess = file.renameTo(getTmpImageFile(ImageSizes.L, userId));
	   //file.deleteOnExit();
       String fileId = String.valueOf( System.currentTimeMillis() );
       File dir = getTmpImageDir(fileId, userId);
       if(!dir.isDirectory()) {
    	   dir.mkdirs();
       }
       for(CommunityImageSize is : CommunityImageSize.values()) {
    	   imageUtil.resize(
    			   file, getTmpImageFile(fileId, is, userId),
    			   is.getWidth(), is.getHeight());
       }
       return fileId;
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
   
   public File getTmpImageFile(String fileId, CommunityImageSize is, Integer userId) {   
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

   abstract public void substantiateTmpImage(
   		Integer id, Integer imageId, String fileId, Integer userId) throws Exception;
   
   abstract public Map<String, Object> formatTmpCreatedData(
   		String fileId, ImageType imageType, String vendorData);
}
