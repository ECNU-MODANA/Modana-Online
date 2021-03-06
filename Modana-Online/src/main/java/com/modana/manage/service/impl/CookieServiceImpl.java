package com.modana.manage.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modana.manage.dao.CookieMapper;
import com.modana.manage.entity.CookieEntity;
import com.modana.manage.service.CookieService;
import com.modana.manage.util.EncodeUtil;

@Service
public class CookieServiceImpl implements CookieService {

	@Autowired
	private CookieMapper cookieDao;

	@Override
	public String insertCookie(String username) {
		Date date = new Date();
		CookieEntity cookieEntity = new CookieEntity();

		String encode = EncodeUtil.encode("sha1", String.format("%s%s", username, date.getTime()));
		cookieEntity.setUsername(username);
		cookieEntity.setCookie(encode);
		cookieEntity.setExpireTime(EXPIERD_TIME);
		cookieDao.insertCookie(cookieEntity);

		return encode;
	}

	@Override
	public CookieEntity findEffectiveCookie(String cookie) {
		return cookieDao.findEffectiveCookie(cookie);
	}

	@Override
	public void deleteCookie(String cookie) {
		cookieDao.deleteCookie(cookie);
	}

}
