package com.galaxii.common.service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityRecommendDao;
import com.galaxii.common.dto.SiteData;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityImageSize;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.entity.RecommendType;
import com.galaxii.common.entity.Status;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SessionUtil;
import com.galaxii.front.form.CommunityRecommendWebForm;

@Service
public class CommunityRecommendService extends ImageService {
	
	@Resource
	private CommunityRecommendDao communityRecommendDao;
	@Resource
	private CommunityDao communityDao;

    protected static final String IMAGE_DIR = "/community-recommend";
	
	public DbSelectMorePaginator<CommunityRecommend> factorySessionPaginator(
			SessionUtil sessUtil, Integer communityId, int limit) {
		DbSelectMorePaginator<CommunityRecommend> p = new DbSelectMorePaginator<CommunityRecommend>(
						sessUtil, communityRecommendDao, communityId);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addAssociation( Association.join("recommendCommunity", JoinType.LEFT_OUTER_JOIN) );
		p.addOrder(Order.desc("id"));
		p.addCriterion(Restrictions.eq("communityId", communityId));
		return p;
	}
	
	public CommunityRecommend create(
			Integer communityId, CommunityRecommendWebForm form, Integer userId) throws Exception {
    	SiteData siteData = detectSiteData(new URL(form.getUrl()));
    	if(siteData == null) {
    		return null;
    	}
    	
    	CommunityRecommend communityRecommend = new CommunityRecommend();
    	communityRecommend.setCommunityId(communityId);
    	communityRecommend.setTitle(siteData.getTitle());
    	communityRecommend.setDescription(siteData.getDescription());
    	communityRecommend.setLikeCnt(0);
    	communityRecommend.setCommentCnt(0);
    	communityRecommend.setStatus(Status.LIVE);
    	communityRecommend.setUrl(form.getUrl());
    	communityRecommend.setUserId(userId);
    	communityRecommend.setType(RecommendType.SITE);
    	Integer id = (Integer)communityRecommendDao.save(communityRecommend);
    	
    	if(siteData.getImageURLs() != null && !siteData.getImageURLs().isEmpty()) {
    		try {
    			int imageIndex = form.getImageIndex() != null ? form.getImageIndex() : 0;
    			String fileId = storeTmpImageFromUrl(siteData.getImageURLs().get(imageIndex), userId);
    			substantiateTmpImage(id, id, fileId, userId);
    		
    			communityRecommend.setImagePath(String.valueOf(id));
    			communityRecommendDao.update(communityRecommend);
    		} catch (Exception e) {
    			
    		}
    	}
    	
    	return communityRecommend;
	}
	
	public CommunityRecommend create(
			Integer communityId, Integer recommendCommunityId, Integer userId) throws Exception {
		
		Community community = communityDao.findById(recommendCommunityId);
		if(community == null) {
			return null;
		}
    	
    	CommunityRecommend communityRecommend = new CommunityRecommend();
    	communityRecommend.setCommunityId(communityId);
    	communityRecommend.setRecommendCommunityId(recommendCommunityId);
    	communityRecommend.setTitle(community.getTitle());
    	communityRecommend.setDescription(community.getDescription());
    	communityRecommend.setLikeCnt(0);
    	communityRecommend.setCommentCnt(0);
    	communityRecommend.setStatus(Status.LIVE);
    	//communityRecommend.setUrl(null);
    	communityRecommend.setUserId(userId);
    	communityRecommend.setType(RecommendType.COMMUNITY);
    	communityRecommendDao.save(communityRecommend);
    	return communityRecommend;
	}
	
	public SiteData detectSiteData(URL url) throws Exception {
		Document doc = fetch(url);
		if(doc == null) {
			return null;
		}
		SiteData siteData = parseHTMLDocument(doc);
		siteData.setUrl(url.toString());
		return siteData;
	}
	
	private SiteData parseHTMLDocument(Document doc) throws Exception {
		int MAX_IMG_NUM = 10;
		
		SiteData dto = new SiteData();
		
		Elements titles = doc.getElementsByTag("title");
		if(titles != null && !titles.isEmpty()) {
			dto.setTitle(titles.get(0).text());
		}
		
		List<String> imageSrcs = new ArrayList<String>();
		
		Elements metas = doc.getElementsByTag("meta");
		if(metas != null && !metas.isEmpty()) {
			for(Element meta : metas) {
				String content = meta.attr("content");
				if(StringUtils.equalsIgnoreCase("description", meta.attr("name"))) {
					dto.setDescription(content);
				} else if(!StringUtils.isEmpty(content) && imageSrcs.size() < MAX_IMG_NUM && 
						"image".equals(meta.attr("itemprop")) ) {
					imageSrcs.add(content);
				}
			}
		}
		
		Elements links = doc.getElementsByTag("link");
		if(links != null && !links.isEmpty()) {
			for(Element link : links) {
				if("icon".equals(link.attr("rel")) && imageSrcs.size() < MAX_IMG_NUM) {
					if(!StringUtils.isEmpty(link.attr("href"))) {
						imageSrcs.add(link.attr("href"));
					}
				}
			}
		}
		
		Elements images = doc.getElementsByTag("img");
		if(images != null) {
			for(Element img : images) {
				if(MAX_IMG_NUM < imageSrcs.size()) {
					break;
				}
				String src = img.attr("src");
				if(StringUtils.isEmpty(src)) {
					continue;
				}
				imageSrcs.add(src);
			}
		}
		
		if(!imageSrcs.isEmpty()) {
			List<URL> imageURLs = new ArrayList<URL>();
			for(String row : imageSrcs) {
				if(row.startsWith("http")) {
					imageURLs.add(new URL(row));
				} else if(row.startsWith("/")) {
					imageURLs.add(new URL(doc.baseUri() + row));
				}
			}
			dto.setImageURLs(imageURLs);
		}
		
		return dto;
	}
	
	/*
	private SiteData parseHTMLDocument(Document doc) throws Exception {
		SiteData dto = new SiteData();
		NodeList titleNodes = doc.getElementsByTagName("title");
		if(titleNodes != null) {
			titleNodes.item(0).getTextContent();
		}
		NodeList metaNodes = doc.getElementsByTagName("meta");
		if(metaNodes != null) {
			for( int i=0; 0<metaNodes.getLength(); i++) {
				Element elem = (Element)metaNodes.item(i);
				if(StringUtils.equalsIgnoreCase("description", elem.getAttribute("name"))) {
					dto.setDescription(elem.getTextContent());
				}
			}
		}
		NodeList imageNodes = doc.getElementsByTagName("img");
		if(imageNodes != null) {
			List<URL> imageURLs = new ArrayList<URL>();
			for( int i=0; 0<imageNodes.getLength(); i++) {
				Element elem = (Element)imageNodes.item(i);
				String src = elem.getAttribute("src");
				if(StringUtils.isEmpty(src)) {
					continue;
				}
				if(src.startsWith("http")) {
					imageURLs.add(new URL(src));
				} else if(src.startsWith("/")) {
					imageURLs.add(new URL(doc.getBaseURI() + src));
				}
				if(10 < imageURLs.size()) {
					break;
				}
			}
			dto.setImageURLs(imageURLs);
		}
		
		return dto;
	}
	*/
	
    private Document fetch(URL url) throws Exception {
    	
    	Document doc = Jsoup.parse(url, 10 * 1000);
    	return doc;
    	/*
    	InputStream is = null;
        try {
        	HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        	connection.setRequestMethod("GET");
        	connection.setConnectTimeout(10 * 1000);
        	connection.connect();

        	int code = connection.getResponseCode();
        	if(code != HttpURLConnection.HTTP_OK || !"text/html".equals(connection.getContentType())) {
        		return null;
        	}
        	
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            dbf.setFeature("http://xml.org/sax/features/namespaces", false);
			return db.parse(connection.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	if(is != null) {
        		is.close();
        	}
        }
        */
    }

	@Override
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

	@Override
	public Map<String, Object> formatTmpCreatedData(String fileId,
			ImageType imageType, String vendorData) {
		return null;
	}
}
