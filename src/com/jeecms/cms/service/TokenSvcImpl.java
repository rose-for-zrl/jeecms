package com.jeecms.cms.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.session.SessionProvider;

/**
 * @author Tom
 */
@Service
public class TokenSvcImpl implements TokenSvc {
	
	public String getToken(HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("GET")){
    		String token=(String) session.getAttribute(request, "token");
    		if(token==null){
    			 token=UUID.randomUUID().toString();
    		}
			session.setAttribute(request, response, "token", token);
			return token;
    	}else if(request.getMethod().equals("POST")){
    		 if (tokenValidate(request)) {
    			 ResponseUtils.renderText(response, "can not repeat submit");
             }
    		 session.setAttribute(request, response, "token", null);
    	}
		return null;
	}
	
	@Override
	public boolean tokenValidate(HttpServletRequest request) {
		String serverToken = (String) session.getAttribute(request, "token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
	}

	 @Autowired
	 private SessionProvider session;
}
