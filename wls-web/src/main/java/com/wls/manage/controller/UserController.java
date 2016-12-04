package com.wls.manage.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	
	@RequestMapping(value = "/findUserList", method = RequestMethod.POST)
	@ResponseBody
	public Object findUserList(@RequestParam(value="pageNum",required=false) Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize, 
			@RequestParam(value="audit", required=false) Integer audit,
			@RequestParam(value="keyword", required=false) String keyword) throws UnsupportedEncodingException {
		if( !(audit == -1 || audit == 1 || audit == 0) ){
			audit = null;
		}
		pageNum = pageNum == null? 1:pageNum;
		pageSize = pageSize==null? 12:pageSize;
		PageHelper.startPage(pageNum, pageSize);
		if(keyword.equals("undefined"))
			keyword = null;
		else{
		keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		return new PageInfo<UserEntity>(userDao.findAllUser(audit,keyword));
		
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
	public Object updateUser(HttpServletRequest request, 
			@RequestParam(value="realname",required=false)String realname,
			@RequestParam(value="telephone",required=false)String telephone,
			@RequestParam(value="position",required=false)String position,
			@RequestParam(value="company",required=false)String company,
			@RequestParam(value="address",required=false)String address,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="signature",required=false)String signature,
			@RequestParam(value="sex",required=false)int sex,
			
			@RequestParam(value="pr_id",required=false)int pr_id,
			@RequestParam(value="ci_id",required=false)int ci_id,
			@RequestParam(value="sh_id",required=false)int sh_id,
			@RequestParam(value="major",required=false)String major,
			@RequestParam(value="skill",required=false)String skill,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="interest",required=false)String interest,
			@RequestParam(value="age",required=false)int age
			) throws ApiException {
		UserEntity user = new UserEntity();
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		user.setAddress(address);
		user.setRealname(realname);
		user.setTelephone(telephone);
		user.setPosition(position);
		user.setCompany(company);
		user.setPhone(phone);
		user.setEmail(email);
		user.setSignature(signature);
		user.setSex(sex);
		
		user.setProvinceid(pr_id);
		user.setCityid(ci_id);
		user.setSchoolid(sh_id);
		user.setMajor(major);
		user.setSkill(skill);
		user.setNickname(nickname);
		user.setInterest(interest);
		user.setAge(age);
		if(!user.getId().equals(0)){
			if(StringUtil.isnotNull(user.getPassword()))
			{
				user.setPassword(EncodeUtil.encodeByMD5(user.getPassword()));
			}
			this.userDao.updateUser(user);
			UserEntity	ol_user = this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ResponseData.newSuccess(ol_user);
		}
		return ResponseData.newFailure();
	}
	
	@RequestMapping(value = "/updateAvatar")
	@ResponseBody
	public Object updateAvatar(HttpServletRequest request, @RequestParam(required = false) MultipartFile useravatar) throws ApiException {
		UserEntity user = new UserEntity();
		UserEntity old_user = (UserEntity)request.getSession().getAttribute("user");
		user.setId(old_user.getId());
		if(useravatar!=null){
			String dir = String.format("%s/user/%s", baseDir, user.getId());
			String fileName = String.format("user%s_%s.%s", user.getId(), new Date().getTime(), "jpg");
			UploadFileEntity uploadFileEntity = new UploadFileEntity(fileName, useravatar, dir);
			ftpService.uploadFile(uploadFileEntity);
			user.setAvatar(FtpService.READ_URL+dir + "/" + fileName);
			this.userDao.updateUser(user);
			UserEntity	ol_user = this.userDao.findUserById(user.getId().intValue());
			ol_user.setPassword("********");
			request.getSession().setAttribute("user",ol_user);
			return ResponseData.newSuccess(ol_user);
		}
		return ResponseData.newFailure();
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
