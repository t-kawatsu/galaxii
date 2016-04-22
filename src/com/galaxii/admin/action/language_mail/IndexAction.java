package com.galaxii.admin.action.language_mail;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageMailDao;
import com.galaxii.common.entity.LanguageMail;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.Paginator;
import com.galaxii.common.util.SimplePager;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	private Integer page;
	private SimplePager<LanguageMail> pager;

	@Action(value="language-mail/index",
		results={@Result(name="success", location="language-mail/index.ftl")}
	)
    public String execute() throws Exception {
		Paginator<LanguageMail> p = new DbSelectPaginator<LanguageMail>(languageMailDao);
		p.setLimit(DISP_ITEMS_NUM_PAR_PAGE);
		p.setPage(page);
		pager = p.paginate();
        return SUCCESS;
    }

	public SimplePager<LanguageMail> getPager() {
		return pager;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}

