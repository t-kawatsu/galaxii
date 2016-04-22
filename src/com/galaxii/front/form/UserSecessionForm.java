package com.galaxii.front.form;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class UserSecessionForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String REASON_SETTING_CODE = "C003";

	private Integer reasonCode;
	private String description;
	private Map<String, String> codes;

	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

	public Integer getReasonCode() {
		return reasonCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getReasonCodes() {
		if (codes == null) {
			codes = getSettingSelectList(REASON_SETTING_CODE);
		}
		return codes;
	}

	public boolean validate(ActionSupport as) {

		String code = String.valueOf(getReasonCode());
		if (!getReasonCodes().containsKey(code)) {
			as.addFieldError("userSecessionForm.reasonCode",
					as.getText("invalidate.unKnownValue"));
		}

		return !as.hasFieldErrors();
	}
}
