package com.galaxii.front.action.search;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dto.HotWord;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityCategory;
import com.galaxii.common.service.CommunityService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;
import com.opensymphony.xwork2.interceptor.annotations.Before;

public class CommunityAction extends AbstractAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DISP_PAR_PAGE_NUM = 10; 
	private static final int DISP_HOT_WORDS_NUM = 20;

	@Resource
	private CommunityService communityService;
	private String word;
	private CommunityCategory communityCategory;
	private Integer page;
	private SimplePager<Community> pager;
	private List<HotWord> hotWords;
	
	@Before
	public void beforeAction() {
		// hot words
		hotWords = communityService.detectHotWords(DISP_HOT_WORDS_NUM);
	}

	@Action(value="search/community")
    public String execute() throws Exception {
		pager = communityService.search(word, DISP_PAR_PAGE_NUM, page);
        return SUCCESS;
    }
	
	@Action(value="search/community-category/{communityCategoryName}",
		results={
            @Result(name="success", location="search/community.ftl")
        }   
	)
    public String categoryAction() throws Exception {
		if(communityCategory == null) {
			return ERROR;
		}
		pager = communityService.paginateByCategory(
				communityCategory, DISP_PAR_PAGE_NUM, page);
        return SUCCESS;
    }
	
	public void setCommunityCategoryName(String categoryName) {
		this.communityCategory = CommunityCategory.nameOf(categoryName);
	}
	
	public CommunityCategory getCommunityCategory() {
		return this.communityCategory;
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public SimplePager<Community> getPager() {
		return pager;
	}
	
	public List<HotWord> getHotWords() {
		return hotWords;
	}
}
