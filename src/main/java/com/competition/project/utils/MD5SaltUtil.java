package com.competition.project.utils;

import org.apache.commons.codec.digest.DigestUtils;

// MD5 加盐加密
public class MD5SaltUtil {
    //使用加密工具类对输入数据进行MD5加密
    public static String createCode(String code) {
        return DigestUtils.md5Hex(code);
    }
    /*
     *  密码校验
     */
    public static boolean checkCode(String userCode, String dbCode) {
        if (dbCode.equals(createCode(userCode))) {
            return true;
        } else {
            return false;
        }
    }
    /*
     *  密码加盐校验
     */
    public static boolean checkCode(String userCode, String dbCode,String salt) {
        if (dbCode.equals(createCode(userCode+salt))) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        String password_before = "123456";
        // 使用盐工具类生成盐
        String salt = SaltUtils.getSalt();
        System.out.println("密码:  "+password_before+"\n"+"盐:    "+salt);
        // MD5 密码加盐加密
        String password_after = createCode(password_before+salt);
        // 输出加密后数据
        System.out.println("加密后数据: "+password_after);

        if (checkCode(password_before,password_after,salt)) {
            System.out.println("加密前数据与加密后数据一致 ！！！");
        }else {
            System.out.println("密码错误 ！！！");
        }
    }
}
