package com.galaxii.common.util;

import java.util.List;

public interface Paginator<T> {
	public int getTotal();
	public List<T> getItems();
	public Integer getPage();
	public void setPage(Integer page);
	public Integer getLimit();
	public void setLimit(Integer limit);
	public SimplePager<T> paginate();
}
