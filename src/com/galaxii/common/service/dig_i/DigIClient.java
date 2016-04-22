package com.galaxii.common.service.dig_i;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.arnx.jsonic.JSON;

import org.apache.commons.codec.net.URLCodec;
import org.springframework.stereotype.Service;

@Service
public class DigIClient {

	protected static final String API_ENDPOINT_URL = "http://dig-i.com/api";
	
	protected static final String LIMIT_PARAM_NAME = "limit";
	
	protected static final String PAGE_PARAM_NAME  = "page";
	
	protected static final String SEARCH_WORD_PARAM_NAME = "word";
	
	protected void verifyParameterPresence(String name, String value) {
		if(value == null || value.equals("")) {
			throw new IllegalArgumentException("");
		}
	}
	
	private void verifyParameterPresence(String name, Object[] value) {
		if(value == null || value.length <= 0) {
			throw new IllegalArgumentException("");
		}
	}
	
	protected String connect(String endpoint, Parameter ...parameters) throws IOException {
		DigIRequest request = new DigIRequest(endpoint, parameters);
		return (String)request.makeRequest();
	}
	
	@SuppressWarnings("unchecked")
	protected List<InformationItem> parseInformationItems(String str) {
		if(str == null) {
			return null;
		}
		try {
			Map<String, Object> res = JSON.decode(str, Map.class);
			if(!res.containsKey("items")) {
				return null;
			}
			List<Object> items = (List<Object>)res.get("items");
			if(items == null || items.isEmpty()) {
				return null;
			}
			List<InformationItem> ret = new ArrayList<InformationItem>();
			for(Object row : items) {
				Map<String, Object> item = (Map<String, Object>)row;
				InformationItem i = new InformationItem();
				i.setId(((BigDecimal)item.get("id")).intValue());
				i.setTitle((String)item.get("title"));
				i.setDescription((String)item.get("description"));
				i.setLink((String)item.get("link"));
				i.setImageUrl((String)item.get("imageUrl"));
				i.setCategories((List<String>)item.get("categories"));
				i.setPublishedAt(new Date(((BigDecimal)item.get("publishedAt")).intValue()));
				i.setLanguageCode(new Locale((String)item.get("languageCode")));
				i.setResourceTitle((String)item.get("resourceTitle"));
				i.setResourceLink((String)item.get("resourceLink"));
				i.setResourceImageUrl((String)item.get("resourceImage"));
				i.setResourceType((String)item.get("resourceType"));
				ret.add(i);
			}
			return ret;
		} catch (Exception e) {
		}
		return null;
	}
	
	public List<InformationItem> searchInformations(
			String[] words, Integer limit, Integer page) {
		verifyParameterPresence(SEARCH_WORD_PARAM_NAME, words);
		
		try {
			StringBuffer sb = new StringBuffer();
			URLCodec codec = new URLCodec();
			for(String row : words) {
				sb.append(codec.encode(row, "UTF-8"));
				sb.append(",");
			}
			
			String ret = connect(API_ENDPOINT_URL+"/search/read",
					Parameter.with(SEARCH_WORD_PARAM_NAME, sb.toString()),
					Parameter.with(LIMIT_PARAM_NAME, limit),
					Parameter.with(PAGE_PARAM_NAME, page));
			return parseInformationItems(ret);
		} catch (IOException e) {
			return null;
		}
	}

}
