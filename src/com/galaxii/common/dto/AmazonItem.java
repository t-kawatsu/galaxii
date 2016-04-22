package com.galaxii.common.dto;

public class AmazonItem {
	
	private String title;
	private String asin;
	private String detailPageUrl;
	private AmazonItemImage mediumImage;
	private AmazonItemImage largeImage;
	private String productGroup;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getDetailPageUrl() {
		return detailPageUrl;
	}
	public void setDetailPageUrl(String detailPageUrl) {
		this.detailPageUrl = detailPageUrl;
	}
	public AmazonItemImage getMediumImage() {
		return mediumImage;
	}
	public void setMediumImage(AmazonItemImage mediumImage) {
		this.mediumImage = mediumImage;
	}
	public AmazonItemImage getLargeImage() {
		return largeImage;
	}
	public void setLargeImage(AmazonItemImage largeImage) {
		this.largeImage = largeImage;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
}
