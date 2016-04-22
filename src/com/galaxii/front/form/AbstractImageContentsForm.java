package com.galaxii.front.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.common.dto.ImageMeta;
import com.galaxii.common.entity.ImageType;

public abstract class AbstractImageContentsForm extends AbstractForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String imageIdsCsv;
	protected String imageTypesCsv;
	protected String imageVendorDataCsv;
	
	public List<ImageMeta> getImageMetas() {
		String[] ids = getImageIds();
		String[] types = getImageTypes();
		String[] vendorData = getImageVendorData();
		if(ids == null) {
			return null;
		}
		List<ImageMeta> imageMetas = new ArrayList<ImageMeta>();
		for(int i=0; i<ids.length; i++) {
			ImageMeta imageMeta = new ImageMeta();
			imageMeta.setId(ids[i]);
			if(types != null && i < types.length) {
				imageMeta.setType(types[i]);
				imageMeta.setImageType(ImageType.valueOf(types[i]));
			}
			if(vendorData != null && i < vendorData.length) {
				imageMeta.setVendorData(vendorData[i]);
			}
			imageMetas.add(imageMeta);
		}
		return imageMetas;
	}
	
	public void setImageMetas(List<ImageMeta> imageMetas) {
		imageIdsCsv = null;
		imageTypesCsv = null;
		imageVendorDataCsv = null;
		List<String> imageIds = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<String> vendors = new ArrayList<String>();
		for(ImageMeta row : imageMetas) {
			imageIds.add(row.getId());
			if(row.getImageType() != null) {
				types.add(row.getImageType().toString());
			} else {
				types.add("");
			}
			if(row.getVendorData() != null) {
				vendors.add(row.getVendorData());
			} else {
				vendors.add("");
			}
		}
		imageIdsCsv = StringUtils.join(imageIds, ",");
		imageTypesCsv = StringUtils.join(types, ",");
		imageVendorDataCsv = StringUtils.join(vendors, ",");
	}
	
	public String[] getImageTypes() {
		return StringUtils.split(getImageTypesCsv(), ",");
	}

	public String[] getImageIds() {
		return StringUtils.split(getImageIdsCsv(), ",");
	}

	public String[] getImageVendorData() {
		return StringUtils.split(getImageVendorDataCsv(), ",");
	}

	public String getImageIdsCsv() {
		return imageIdsCsv;
	}

	public void setImageIdsCsv(String imageIdsCsv) {
		this.imageIdsCsv = imageIdsCsv;
	}

	public String getImageTypesCsv() {
		return imageTypesCsv;
	}

	public void setImageTypesCsv(String imageTypesCsv) {
		this.imageTypesCsv = imageTypesCsv;
	}

	public String getImageVendorDataCsv() {
		return imageVendorDataCsv;
	}

	public void setImageVendorDataCsv(String imageVendorDataCsv) {
		this.imageVendorDataCsv = imageVendorDataCsv;
	}
	
}
