package com.galaxii.admin.action.inquiry;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.InquiryDao;
import com.galaxii.common.entity.Inquiry;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.Paginator;
import com.galaxii.common.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InquiryDao inquiryDao;
	private Integer page;
	private SimplePager<Inquiry> pager;

	@Action(value="inquiry/index",
		results={@Result(name="success", location="inquiry/index.ftl")}
	)
    public String execute() throws Exception {
		Paginator<Inquiry> p = new DbSelectPaginator<Inquiry>(inquiryDao);
		p.setLimit(DISP_ITEMS_NUM_PAR_PAGE);
		p.setPage(page);
		pager = p.paginate();
        return SUCCESS;
    }

	public SimplePager<Inquiry> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}

