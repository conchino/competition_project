package com.competition.project.utils;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

/*
    随机生成盐
 */
public class SaltUtils {
        // 设定生成盐的随机字符范围
        private final static String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#&$%^+*";
        public static String uuid = UUID.randomUUID().toString().replace("-", "");

        /**
         * 生成十六位盐
         * @return
         */
        public static String getSalt(){
            String saltID = "";
            for (int i = 0; i < 16; i++) {
                char ch = str.charAt(new Random().nextInt(str.length()));
                saltID += ch;
            }
            return saltID;
        }

        /*
         * 生成指定长度盐
         */
        public static String getSalt(int len){
            String saltID = "";
            for (int i = 0; i < len; i++) {
                char ch = str.charAt(new Random().nextInt(str.length()));
                saltID += ch;
            }
            return saltID;
        }

        /*
         * 使用uuid 生成盐
         */
        public static String getUUIDSalt() {
            String salt = uuid.substring(0, 16);
            return salt;
        }


    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("输入随机密码长度: ");
//        if (scanner.hasNextInt()) {
//            System.out.println(getSalt(scanner.nextInt()));
            String salt = getUUIDSalt();
            System.out.println("uuid:         "+salt);
//        }
//        while (scanner.hasNextInt()) {
            System.out.println("randomSalt:   "+getSalt());
//        }


    }
}
