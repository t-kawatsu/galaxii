package com.galaxii.batch.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

@ParentPackage("base")
@Namespace("/batch")
@InterceptorRefs({ @InterceptorRef("myBatchStack"), })
abstract public class AbstractAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Before
	public void beforeAction() {
	}

}
