package com.wls.manage.crawler.general;

/**
 * Created by haolidong on 2016/12/13.
 */
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CrawlTimer {
    static int count = 0;

    public static void runTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("时间=" + new Date()); // 1次
            }
        };

        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的23:00:00执行，
        calendar.set(year, month, day, 23, 00, 00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(task, date);
    }
    
    public static void main(String[] args) {
        runTimer();
    }
}
