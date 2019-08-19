package com.jeecms.cms;

import com.jeecms.cms.ThreeDES; 
import org.springframework.beans.BeansException; 
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory; 
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer; 

import java.util.Properties; 

/** 
* 重写PropertyPlaceholderConfigurer的processProperties方法实现 
* 
* @author leizhimin 2012-03-14 16:47 
*/ 

public class PropertyPlaceholderConfigurerExt extends PropertyPlaceholderConfigurer{ 


        @Override 
        protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) 
                        throws BeansException { 
                String password = props.getProperty("jdbc.password"); 
                String username = props.getProperty("jdbc.username"); 
                String url = props.getProperty("jdbc.url"); 
                
                if (password != null) { 
                	
                        //解密jdbc.password属性值，并重新设置 
                	ThreeDES td;
						try {
							td = new ThreeDES("jeecmsv7");
							
	                	String pw = td.decrypt(password);
	                	String un = td.decrypt(username);
	                	String ur = td.decrypt(url);
							
                     props.setProperty("jdbc.password", pw); 
                     props.setProperty("jdbc.username", un); 
                     props.setProperty("jdbc.url", ur); 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		
                } 
                super.processProperties(beanFactory, props); 

        } 
}
