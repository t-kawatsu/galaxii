package com.galaxii.front.action.common;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.front.action.AbstractAction;

public class LoginAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> uploadResultJson;
	
	@Action(value="common/login",
		results={
			@Result(name="ajax", location="error/401-ajax.ftl"),
			@Result(name="html", location="user/login", type="redirect"),
            @Result(name="json", type="json", params={
					"statusCode", "200",
					"contentType", "text/html",
					"noCache", "true",
					"root", "uploadResultJson"
			})
		}
	)
    public String execute() throws Exception {
		if(!StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
			return "ajax";
		}
		return "html";
    }
	
	public Map<String,Object> getUploadResultJson() {
		return uploadResultJson;
	}
}

