package com.galaxii.common.util;

import java.util.ArrayList;
import java.util.List;

public class SimplePager<T> {

	private int limit;
	private int firstIndice;
	private int lastIndice;
	private int page;
	private int pageRange;
	private int firstPage;
	private int lastPage;
	private int totalPage;
	private int prevPage;
	private int nextPage;
	private int startPage;
	private int endPage;
	private int total;
	private List<T> items;

	public SimplePager(int limit, int page, List<T> items, int total) {
		this(limit, page, items, total, 10);
	}

	public SimplePager(int limit, int page, List<T> items, int total, int pageRange) {
		this.limit = limit;
		this.page  = page == 0 ? 1:page;
		this.items = items;
		this.total = total;
		this.pageRange = pageRange;

		this.firstPage = this.total > 0 ? 1:0;
		this.lastPage  = (int)Math.ceil(this.total / (double)this.limit);
		this.firstIndice = this.total == 0 ? 0 : (this.page -1) * this.limit +1;
		this.lastIndice  = this.items == null ? 
			this.firstIndice : this.firstIndice + this.items.size() -1;
		this.prevPage  = this.page > this.firstPage ? this.page -1 : this.page;
		this.nextPage  = this.page < this.lastPage  ? this.page +1 : this.page;

		this.totalPage = this.lastPage;

		// 開始ページ番号を求める
		if (this.page > Math.ceil(this.totalPage - this.pageRange / 2)) {
			this.startPage = this.totalPage - this.page + (int)(Math.ceil(this.page - (this.pageRange - 1)));
		} else {
			this.startPage = Math.ceil(this.page - (this.pageRange - 1) / 2) > 1
				? (int)Math.ceil(this.page - (this.pageRange - 1) / 2) : 1;
		}
		if (this.startPage < 1) { this.startPage = 1; }

		// 終了ページ番号を求める
		if (this.totalPage > this.pageRange) {
			this.endPage = this.startPage + this.pageRange - 1;
		} else {
			this.endPage = this.totalPage;
		}
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public int getFirstIndice() {
		return firstIndice;
	}

	public int getLastIndice() {
		return lastIndice;
	}

	public int getPage() {
		return page;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageRange() {
		return pageRange;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotal() {
		return total;
	}

	public List<T> getItems() {
		return items;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}

	public List<Integer> getPages() {
        List<Integer> pageList = new ArrayList<Integer>();
        for(int i = startPage; i<=endPage; i++){
            pageList.add(i);
        }
        return pageList;
	}

	public boolean getHasNextPage() {
		return this.page < this.lastPage;
	}

	public boolean getHasPrevPage() {
		return this.page > this.firstPage;
	}
}
