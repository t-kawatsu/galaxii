package com.galaxii.front.action.user_information;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserInformation;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int DISP_USER_INFORMATION_NUM = 15;
	
	private SimplePager<UserInformation> userInformations;
	
	@Action(value="user-information/index-ajax",
		results={
            @Result(name="success", location="user-information/index-ajax.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		User user = getCurrentUser();
		if(user == null) {
			return ERROR;
		}
		userInformations = userInformationService
    			.factorySessionPaginator(sessUtil, user.getId(), 
    					DISP_USER_INFORMATION_NUM).clearPage().paginate();
		return SUCCESS;
    }

	@Action(value="user-information/more-ajax",
		results={
            @Result(name="success", location="user-information/_index.ftl")
		}
	)
    public String moreAction() throws Exception {
		User user = getCurrentUser();
		if(user == null) {
			return ERROR;
		}
		userInformations = userInformationService
    			.factorySessionPaginator(sessUtil, user.getId(), 
    					DISP_USER_INFORMATION_NUM).paginate();
		return SUCCESS;
    }
	
    public SimplePager<UserInformation> getUserInformations() {
    	return userInformations;
    }
}