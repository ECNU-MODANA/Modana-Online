package com.wls.manage.crawler.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NowTime {
    public static String getNowTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
