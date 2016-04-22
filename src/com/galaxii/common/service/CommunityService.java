package com.galaxii.common.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.dao.CommunityTagDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.dto.CategorizedCommunity;
import com.galaxii.common.dto.HotWord;
import com.galaxii.common.dto.ImageMeta;
import com.galaxii.common.dto.RecommendNews;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityCategory;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.CommunityTag;
import com.galaxii.common.entity.CommunityTagId;
import com.galaxii.common.entity.CommunityUser;
import com.galaxii.common.entity.CommunityUserId;
import com.galaxii.common.entity.Status;
import com.galaxii.common.entity.User;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.service.dig_i.DigIClient;
import com.galaxii.common.service.dig_i.InformationItem;
import com.galaxii.common.util.CollectionUtils;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.form.CommunityForm;

//@Scope("prototype")
@Service
@Transactional
public class CommunityService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityTagDao communityTagDao;
	@Resource
	private CommunityImageDao communityImageDao;
	@Resource
	private DigIClient digIClient;
	
	public Community findById(Integer id) {
		Community community = communityDao.findById(id);
		if(community == null) {
			return null;
		}
		community.setCommunityTags(
				communityTagDao.findByCommunityId(community.getId()) );
		return community;
	}
	
	@CacheEvict(value = "community", allEntries=true)
	public Community create(CommunityForm communityForm, User user) 
		throws Exception {
		
		// create community
		Community community = new Community();
		community.setTitle(communityForm.getTitle());
		community.setDescription(communityForm.getDescription());
		community.setCategoryId(communityForm.getCategoryId());
		community.setUserId(user.getId());
		community.setCommunityUsersCnt(1);
		community.setCommentCnt(0);
		community.setStatus(Status.LIVE);
		int id = (Integer) communityDao.save(community);
		
		// create community user
		CommunityUser communityUser = new CommunityUser();
		CommunityUserId communityUserId = new CommunityUserId();
		communityUserId.setCommunityId(id);
		communityUserId.setUserId(user.getId());
		communityUser.setId(communityUserId);
		communityUserDao.save(communityUser);
		
		// create community tag
		for(String tagName : communityForm.getCommunityTags()) {
			CommunityTag communityTag = new CommunityTag();
			CommunityTagId communityTagId = new CommunityTagId();
			communityTagId.setCommunityId(id);
			communityTagId.setName(tagName);
			communityTag.setId(communityTagId);
			communityTagDao.persist(communityTag);
		}
		
		// create community image
		List<ImageMeta> imageMetas = communityForm.getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			return community;
		}
		Integer communityImageId = null;
		for(ImageMeta im : imageMetas) {
			CommunityImage communityImage = new CommunityImage();
			communityImage.setCommunityId(id);
			communityImage.setPath(im.getId());
			communityImage.setLink(im.getVendorData());
			communityImage.setType(im.getImageType());
			communityImage.setUserId(user.getId());
			communityImage.setVendorData(im.getVendorData());
			communityImage.setLikeCnt(0);
			communityImage.setCommentCnt(0);
			communityImage.setStatus(Status.LIVE);
			communityImageId = (Integer)communityImageDao.save(communityImage);

			try {
				communityImageService.substantiateTmpImage(
					id, communityImageId, im.getId(), user.getId());
			} catch (Exception e) {
				communityImageDao.delete(communityImage);
				communityImageId = null;
			}
		}
		
		// update community
		community.setCommunityImageId(communityImageId);
		communityDao.update(community);
		
		return community;
	}
	
	@CacheEvict(value = "community", allEntries=true)
	public Community update(Community community, CommunityForm communityForm, User user) 
			throws Exception {
		community.setDescription(communityForm.getDescription());
		community.setCategoryId(communityForm.getCategoryId());
		communityDao.update(community);
		
		// remove old tags
		communityTagDao.deleteByCommunityId(community.getId());
		
		// create community tag
		for(String tagName : communityForm.getCommunityTags()) {
			CommunityTag communityTag = new CommunityTag();
			CommunityTagId communityTagId = new CommunityTagId();
			communityTagId.setCommunityId(community.getId());
			communityTagId.setName(tagName);
			communityTag.setId(communityTagId);
			communityTagDao.persist(communityTag);
		}
		
		/*
		 *  イメージが削除された場合は元のイメージを削除し、
		 *  アップロードされている場合は元のイメージと同じものであれば何もせず
		 *  違うものであれば登録しそれをデフォルトイメージにする
		 */
		// create community image
		List<ImageMeta> imageMetas = communityForm.getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			if(community.getCommunityImageId() != null) {
				communityImageDao.deleteById(community.getCommunityImageId());
				community.setCommunityImageId(null);
				communityDao.update(community);
			}
			return community;
		}

		CommunityImage defaultCommunityImage = null;
		if(community.getCommunityImageId() != null) {
			defaultCommunityImage = 
					communityImageDao.findById(community.getCommunityImageId());
		}
		
		Integer communityImageId = null;
		for(ImageMeta im : imageMetas) {
			if(defaultCommunityImage != null && 
					im.getId().equals(defaultCommunityImage.getPath())) {
				return community;
			}
			CommunityImage communityImage = new CommunityImage();
			communityImage.setCommunityId(community.getId());
			communityImage.setPath(im.getId());
			communityImage.setLink(im.getVendorData());
			communityImage.setType(im.getImageType());
			communityImage.setUserId(user.getId());
			communityImage.setVendorData(im.getVendorData());
			communityImage.setLikeCnt(0);
			communityImage.setCommentCnt(0);
			communityImage.setStatus(Status.LIVE);
			communityImageId = (Integer)communityImageDao.save(communityImage);

			try {
				communityImageService.substantiateTmpImage(
					community.getId(), communityImageId, im.getId(), user.getId());
			} catch (Exception e) {
				communityImageDao.delete(communityImage);
				communityImageId = null;
			}
		}
		
		// update community
		community.setCommunityImageId(communityImageId);
		communityDao.update(community);
		
		return community;
	}
	
	public void reflectForm(
			CommunityForm communityForm, Community community) throws IllegalAccessException, InvocationTargetException {
    	BeanUtils.copyProperties(communityForm, community);
    	
    	if(community.getCommunityTags() != null 
    			&& !community.getCommunityTags().isEmpty() ) {
    		List<String> tagNames = new ArrayList<String>();
    		for(CommunityTag row : community.getCommunityTags()) {
    			tagNames.add(row.getId().getName());
    		}
    		communityForm.setCommunityTagsCsv(StringUtils.join(tagNames, ","));
    	}
    	
    	if(community.getCommunityImageId() == null) {
    		return;
    	}
    	CommunityImage image = communityImageDao.findById(
    			community.getCommunityImageId());
    	List<CommunityImage> communityImages = new ArrayList<CommunityImage>();
    	communityImages.add(image);
    	communityForm.setImageMetas( 
    			communityImageService.temporarizeExistImage( communityImages ) );
	}

	public SimplePager<Community> search(String word, Integer limit, Integer page) {
		if(StringUtils.isEmpty(word)) {
			return new SimplePager<Community>(limit, 1, null, 0);
		}
		String escapedWord = word.replaceAll("%","\\\\%").replaceAll("_","\\\\_");
		
		// TODO 全文検索
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE c.title like :title OR ct.id.name like :word ");
		sb.append(" group by c.id ");

		int cnt = communityDao.detectCountValue( communityDao.getSession().createQuery(
				"SELECT COUNT(*) FROM Community c join c.user join c.communityTags ct "
						+ sb.toString())
				.setString("title", "%"+escapedWord+"%")
				.setString("word", escapedWord + "%") );
		
		int _page = page == null ? 1 : (int)page;
		if(cnt == 0) {
			return new SimplePager<Community>(limit, _page, null, cnt);
		}
		
		List<Community> rows = communityDao.resultList( communityDao.getSession().createQuery(
				"SELECT c FROM Community c join fetch c.user join c.communityTags ct "
						+ sb.toString())
				.setString("title", "%"+escapedWord+"%")
				.setString("word", escapedWord + "%")
				.setFirstResult((_page-1) * limit)
				.setMaxResults(limit) );
		
		return new SimplePager<Community>(limit, _page, rows, cnt);
	}
	
	public SimplePager<Community> paginateByCategory(CommunityCategory category, Integer limit, Integer page) {
		if(category == null) {
			return new SimplePager<Community>(limit, 1, null, 0);
		}
		DbSelectPaginator<Community> p =
				new DbSelectPaginator<Community>(communityDao);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addCriterion( Restrictions.eq("categoryId", category));
		p.setLimit(limit);
		p.setPage(page);
		return p.paginate();
	}
	
	public boolean isMember(Community community, Integer userId) {
        return communityUserDao.isExistCommunityUser(
        		community.getId(), userId);
	}
	
	@Cacheable( value = "community#86400", key = "#root.methodName + '_' + #root.args[0].id" )
	public List<RecommendNews> findRecommendNews(
			Community community, Integer limit, Integer page) {
		List<String> words = new ArrayList<String>();
		List<CommunityTag> tags = community.getCommunityTags();
		if(tags == null || tags.isEmpty()) {
			return null;
		}
		for(CommunityTag tag : tags) {
			words.add(tag.getId().getName());
		}
		List<InformationItem> items = 
				digIClient.searchInformations(words.toArray(new String[0]), limit, page);
		if(items == null || items.isEmpty()) {
			return null;
		}
		List<RecommendNews> ret = new ArrayList<RecommendNews>();
		for(InformationItem row : items) {
			RecommendNews news = new RecommendNews();
			news.setTitle(row.getTitle());
			news.setDescription(row.getDescription());
			news.setUrl(row.getLink());
			news.setImageUrl(row.getImageUrl());
			news.setResourceImageUrl(row.getResourceImageUrl());
			news.setResourceName(row.getResourceTitle());
			news.setResourceType(row.getResourceType());
			
			ret.add(news);
		}
		return ret;
	}
	
	public void joinUser(Community community, Integer userId) {
		communityUserDao.create(community.getId(), userId);
		communityDao.getSession().createQuery(
			"UPDATE Community SET communityUsersCnt = communityUsersCnt + 1 WHERE id = :communityId" )
			.setInteger( "communityId", community.getId() )
			.executeUpdate();
	}
	
	public void unJoinUser(Community community, Integer userId) {
		communityUserDao.deleteById(community.getId(), userId);
		communityDao.getSession().createQuery(
			"UPDATE Community SET communityUsersCnt = communityUsersCnt - 1 WHERE id = :communityId" )
			.setInteger( "communityId", community.getId() )
			.executeUpdate();
	}
	
	public SimplePager<CommunityUser> paginateUserCommunities(Integer userId, int limit, Integer page) {
		DbSelectPaginator<CommunityUser> p = 
				new DbSelectPaginator<CommunityUser>(communityUserDao);
		p.setLimit(limit);
		p.setPage(page);
		p.addAssociation( Association.join("community", JoinType.INNER_JOIN) );
		//p.addAssociation("community.communityImage", JoinType.INNER_JOIN);
		p.addCriterion( Restrictions.eq("id.userId", userId) );
		p.addOrder(Order.desc("id"));
		return p.paginate();
	}

	public List<HotWord> detectHotWords(int limit) {
		// TODO AspectJに変更しキャッシュからランダムに取得するロジックをサービス内に移す
		List<HotWord> hotWords =  communityTagDao.findByHotWord(limit *2);
		return CollectionUtils.detectRandom(hotWords, limit);
	}
	
	public List<Community> detectPickups(int limit) {
		// TODO AspectJに変更しキャッシュからランダムに取得するロジックをサービス内に移す
		List<Community> ret = communityDao.findOrderByUsersCnt(limit *2);
		return CollectionUtils.detectRandom(ret, limit);
	}
	
	public List<Community> detectNew(int limit) {
		// TODO AspectJに変更しキャッシュからランダムに取得するロジックをサービス内に移す
		List<Community> ret = communityDao.findOrderByNew(limit *2);
		return CollectionUtils.detectRandom(ret, limit);
	}
	
	public List<CategorizedCommunity> findCategorizedCommunities(int categoryNum, int limit) {
		// TODO AspectJに変更しキャッシュからランダムに取得するロジックをサービス内に移す
		List<CategorizedCommunity> categorizedes = communityDao.findHotCommunityCategories(categoryNum+1);
		categorizedes = CollectionUtils.detectRandom(categorizedes, categoryNum);
		if(categorizedes == null) {
			return null;
		}
		for(CategorizedCommunity cc :categorizedes) {
			List<Community> communities = communityDao.findOrderByUsersCntByCategory(cc.getCategoryId(), limit);
			cc.setCommunities( CollectionUtils.detectRandom(communities, limit) );
			cc.setCommunities( communities );
		}
		return categorizedes;
	}
}
