package com.modana.manage.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.modana.manage.dao.UserMapper;
import com.modana.manage.entity.CookieEntity;
import com.modana.manage.entity.UserEntity;
import com.modana.manage.service.CookieService;
import com.modana.manage.util.EncodeUtil;
import com.modana.manage.util.ResponseData;
import com.modana.manage.util.StringUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	private UserMapper userDao;

	@Autowired
	private CookieService cookieService;

	/**
	 * @param @param
	 *            userName
	 * @param @param
	 *            password
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @return
	 *            ModelAndView
	 * @param @throws
	 *            Exception
	 * @return true/false
	 * @throws @Title:
	 *             login UserController
	 * @Description: 用户登录
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(HttpServletRequest request, String userName, String password, HttpServletResponse response) {
		if(StringUtil.isnotNull(userName)&&StringUtil.isnotNull(password)){
			UserEntity user = userDao.findByPassword(userName, EncodeUtil.encodeByMD5(password));
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
		request.getSession().setAttribute("user", null);
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("token")) {
				cookieService.deleteCookie(cookie.getValue());
			}
		}
		return true;
	}

	@RequestMapping(value = "/findUser", method = RequestMethod.GET)
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
}
