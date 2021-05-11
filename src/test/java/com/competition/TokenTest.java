package com.competition;

import com.competition.project.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
public class TokenTest {

    @Test
    public void token_test() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsi5ZCR5a6i5oi356uv5Y-R6YCB55qE5L-h5oGvIiwi5YaF5a655Y-v5Lul5pyJ5aSa5p2hIiwiLi4uU3RyaW5nIl0sImV4cCI6MTYxODMxOTQzOSwidXNlcklkIjoiYWRtaW4iLCJpYXQiOjE2MTgzMTIyMzl9.1M7kbj9rnrmKKzWLvxv42U3BxbCWuXhlVSRS8xPOoLU";
        String token_1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsi5ZCR5a6i5oi356uv5Y-R6YCB55qE5L-h5oGvIiwi5YaF5a655Y-v5Lul5pyJ5aSa5p2hIiwiLi4uU3RyaW5nIl0sImV4cCI6MTYxODMyMDgzMSwidXNlcklkIjoiYWRtaW4iLCJpYXQiOjE2MTgzMTM2MzF9.hv0muKtwGaHaEQt1bFE0En2eiEyg80sOd68WbdkOBdo";

        if (JwtUtil.verify(token_1)) {
            System.out.println("Token 有效!");
        }
        System.out.println("Audience:  " + JwtUtil.getAudience(token_1));
        System.out.println("Claims:   " + JwtUtil.getClaims(token_1));

        // 获取token信息中的用户名
        String userId = JwtUtil.getClaims(token).get("userId").toString();
        System.out.println("userId:   "+userId);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 时间戳以秒为单位，需要乘以1000L(Long型)，才能获取正确当前时间
        String startDate = sdf.format(Long.parseLong(JwtUtil.getClaims(token).get("iat").toString())*1000L);
        String endDate = sdf.format(new Date(Long.parseLong(JwtUtil.getClaims(token).get("exp").toString())*1000L));

        System.out.println("有效时间: ");
        System.out.println(startDate + " --- " + endDate);
    }

    @Test
    public void timeStamp_test(){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

    @Test
    public void mapTest(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("mapKey1","key1...");
        map.put("mapKey2","key2...");
        System.out.println(map);
    }
}
