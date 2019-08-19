package com.jeecms.cms.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Tom
 */
public interface TokenSvc {
	public String getToken(HttpServletRequest request,HttpServletResponse response);
	public boolean tokenValidate(HttpServletRequest request);
}
