package com.galaxii.front.action.community;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityCommentDao;
import com.galaxii.common.dao.CommunityCommentLikeDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.dto.EventCalendar;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.service.CommentService;
import com.galaxii.common.service.CommunityEventService;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.common.service.CommunityRecommendService;
import com.galaxii.common.service.CommunityTopicService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.form.CommentForm;

public class ReadAction extends AbstractReadAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_COMMENT_NUM_PAR_PAGE = 10;

	private int DISP_IMAGE_NUM_PAR_PAGE = 15;
	
	private int DISP_TOPICS_NUM_PAR_PAGE = 8;
	
	private int DISP_EVENT_NUM_PAR_PAGE = 100;
	
	private int DISP_RECOMMEND_NUM_PAR_PAGE = 15;

	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityRecommendService communityRecommendService;
	@Resource
	private CommunityTopicService communityTopicService;
	@Resource
	private CommunityEventService communityEventService;
	@Resource
	private CommunityCommentDao commentDao;
	@Resource
	private CommunityCommentLikeDao communityCommentLikeDao;
	@Resource
	private CommunityTopicDao communityTopicDao;
	@Resource
	private CommentForm commentForm;
	
	private SimplePager<CommentEntity> comments;
	
	private SimplePager<CommunityImage> communityImages;
	
	private SimplePager<CommunityTopic> communityTopics;
	
	private SimplePager<CommunityEvent> communityEvents;
	
	private SimplePager<CommunityRecommend> communityRecommends;
	
	private EventCalendar eventCalendar;

	@Actions({
	  @Action(value="community/read/{id}/{contents}",
		results={
			@Result(name="success", location="community/read.ftl"),
			@Result(name="pjax", location="community/_cm-sub-contents.ftl")
		}  
	  ),
	  @Action(value="community/read/{id}",
		results={
			@Result(name="success", location="community/read.ftl"),
			@Result(name="pjax", location="community/_cm-sub-contents.ftl")
		} 
	  )
	})
	@Override
    public String execute() throws Exception {
    	String ret = beforeReadProcess(id);
    	if(!SUCCESS.equals(ret)) {
    		return ret;
    	}
    	
		// contents
		switch(getContents()) {
		case IMAGE:
		case MOVIE:
			communityImages = communityImageService.factorySessionPaginator(
					sessUtil, id, DISP_IMAGE_NUM_PAR_PAGE, getContents()).clearPage().paginate();
			break;
			
		case RECOMMEND:
			communityRecommends = communityRecommendService.factorySessionPaginator(
					sessUtil, id, DISP_RECOMMEND_NUM_PAR_PAGE).clearPage().paginate();
			break;
			
		case REVIEW:
			communityTopics = communityTopicService.factorySessionPaginator(
					sessUtil, id, DISP_TOPICS_NUM_PAR_PAGE, getContents()).clearPage().paginate();
			break;
			
		case EVENT:
			eventCalendar = new EventCalendar(new Date());
			communityEvents = communityEventService.paginateByMonth(
					id, DISP_EVENT_NUM_PAR_PAGE, eventCalendar);
			break;
		case COMMENT:
			comments = CommentService.factorySessionPaginator(
					sessUtil, commentDao, id, DISP_COMMENT_NUM_PAR_PAGE)
					.clearPage().paginate();
				if(getIsLogined()) {
					communityCommentLikeDao.fetchUserLikeState(comments.getItems(), getCurrentUser().getId());
				}
				commentForm.setBaseId(id);
		case HOME:
		default:
			// activates
			communityActivities = communityActivityService.factorySessionPaginator(
					sessUtil, id, DISP_ACTIVITY_NUM_PAR_PAGE).clearPage().paginate();
		}
		if(isPjax()) {
			return PJAX;
		}
		return SUCCESS;
    }
	
	public SimplePager<CommentEntity> getCommunityComments() {
		return comments;
	}
	
	public List<CommentEntity> getComments() {
		return comments != null ? comments.getItems() : null;
	}
	
	public SimplePager<CommunityImage> getCommunityImages() {
		return communityImages;
	}
	
	public SimplePager<CommunityTopic> getCommunityTopics() {
		return communityTopics;
	}
	
	public SimplePager<CommunityEvent> getCommunityEvents() {
		return communityEvents;
	}
	
	public SimplePager<CommunityRecommend> getCommunityRecommends() {
		return communityRecommends;
	}
	
	public CommentForm getCommentForm() {
		return commentForm;
	}

	public EventCalendar getEventCalendar() {
		return eventCalendar;
	}
}