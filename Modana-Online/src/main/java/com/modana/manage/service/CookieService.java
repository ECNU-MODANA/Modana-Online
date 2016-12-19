package com.modana.manage.service;

import com.modana.manage.entity.CookieEntity;

public interface CookieService {

	static int EXPIERD_TIME = 60;
	
	public String insertCookie(String username);

	public CookieEntity findEffectiveCookie(String cookie);

	public void deleteCookie(String cookie);
}
