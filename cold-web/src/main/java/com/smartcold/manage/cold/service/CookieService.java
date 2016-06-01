package com.smartcold.manage.cold.service;

import com.smartcold.manage.cold.entity.CookieEntity;

public interface CookieService {

	static int EXPIERD_TIME = 60;

	public CookieEntity findEffectiveCookie(String cookie);

	public void deleteCookie(String cookie);
}
