package com.competition.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/*
    根据时间戳来获取ID
 */
public class IDUtils {

    // 截取时间戳，随机生成ID
    public static String getID(){
        String substring = String.valueOf(new Date().getTime()).substring(4);
        return substring;
    }

    public static String getGuid(){
        StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        int n = (int)(Math.random() * 90000.0D + 10000.0D);
        return now.append(n).toString();
    }

    public static String getZcbhid(){
        StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int n = (int)(Math.random() * 9000.0D + 1000.0D);
        return now.append(n).toString();
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Date());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(30);
            System.out.println(IDUtils.getID()+"  ---  "+ new Date().getTime());
            System.out.println(IDUtils.getZcbhid());
        }
    }
}
