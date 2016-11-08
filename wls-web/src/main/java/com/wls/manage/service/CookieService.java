package com.wls.manage.service;

import com.wls.manage.entity.CookieEntity;

public interface CookieService {

	static int EXPIERD_TIME = 60;

	public String insertCookie(String username);

	public CookieEntity findEffectiveCookie(String cookie);

	public void deleteCookie(String cookie);
}
