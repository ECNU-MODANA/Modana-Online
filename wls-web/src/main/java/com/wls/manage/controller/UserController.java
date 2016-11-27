package com.wls.manage.controller;

import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.taobao.api.ApiException;
import com.wls.manage.dao.UserMapper;
import com.wls.manage.dto.UploadFileEntity;
import com.wls.manage.entity.CookieEntity;
import com.wls.manage.entity.UserEntity;
import com.wls.manage.service.CookieService;
import com.wls.manage.service.FtpService;
import com.wls.manage.util.EncodeUtil;
import com.wls.manage.util.ResponseData;
import com.wls.manage.util.StringUtil;
import com.wls.manage.util.TelephoneVerifyUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	private static String baseDir = "picture";
	@Autowired
	private UserMapper userDao;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private FtpService ftpService;
	@RequestMapping(value = "/login")
	@ResponseBody
	public ResponseData<String> login(HttpServletRequest request, String userName, String password) {
		if(StringUtil.isnotNull(userName)&&StringUtil.isnotNull(password)){
			UserEntity user = userDao.findUser(userName, EncodeUtil.encodeByMD5(password));
			if (user != null) {
				String cookie = cookieService.insertCookie(userName);
				user.setPassword("********");
				request.getSession().setAttribute("user", user);
	            return  ResponseData.newSuccess(String.format("token=%s", cookie));
			}
			return ResponseData.newFailure("用户名或者密码不正确~");
		}else{
			return ResponseData.newFailure("用户名和密码不能为空~");
		}
		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					cookieService.deleteCookie(cookie.getValue());
				}
			}
		}
		return true;
	}

	@RequestMapping(value = "/findUser")
	@ResponseBody
	public Object findUser(HttpServletRequest request,String token) {
		UserEntity user = (UserEntity)request.getSession().getAttribute("user");
		if(user!=null){return user;}
		if(StringUtil.isNull(token)){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0){
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("token")) {token=	cookie.getValue();break;}
				}
			}
		}
		if(StringUtil.isnotNull(token)){
			CookieEntity effectiveCookie = cookieService.findEffectiveCookie(token);
			if (effectiveCookie != null) {
				user = userDao.findUserByName(effectiveCookie.getUsername());
				if(user!=null){
					user.setPassword("********");
					request.getSession().setAttribute("user", user);
					return user;
				}
			}
		}
		user = new UserEntity();
		return user;
	}

	@RequestMapping(value = "/userNameVerify", method = RequestMethod.POST)
	@ResponseBody
	public Object userNameVerify(HttpServletRequest request, String username) throws ApiException {
		if(userDao.findUserByName(username)==null)
			return true;
		return false;
	}
	
	@RequestMapping(value = "/checkVerifyCode")
	@ResponseBody
	public Object checkVerifyCode(HttpServletRequest request, String verifycode) {
		if (verifycode!=null&&request.getSession().getAttribute("identityVerifyCode")!=null) {
			if(request.getSession().getAttribute("identityVerifyCode").equals(verifycode))
				return true;
		}
		return false;
	}
	
	
	@RequestMapping(value = "/sendSignUpCode")
	@ResponseBody
	public Object sendSignUpCode(HttpServletRequest request,@RequestParam(value="telephone",required=false) String telephone) throws ApiException {
		if(telephone!=null&&!telephone.equals("")){
			TelephoneVerifyUtil teleVerify = new TelephoneVerifyUtil();
			String signUpCode = teleVerify.signUpVerify(telephone);
			request.getSession().setAttribute("signUpCode", signUpCode);
			return ResponseData.newSuccess("验证码已发送"); 
		}
		return ResponseData.newFailure("请填写手机号"); 
	}
	
	
	@RequestMapping(value = "/verifySignUpCode")
	@ResponseBody
	public Object verifySignUpCode(HttpServletRequest request, String signUpCode) throws ApiException {
		String sessyzm=""+request.getSession().getAttribute("signUpCode");
		if(signUpCode==null||!(sessyzm).equalsIgnoreCase(signUpCode))
			return ResponseData.newFailure("验证码输入错误");
		else 
			return ResponseData.newSuccess("验证码验证成功");
	}
	
	@RequestMapping(value = "/identityVerify", method = RequestMethod.POST)
	@ResponseBody
	public Object identityVerify(HttpServletRequest request, String telephone) throws ApiException {
		if(telephone!=null&&!telephone.equals("")){
			TelephoneVerifyUtil teleVerify = new TelephoneVerifyUtil();
			String identityVerifyCode = teleVerify.identityVerify(telephone);
			request.getSession().setAttribute("identityVerifyCode", identityVerifyCode);
			return ResponseData.newSuccess("验证码已发送"); 
		}
		return ResponseData.newFailure("请填写手机号"); 
	}
	
	@RequestMapping(value = "/signup")
	@ResponseBody
	public Object signup(HttpServletRequest request,String username, String password,String rpassword, String email,String telephone,Integer suproleid) throws ApiException {
		if (username == null || password == null ) {
			return ResponseData.newFailure("用户名和密码输入不能为空"); 
		}
		if (!password.equals(rpassword) ) {
			return ResponseData.newFailure("两次密码输入不一致"); 
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword(EncodeUtil.encodeByMD5(password));
		userEntity.setEmail(email);
		userEntity.setTelephone(telephone);
		userEntity.setSuproleid(suproleid);
		userDao.insertUser(userEntity);
		return ResponseData.newSuccess("注册成功");
	}
	
	
	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public Object updateUser(HttpServletRequest request, @RequestParam(required = false) MultipartFile useravatar,UserEntity user) throws ApiException {
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		String dir = String.format("%s/user/%s", baseDir, user.getId());
		String fileName = String.format("user%s_%s.%s", user.getId(), new Date().getTime(), "jpg");
		UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, useravatar, dir);
		ftpService.uploadFile(uploadFileEntity);
		user.setAvatar(FtpService.READ_URL+dir + "/" + fileName);
		if(user.getId().equals(0)){
			if(StringUtil.isnotNull(user.getPassword()))
			{user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));}
			this.userDao.updateUser(user);
			UserEntity	ol_user=this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ol_user;
		}
		return false;
	}
	
	@RequestMapping(value = "/checkOldPassword")
	@ResponseBody
	public boolean checkOldPassword(HttpServletRequest request,String pwd){
		if(StringUtil.isNull(pwd)){return false;};
		pwd=EncodeUtil.encodeByMD5(pwd);
		UserEntity ol_user = (UserEntity)request.getSession().getAttribute("user");
		UserEntity	new_user=this.userDao.findUserById(ol_user.getId().intValue());
		return pwd.equals(new_user.getPassword());
	}
	
	@RequestMapping(value = "/upPwdByTelephone")
	@ResponseBody
	public ResponseData<String> upPwdByTelephone(HttpServletRequest request,String key,String toke,UserEntity user){
		if(StringUtil.isnotNull(key)&&StringUtil.isnotNull(toke)){
			String stoke=request.getSession().getAttribute(key+"shear_yzm")+""; request.getSession().removeAttribute(key+"shear_yzm");  
			if(toke.equalsIgnoreCase(stoke)){
				if(StringUtil.isnotNull(user.getPassword())){user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));}
				userDao.updateUser(user);
				return ResponseData.newSuccess("密码修改重置成功！");
			}else{
				return ResponseData.newFailure("非法操作！");			}
		}
		return ResponseData.newFailure("非法操作！");		//返回受影响的行
	}
	
	/**
	 * 检查用户名是否占用
	 * @param request true：表示当前用户名已存在或为null->不能注册
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/existenceUserName")
	@ResponseBody
	public boolean existenceUserName(HttpServletRequest request,String userName){
		if(StringUtil.isNull(userName)){return true;}
	    return this.userDao.existenceUserName(userName)>0;
	}

}
