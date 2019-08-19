package com.jeecms.cms.action.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.BinaryReadWrite;
import com.jeecms.cms.Employee;
import com.jeecms.cms.action.admin.main.CmsAdminGlobalAct;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class PersonalAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAdminGlobalAct.class);
	
	@RequiresPermissions("personal:v_profile")
	@RequestMapping("/personal/v_profile.do")
	public String profileEdit(HttpServletRequest request, ModelMap model) {
		CmsUser user = CmsUtils.getUser(request);
		model.addAttribute("user", user);
		return "personal/profile";
	}
	
    public static boolean checkPassword(String password){
        if(password.matches("\\w+")){
            Pattern p1= Pattern.compile("[a-z]+");
            Pattern p2= Pattern.compile("[A-Z]+");
            Pattern p3= Pattern.compile("[0-9]+");
            Matcher m=p1.matcher(password);
            if(!m.find())
                return false;
            else{
                m.reset().usePattern(p2);
                if(!m.find())
                    return false;
                else{
                    m.reset().usePattern(p3);
                    if(!m.find())
                        return false;
                    else{
                        return true;
                    }
                }
            }
        }else{
            return false;
        }
    }
	
	@RequiresPermissions("personal:o_profile")
	@RequestMapping("/personal/o_profile.do")
	public String profileUpdate(String origPwd, String newPwd, String email,
			String realname, HttpServletRequest request, ModelMap model) throws Exception {
		CmsUser user = CmsUtils.getUser(request);
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd, email, realname, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}

		
		CmsUserExt ext = user.getUserExt();
		if (ext == null) {
			ext = new CmsUserExt();
		}
		realname = StringUtils.replaceEach(realname, new String[]{"\'","<",">"}, new String[]{"","",""});
		ext.setRealname(realname);
		email = StringUtils.replaceEach(email, new String[]{"\'","<",">"}, new String[]{"","",""});
		newPwd = StringUtils.replaceEach(newPwd, new String[]{"\'","<",">"}, new String[]{"","",""});
		if(newPwd.length() < 8)
		{
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("您输入的密码长度小于8位!，请重复输入！！！");
			return errors1.showErrorPage(model);
		}

		if(checkPassword(newPwd) == false){
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("密码复杂度不够, 必须包含数字和字母大小写,请重复输入！！！");
			return errors1.showErrorPage(model);
		}
		
		BinaryReadWrite binary = new BinaryReadWrite();
		if(binary.isPassword(user.getId().longValue(),newPwd))
		{
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("您输入的密码和之前4次密码相同，请重复输入！！！");
			return errors1.showErrorPage(model);
		}
		
		cmsUserExtMng.update(ext, user);
		cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		binary.modifyBinaryData(new Employee(user.getId().longValue(),user.getUsername(),newPwd,df.format(new Date())));
		
		log.info("update CmsAdmin id={}.", user.getId());
		cmsLogMng.operating(request, "cmsUser.log.update", "id=" + user.getId()
				+ ";username=" + user.getUsername());
		
		model.addAttribute("message", "global.success");
		return profileEdit(request, model);
	}

	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("personal:v_checkPwd")
	@RequestMapping("/personal/v_checkPwd.do")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		CmsUser user = CmsUtils.getUser(request);
		boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}

	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
			String newPwd, String email, String realname,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 32)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 32)) {
			return errors;
		}
		if (errors.ifMaxLength(email, "email", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(realname, "realname", 100)) {
			return errors;
		}
		if (!cmsUserMng.isPasswordValid(id, origPwd)) {
			errors.addErrorCode("member.origPwdInvalid");
			return errors;
		}
		return errors;
	}

	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsUserExtMng cmsUserExtMng;
	@Autowired
	protected CmsLogMng cmsLogMng;
}
