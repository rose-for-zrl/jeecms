package com.jeecms.cms.action.admin.main;

import static com.jeecms.common.page.SimplePage.cpn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsWebservice;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.entity.CmsRole;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.cms.BinaryReadWrite;
import com.jeecms.cms.Employee;


/**
 * 全站管理员ACTION
 * 
 */
@Controller
public class CmsAdminGlobalAct extends CmsAdminAbstract {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAdminGlobalAct.class);

	@RequiresPermissions("admin_global:v_list")
	@RequestMapping("/admin_global/v_list.do")
	public String list(String queryUsername, String queryEmail,
			Integer queryGroupId, Boolean queryDisabled, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		
		queryUsername = StringUtils.replaceEach(queryUsername, new String[]{"\'","<",">"}, new String[]{"","",""});
		queryEmail = StringUtils.replaceEach(queryEmail, new String[]{"\'","<",">"}, new String[]{"","",""});
		
		CmsUser currUser = CmsUtils.getUser(request);
		Pagination pagination = manager.getPage(queryUsername, queryEmail,
				null, queryGroupId, queryDisabled, true, currUser.getRank(),
				cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);

		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);
		model.addAttribute("groupList", cmsGroupMng.getList());
		return "admin/global/list";
	}

	@RequiresPermissions("admin_global:v_add")
	@RequestMapping("/admin_global/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser currUser = CmsUtils.getUser(request);
		List<CmsGroup> groupList = cmsGroupMng.getList();
		List<CmsSite> siteList = cmsSiteMng.getList();
		List<CmsRole> roleList = cmsRoleMng.getList();
		model.addAttribute("site", site);
		model.addAttribute("groupList", groupList);
		model.addAttribute("siteList", siteList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("currRank", currUser.getRank());
		return "admin/global/add";
	}

	@RequiresPermissions("admin_global:v_edit")
	@RequestMapping("/admin_global/v_edit.do")
	public String edit(Integer id, Integer queryGroupId, Boolean queryDisabled,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		CmsUser currUser = CmsUtils.getUser(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser admin = manager.findById(id);

		List<CmsGroup> groupList = cmsGroupMng.getList();
		List<CmsSite> siteList = cmsSiteMng.getList();
		List<CmsRole> roleList = cmsRoleMng.getList();

		model.addAttribute("cmsAdmin", admin);
		model.addAttribute("site", site);
		model.addAttribute("sites", admin.getSites());
		model.addAttribute("roleIds", admin.getRoleIds());
		model.addAttribute("groupList", groupList);
		model.addAttribute("siteList", siteList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("currRank", currUser.getRank());

		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);
		return "admin/global/edit";
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
	
	@RequiresPermissions("admin_global:o_save")
	@RequestMapping("/admin_global/o_save.do")
	public String save(CmsUser bean, CmsUserExt ext, String username,
			String email, String password, Boolean selfAdmin, Integer rank, Integer groupId,Integer departmentId,
			Integer[] roleIds,Integer[] channelIds, Integer[] siteIds,
			Byte[] steps, Boolean[] allChannels,Boolean[] allControlChannels,
			HttpServletRequest request,ModelMap model) throws Exception {
		
		WebErrors errors = validateSave(bean, siteIds, steps, allChannels,
				request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}


		
		String ip = RequestUtils.getIpAddr(request);
		String Realname = ext.getRealname();
		Realname = StringUtils.replaceEach(Realname, new String[]{"\'","<",">"}, new String[]{"","",""});
		username = StringUtils.replaceEach(username, new String[]{"\'","<",">"}, new String[]{"","",""});
		email = StringUtils.replaceEach(email, new String[]{"\'","<",">"}, new String[]{"","",""});
		password = StringUtils.replaceEach(password, new String[]{"\'","<",">"}, new String[]{"","",""});
		ext.setRealname(Realname);
		
		if(password.length() < 8)
		{
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("您输入的密码长度小于8位!，请重复输入！！！");
			return errors1.showErrorPage(model);
		}

		if(checkPassword(password) == false){
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("密码复杂度不够, 必须包含数字和字母大小写,请重复输入！！！");
			return errors1.showErrorPage(model);
		}
		
		bean = manager.saveAdmin(username, email, password, ip, false,
				selfAdmin, rank, groupId,departmentId, roleIds, channelIds, siteIds, steps,
				allChannels, allControlChannels,ext);
		BinaryReadWrite binary = new BinaryReadWrite();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		binary.addBinaryData(new Employee(bean.getId().longValue(),username,password,df.format(new Date())));
		
		cmsWebserviceMng.callWebService("true",username, password, email, ext,CmsWebservice.SERVICE_TYPE_ADD_USER);
		log.info("save CmsAdmin id={}", bean.getId());
		cmsLogMng.operating(request, "cmsUser.log.save", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
		return "redirect:v_list.do";
	}


	@RequiresPermissions("admin_global:o_update")
	@RequestMapping("/admin_global/o_update.do")
	public String update(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,Boolean[] allControlChannels,
			String queryUsername, String queryEmail, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) throws Exception {

		WebErrors errors = validateUpdate(bean.getId(),bean.getRank(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		
		String Realname = ext.getRealname();
		Realname = StringUtils.replaceEach(Realname, new String[]{"\'","<",">"}, new String[]{"","",""});
		ext.setRealname(Realname);
		String email = bean.getEmail();
		email = StringUtils.replaceEach(email, new String[]{"\'","<",">"}, new String[]{"","",""});
		bean.setEmail(email);
		password = StringUtils.replaceEach(password, new String[]{"\'","<",">"}, new String[]{"","",""});
		BinaryReadWrite binary = new BinaryReadWrite();
		if(binary.isPassword(bean.getId().longValue(),password))
		{
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("您输入的密码和之前4次密码相同，请重复输入！！！");
			return errors1.showErrorPage(model);
		}
		
		if(password.length() < 8)
		{
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("您输入的密码长度小于8位!，请重复输入！！！");
			return errors1.showErrorPage(model);
		}

		if(checkPassword(password) == false){
			WebErrors errors1 = WebErrors.create(request);
			errors1.ErrorString("密码复杂度不够, 必须包含数字和字母大小写,请重复输入！！！");
			return errors1.showErrorPage(model);
		}
		
		
		bean = manager.updateAdmin(bean, ext, password, groupId,departmentId, roleIds,
				channelIds, siteIds, steps, allChannels,allControlChannels);
		
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

		binary.modifyBinaryData(new Employee(bean.getId().longValue(),bean.getUsername(),password,df.format(new Date())));
		
		cmsWebserviceMng.callWebService("true",bean.getUsername(), password, null, ext,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
		log.info("update CmsAdmin id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsUser.log.update", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
			
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}

	@RequiresPermissions("admin_global:o_delete")
	@RequestMapping("/admin_global/o_delete.do")
	public String delete(Integer[] ids, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) throws Exception {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser[] beans = manager.deleteByIds(ids);
		CmsUser user =CmsUtils.getUser(request);
		boolean deleteCurrentUser=false;
		
		
		for (CmsUser bean : beans) {
			Map<String,String>paramsValues=new HashMap<String, String>();
			paramsValues.put("username", bean.getUsername());
			paramsValues.put("admin", "true");
			cmsWebserviceMng.callWebService(CmsWebservice.SERVICE_TYPE_DELETE_USER, paramsValues);
			log.info("delete CmsAdmin id={}", bean.getId());
			if(user.getUsername().equals(bean.getUsername())){
				deleteCurrentUser=true;
			}else{
				cmsLogMng.operating(request, "cmsUser.log.delete", "id="
						+ bean.getId() + ";username=" + bean.getUsername());
				BinaryReadWrite binary = new BinaryReadWrite();
				binary.deleteBinaryData(bean.getId().longValue());
			}
		}
		if(deleteCurrentUser){
			 Subject subject = SecurityUtils.getSubject();
			 subject.logout();
			 return "login";
		}
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}

	@RequiresPermissions("admin_global:v_channels_add")
	@RequestMapping(value = "/admin_global/v_channels_add.do")
	public String channelsAdd(Integer siteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return channelsAddJson(siteId, request, response, model);
	}

	@RequiresPermissions("admin_global:v_channels_edit")
	@RequestMapping(value = "/admin_global/v_channels_edit.do")
	public String channelsEdit(Integer userId, Integer siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		return channelsEditJson(userId, siteId, request, response, model);
	}

	@RequiresPermissions("admin_global:v_check_username")
	@RequestMapping(value = "/admin_global/v_check_username.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		checkUserJson(request, response);
	}

	@RequiresPermissions("admin_global:v_check_email")
	@RequestMapping(value = "/admin_global/v_check_email.do")
	public void checkEmail(String email, HttpServletResponse response) {
		checkEmailJson(email, response);
	}
	
	@RequiresPermissions("admin_global:v_depart_add")
	@RequestMapping(value = "/admin_global/v_depart_add.do")
	public String departTree(String root, HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		return departAdd(root, request, response, model);
	}
	
	@RequiresPermissions("admin_global:v_site_edit")
	@RequestMapping(value = "/admin_global/v_site_edit.do")
	public String siteEditTree(Integer id,HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		List<CmsSite> siteList= cmsSiteMng.getTopList();
		CmsUser admin = manager.findById(id);
		model.addAttribute("cmsAdmin", admin);
		model.addAttribute("siteIds", admin.getSiteIds());
		model.addAttribute("siteList", siteList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/sites_edit";
	}
	
	@RequiresPermissions("admin_global:v_site_add")
	@RequestMapping(value = "/admin_global/v_site_add.do")
	public String siteAddTree(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		List<CmsSite> siteList= cmsSiteMng.getTopList();
		model.addAttribute("siteList", siteList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/sites_add";
	}

	private WebErrors validateSave(CmsUser bean, Integer[] siteIds,
			Byte[] steps, Boolean[] allChannels, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (siteIds != null) {
			if (steps == null) {
				errors.addError("steps cannot be null");
				return errors;
			}
			if (allChannels == null) {
				errors.addError("allChannels cannot be null");
				return errors;
			}
			if (siteIds.length != steps.length
					|| siteIds.length != allChannels.length) {
				errors.addError("siteIds length, steps length,"
						+ " allChannels length not equals");
				return errors;
			}
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		// TODO 检查管理员rank
		return errors;
	}

	private WebErrors validateUpdate(Integer id, Integer rank,HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		if (vldParams(id,rank, request, errors)) {
			return errors;
		}
		// TODO 检查管理员rank
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsUser entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsUser.class, id)) {
			return true;
		}
		return false;
	}
	
	private boolean vldParams(Integer id,Integer rank, HttpServletRequest request,
			WebErrors errors) {
		CmsUser user = CmsUtils.getUser(request);
		CmsUser entity = manager.findById(id);
		//提升等级大于当前登录用户
		if (rank > user.getRank()) {
			errors.addErrorCode("error.noPermissionToRaiseRank", id);
			return true;
		}
		//修改的用户等级大于当前登录用户 无权限
		if (entity.getRank() > user.getRank()) {
			errors.addErrorCode("error.noPermission", CmsUser.class, id);
			return true;
		}
		return false;
	}

}