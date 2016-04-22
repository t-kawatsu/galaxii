package com.galaxii.batch.action.user_information;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;

import com.galaxii.batch.action.AbstractAction;
import com.galaxii.common.dao.UserInformationDao;
import com.galaxii.common.entity.UserInformation;
import com.galaxii.common.entity.UserInformationStatus;
import com.galaxii.common.service.UserInformationService;
import com.galaxii.common.util.SimplePager;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PER_DETECT_NUM = 100;

	@Resource
	private UserInformationService userInformationService;
	@Resource
	private UserInformationDao userInformationDao;

	@Action(value = "user-information/update")
	public String execute() throws Exception {
		SimplePager<UserInformation> pager;
		Integer page = 0;
		Date borderDate = DateUtils.addMinutes(new Date(), -30);
		Long borderTime = borderDate.getTime();
		while (true) {
			page++;
			pager = userInformationService.paginateReadyState(PER_DETECT_NUM, page);
			List<UserInformation> rows = pager.getItems();
			if (rows == null || rows.isEmpty()) {
				break;
			}
			for (UserInformation row : rows) {
				try {
					if(row.getCreatedAt().getTime() <= borderTime){
						row.setStatus(UserInformationStatus.ACCEPT);
						userInformationDao.update(row);
					}
				} catch (Exception e) {
					logger.error("{}", e);
					continue;
				}
			}
		}
		return NONE;
	}

}
