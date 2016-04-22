package com.galaxii.common.util;

import java.util.List;
import java.util.Map;

public class SessionUtil {

	Map<String, Object> sess;
	private String contextPath;
	private String namespace;
	private String name;

	public SessionUtil(Map<String, Object> sess, String contextPath, String namespace, String name) {
		this.sess = sess;
		this.contextPath = contextPath;
		this.namespace = namespace;
		this.name = name;
	}

	public String getAppBaseKey() {
		return contextPath;
	}

	public String getNamespaceBaseKey() {
		return (getAppBaseKey() + namespace).replace('/', '_') + '_';
	}

	public String getActionBaseKey() {
		return getNamespaceBaseKey() + name.replace('/', '_') + '_';
	}

	public void put(String key, Object val) {
		sess.put(key, val);
	}

	public Object get(String key) {
		return sess.get(key);
	}

	public void remove(String key) {
		sess.remove(key);
	}

	public void putAction(String key, Object val) {
		put(getActionBaseKey() + key, val);
	}

	public Object getAction(String key) {
		return get(getActionBaseKey() + key);
	}

	public void removeAction(String key) {
		remove(getActionBaseKey() + key);
	}

	public void putNamespace(String key, Object val) {
		put(getNamespaceBaseKey() + key, val);
	}

	public Object getNamespace(String key) {
		return get(getNamespaceBaseKey() + key);
	}

	public void removeNamespace(String key) {
		remove(getNamespaceBaseKey() + key);
	}

	public void putMorePage(String pageKey, Object id, List<Integer> ids) {
		putNamespace(pageKey + "_page_" + id, ids);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getMorePage(String pageKey, Object id) {
		return (List<Integer>)getNamespace(pageKey + "_page_" + id);
	}

	public void removeMorePage(String pageKey, Object id) {
		removeNamespace(pageKey + "_page_" + id);
	}

	public void putCurrentUser(Object user) {
		putNamespace("user", user);
	}

	public Object getCurrentUser() {
		return getNamespace("user");
	}

	public void removeCurrentUser() {
		removeNamespace("user");
	}

}
