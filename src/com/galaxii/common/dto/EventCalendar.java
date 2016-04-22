package com.galaxii.common.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateUtils;

public class EventCalendar {

	private Date currentMonth;
	private Date targetMonth;
	private Date nextMonth;
	private Date prevMonth;
	private Date calendarFirstDay;
	private Date monthFirstDay;
	private Date monthLastDay;
	private Date current;
	private Iterator<Calendar> monthCals;
	private String[] weekLabels = 
		{"日","月","火","水","木","金","土"};
	
	public EventCalendar(Date today) {
		// TODO factoryクラスに移す
		monthCals = DateUtils.iterator(today, DateUtils.RANGE_MONTH_SUNDAY);
		targetMonth = DateUtils.truncate(today, Calendar.MONTH);
		nextMonth = DateUtils.addMonths(targetMonth, 1);
		prevMonth = DateUtils.addMonths(targetMonth, -1);
		
		monthFirstDay = (Date)targetMonth.clone();
		monthLastDay = DateUtils.addDays(nextMonth, -1);
		
		current = new Date();
		currentMonth = DateUtils.truncate(current, Calendar.MONTH);
	}

	public Date getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}

	public Date getNextMonth() {
		return nextMonth;
	}

	public void setNextMonth(Date nextMonth) {
		this.nextMonth = nextMonth;
	}

	public Date getPrevMonth() {
		return prevMonth;
	}

	public void setPrevMonth(Date prevMonth) {
		this.prevMonth = prevMonth;
	}

	public Date getCalendarFirstDay() {
		return calendarFirstDay;
	}

	public void setCalendarFirstDay(Date calendarFirstDay) {
		this.calendarFirstDay = calendarFirstDay;
	}

	public Date getMonthFirstDay() {
		return monthFirstDay;
	}

	public void setMonthFirstDay(Date monthFirstDay) {
		this.monthFirstDay = monthFirstDay;
	}

	public Date getMonthLastDay() {
		return monthLastDay;
	}

	public void setMonthLastDay(Date monthLastDay) {
		this.monthLastDay = monthLastDay;
	}

	public Iterator<Calendar> getMonthCals() {
		return monthCals;
	}

	public void setMonthCals(Iterator<Calendar> monthCals) {
		this.monthCals = monthCals;
	}

	public String[] getWeekLabels() {
		return weekLabels;
	}

	public void setWeekLabels(String[] weekLabels) {
		this.weekLabels = weekLabels;
	}

	public Date getTargetMonth() {
		return targetMonth;
	}

	public void setTargetMonth(Date targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	public void setCurrent(Date date) {
		this.current = date;
	}
	
	public Date getCurrent() {
		return this.current;
	}
}
