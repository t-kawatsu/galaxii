package com.galaxii.common.interceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PreventDoubleExecuteInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger logger = LoggerFactory.getLogger(PreventDoubleExecuteInterceptor.class);
	private static Map<String, Boolean> processMap = Collections.synchronizedMap(new HashMap<String, Boolean>());

	// The init method is called the after interceptor is instantiated and before calling intercept. This is the place to allocate any resources used by the interceptor.
	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception { 
		/**
		 * 1ブラウザ×
		 */
		logger.info("execute PreventDoubleExecuteInterceptor");
		synchronized(this) {
			if(hasProcess()) {
				logger.info("既にプロセスが存在するので実行出来ません。");
				return null;
			}
			lock();
		}
        String ret = invocation.invoke();
		release();
		return ret;
	}

	// Overwrite destroy to release resources on application shutdown.
	public void destroy() {
	}

	private void release() {
		String name = getProcessName();
		processMap.put(name, Boolean.FALSE);
	}

	private void lock() {
		String name = getProcessName();
		processMap.put(name, Boolean.TRUE);
	}

	private boolean hasProcess() {
		String name = getProcessName();
		if(!processMap.containsKey(name)) {
			return false;
		}

		boolean hasProcess = Boolean.valueOf( processMap.get(name) );
		return hasProcess;
	}

	private String getProcessName() {
		return ServletActionContext.getActionMapping().getName();
	}
}
