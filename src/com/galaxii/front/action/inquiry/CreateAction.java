package com.galaxii.front.action.inquiry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.InquiryDao;
import com.galaxii.common.entity.Inquiry;
import com.galaxii.common.service.MailService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.InquiryForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private InquiryDao inquiryDao;
    @Resource
    private InquiryForm inquiryForm;
    @Resource
    private MailService mailService;
	
	@Action(value="inquiry/create",
		results={
			@Result(name="input", location="inquiry/create.ftl"),
            @Result(name="success", location="inquiry/create-complete", type="redirect")
		}
	)
	@Override
	public String execute() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!inquiryForm.validate(this)) {
            return INPUT;
        }   

        // save
        Inquiry inquiry = new Inquiry();
        BeanUtils.copyProperties(inquiry, inquiryForm);
        inquiry.setUseragent(request.getHeader("User-Agent"));
        if(getIsLogined()) {
            inquiry.setUserId(getCurrentUser().getId());
        }
        inquiryDao.save(inquiry);

        // send mail
        try {
        	mailService.sendInquiryToSupport(
        		getText("app.mail.support"), inquiry);
        } catch (Exception e) {}
        
        return SUCCESS;
	}

    @Action(value="inquiry/create-complete",
        results={@Result(name="success", location="inquiry/create-complete.ftl")}
    )   
    public String completeAction() throws Exception {
        return SUCCESS;
    }   

    public InquiryForm getInquiryForm() {
        return inquiryForm;
    }   

    public void setInquiryForm(InquiryForm inquiryForm) {
        this.inquiryForm = inquiryForm;
    }
}
