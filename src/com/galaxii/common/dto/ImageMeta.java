package com.galaxii.common.dto;

import com.galaxii.common.entity.ImageType;

public class ImageMeta {
	private String id;
	private String vendorData;
	private String type;
	private ImageType imageType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVendorData() {
		return vendorData;
	}
	public void setVendorData(String vendorData) {
		this.vendorData = vendorData;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
}
