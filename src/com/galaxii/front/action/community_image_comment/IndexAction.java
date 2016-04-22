package com.galaxii.front.action.community_image_comment;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityImageCommentDao;
import com.galaxii.common.dao.CommunityImageCommentLikeDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.service.CommentService;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_COMMENT_NUM_PAR_PAGE = 10;
	
	@Resource
	private CommentService commentService;
	@Resource
	private CommunityImageCommentDao commentDao;
	@Resource
	private CommunityImageCommentLikeDao likeDao;
	
	private SimplePager<CommentEntity> comments;
	
	private Integer id;

	@Action(value="community-image-comment/more-ajax/{id}",
		results={
            @Result(name="success", location="common/_comments.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		DbSelectMorePaginator<CommentEntity> paginator = 
				CommentService.factorySessionPaginator(
					sessUtil, commentDao, id, DISP_COMMENT_NUM_PAR_PAGE);
		comments = paginator.paginate();
		if(getIsLogined()) {
			likeDao.fetchUserLikeState(
					comments.getItems(), getCurrentUser().getId());
		}
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public SimplePager<CommentEntity> getCommunityComments() {
		return comments;
	}
	
	public List<CommentEntity> getComments() {
		return comments.getItems();
	}
}