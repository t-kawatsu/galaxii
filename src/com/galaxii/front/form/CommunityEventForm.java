package com.galaxii.front.form;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ibm.icu.util.Calendar;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class CommunityEventForm extends AbstractForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	private static final String TIME_FORMAT = "HH:ss";

	private String title;
	private String description;
	private String place;
	private Double lat;
	private Double lon;
	private String startAtDate;
	private String startAtTime;
	private String endAtDate;
	private String endAtTime;
	private Date startAt;
	private Date endAt;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = StringUtils.trim(title);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = StringUtils.trim(description);
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = StringUtils.trim(place);
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public String getStartAtDate() {
		return startAtDate;
	}
	public void setStartAtDate(String startAtDate) {
		this.startAtDate = startAtDate;
	}
	public String getStartAtTime() {
		return startAtTime;
	}
	public void setStartAtTime(String startAtTime) {
		this.startAtTime = startAtTime;
	}
	public String getEndAtDate() {
		return endAtDate;
	}
	public void setEndAtDate(String endAtDate) {
		this.endAtDate = endAtDate;
	}
	public String getEndAtTime() {
		return endAtTime;
	}
	public void setEndAtTime(String endAtTime) {
		this.endAtTime = endAtTime;
	}
	public Date getStartAt() {
		return startAt;
	}
	public void setStartAt(Date startAt) {
		if(startAt != null) {
			SimpleDateFormat dsdf = new SimpleDateFormat(DATE_FORMAT);
			SimpleDateFormat tsdf = new SimpleDateFormat(TIME_FORMAT);
			startAtDate = dsdf.format(startAt);
			startAtTime = tsdf.format(startAt);
		}
		this.startAt = startAt;
	}
	public Date getEndAt() {
		return endAt;
	}
	public void setEndAt(Date endAt) {
		if(endAt != null) {
			SimpleDateFormat dsdf = new SimpleDateFormat(DATE_FORMAT);
			SimpleDateFormat tsdf = new SimpleDateFormat(TIME_FORMAT);
			endAtDate = dsdf.format(endAt);
			endAtTime = tsdf.format(endAt);
		}
		this.endAt = endAt;
	}
	
	public boolean validate(ActionSupport as) {
		
		// title
		if(StringUtils.isEmpty(getTitle())) {
			as.addFieldError("communityEventForm.title", as.getText("invalidate.required"));
		} else if(100 < getTitle().length()) {
			as.addFieldError("communityEventForm.title", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}
		
		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("communityEventForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("communutyEventForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("1500")));
		}
		
		// place 
		if(!StringUtils.isEmpty(getDescription()) && 500 < getDescription().length()) {
			as.addFieldError("communityEventForm.place", as.getText("invalidate.maxLength", null, Arrays.asList("500")));
		}
		
		// startAt
		if(StringUtils.isEmpty(getStartAtDate()) || StringUtils.isEmpty(getStartAtTime())) {
			as.addFieldError("communityEventForm.startAt", as.getText("invalidate.required"));
		} else {
			try {
				DateUtils.parseDate(getStartAtDate(), DATE_FORMAT);
				DateUtils.parseDate(getStartAtTime(), TIME_FORMAT);
				this.startAt = DateUtils.parseDate(
						getStartAtDate()+" "+getStartAtTime(), DATE_FORMAT + " " + TIME_FORMAT);
			} catch (Exception e) {
				as.addFieldError("communityEventForm.startAt", as.getText("invalidate.valueForm"));
			}
		}
		
		// endAt
		if(!StringUtils.isEmpty(getEndAtDate())) {
			if(StringUtils.isEmpty(getStartAtTime())) {
				as.addFieldError("communityEventForm.startAt", as.getText("invalidate.required"));
			} else {
				try {
					DateUtils.parseDate(getEndAtDate(), DATE_FORMAT);
					DateUtils.parseDate(getEndAtTime(), TIME_FORMAT);
					this.endAt = DateUtils.parseDate(
							getEndAtDate()+" "+getEndAtTime(), DATE_FORMAT + " " + TIME_FORMAT);
				} catch (Exception e) {
					as.addFieldError("communityEventForm.startAt", as.getText("invalidate.valueForm"));
				}
			}
		}
		
		if(as.hasFieldErrors()) {
			return false;
		}
		
		// valid date ??
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		if(startAt.getTime() < now.getTime()) {
			as.addFieldError("communityEventForm.startAt", as.getText("invalidate.passTime"));
		} else if (endAt != null) {
			if(endAt.getTime() < now.getTime()) {
			} else if(endAt.getTime() < startAt.getTime()) {
				as.addFieldError("communityEventForm.startAt", as.getText("invalidate.start2endDate"));
			}
		}
			
		return !as.hasFieldErrors();
	}

}
