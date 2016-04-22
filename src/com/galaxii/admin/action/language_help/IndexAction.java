package com.galaxii.admin.action.language_help;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageHelpDao;
import com.galaxii.common.entity.LanguageHelp;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.Paginator;
import com.galaxii.common.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageHelpDao languageHelpDao;
	private Integer page;
	private SimplePager<LanguageHelp> pager;

	@Action(value="language-help/index",
		results={@Result(name="success", location="language-help/index.ftl")}
	)
    public String execute() throws Exception {
		Paginator<LanguageHelp> p = new DbSelectPaginator<LanguageHelp>(languageHelpDao);
		p.setLimit(DISP_ITEMS_NUM_PAR_PAGE);
		p.setPage(page);
		pager = p.paginate();
        return SUCCESS;
    }

	public SimplePager<LanguageHelp> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}

