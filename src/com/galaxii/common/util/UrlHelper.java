package com.galaxii.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

public class UrlHelper {

	public String getBaseUrl() {
		return getBaseUrl(false);
	}

	public String getBaseUrl(boolean useSsl) {
		AppPropertyUtil appProp = AppPropertyUtil.getInstance();
		if(useSsl) {
			useSsl = appProp.getAppUseSsl();
		}

		String scheme = useSsl ? "https" : "http";
		String domain = appProp.getAppSiteDomain();
		String port   = useSsl ? 
			appProp.getAppSiteSslPort() : appProp.getAppSitePort();

		String portPlace = null;
		if("80".equals(port)) {
			portPlace = "";
		} else {
			portPlace = ":" + port;
		}
		return scheme + "://" + domain + portPlace;
	}

	public String getBaseContextUrl() {
		return getBaseUrl(false);
	}

	public String getBaseContextUrl(boolean useSsl) {
		String contextpath = ServletActionContext.getRequest().getContextPath();
		return getBaseUrl(useSsl) + contextpath;
	}

	public String buildUrl() {
		String actionname = ServletActionContext.getActionMapping().getName();
		return buildUrl("/" + actionname);
	}

	public String buildUrl(final String action) {
		return buildUrl(action, null);
	}

	public String buildUrl(final String action, Map<String,Object> params) {
		return buildUrl(action, params, false);
	}

	public String buildUrl(final String action, Map<String,Object> params,
		boolean isAbsolute) {
		return buildUrl(action, params, isAbsolute, false);
	}

	public String buildUrl(final String action, Map<String,Object> params,
		boolean isAbsolute, boolean useSsl) {
		String namespace = ServletActionContext.getActionMapping().getNamespace();
		return buildUrl(action, params, isAbsolute, useSsl, namespace);
	}

	public String buildUrl(String action, Map<String,Object> params, 
		boolean isAbsolute, boolean useSsl, final String namespace) {
		if(action == null || action.equals("")) {
			action = "/" + ServletActionContext.getActionMapping().getName();
		}
		StringBuilder sb = new StringBuilder();
		String contextpath = ServletActionContext.getRequest().getContextPath();
		sb.append(contextpath);
		if(!"/".equals(namespace)) {
		  sb.append(namespace);
		}
		sb.append(action);
		if(params != null && !params.isEmpty()) {
			sb.append("?");
			for(Map.Entry<String, Object> e : params.entrySet()) {
				// @TODO encode uri
				sb.append(e.getKey()).append("=").append(e.getValue());
			}
		}
		if(!isAbsolute) {
			return sb.toString();
		}
		return getBaseUrl(useSsl) + sb.toString();
    }

	public static Map<String, String> parseQuery(String query) {
		if(query == null) {
			return null;
		}
        String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
			String[] splitted = param.split("=");
			map.put(splitted[0], splitted[1]);
	    }
        return map;
	}
}
