package com.competition.project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.StringUtils;

import javax.security.auth.login.AccountException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Jwt工具类
public class JwtUtil {
        /*
            JWT消息构成
            一个token分3部分，按顺序为
            * 头部（header)
            * 载荷（payload)
            * 签证（signature)
            由三部分生成token
            3部分之间用“.”号做分隔
         */

    // 设置 jwt 加密密钥
    private static final String SECRET = "Wzvf&ELJkrWab%3yl4#Z@Ar^SFcSE*";
    // 设置过期时间  两小时
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    // 生成签名 (固定测试)
    public static String sign() {
        try {
            // 设置过期的时间  以毫秒计算  2小时后过期
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("alg", "HS256");
            headerMap.put("typ", "JWT");
            return JWT.create()
                    .withHeader(headerMap)
                    // 将 user id 保存到 token 里面
                    //。withAudience()唯一
                    .withAudience("向客户端发送的信息", "内容可以有多条", "...String")
                    //.withAudience()
                    // 最终存放的数据在JWT内部的实体claims里。它是存放数据的地方
                    .withClaim("userId", "zhengwei")
                    .withClaim("age", "30")
                    .withClaim("phone", "1810824293X")

                    .withIssuedAt(new Date())   // 发行时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))  // 过期时间
                    .sign(algorithm);   // 使用加密算法生成签名
        } catch (Exception e) {
            return null;
        }
    }


    // token中存入用户信息（支持六种类型）
    // Integer,Long,Boolean,Double,String,Date
    public static String sign(Map<String,Object> map){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("alg", "HS256");
            headerMap.put("typ", "JWT");

            JWTCreator.Builder builder = JWT.create()
                    .withHeader(headerMap)
                    .withAudience("向客户端发送的信息", "内容可以有多条", "这是第三条")
                    .withIssuedAt(new Date())   // 发行时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME));  // 过期时间
            // map.forEach 迭代取代map.entrySet().forEach()
            map.forEach((key, value) -> {
                if (value instanceof Integer) {
                    builder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(key, (Long) value);
                } else if (value instanceof Boolean) {
                    builder.withClaim(key, (Boolean) value);
                } else if (value instanceof String) {
                    builder.withClaim(key, String.valueOf(value));
                } else if (value instanceof Double) {
                    builder.withClaim(key, (Double) value);
                } else if (value instanceof Date) {
                    builder.withClaim(key, (Date) value);
                }
            });
            return builder.sign(algorithm);

        } catch (Exception e) {
            return null;
        }
    }


    public static String sign(String userId){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("alg", "HS256");
            headerMap.put("typ", "JWT");

            return JWT.create()
                    .withHeader(headerMap)
                    .withAudience("向客户端发送的信息", "内容可以有多条", "...String")
                    .withClaim("userId", userId)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .sign(algorithm);

        } catch (Exception e) {
            return null;
        }
    }



    public static String sign(String userId, Map<String,Object> map){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("alg", "HS256");
            headerMap.put("typ", "JWT");

            JWTCreator.Builder builder = JWT.create()
                    .withHeader(headerMap)
                    .withAudience("向客户端发送的信息", "内容可以有多条", "这是第三条")
                    .withIssuedAt(new Date(System.currentTimeMillis()))   // 发行时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME));  // 过期时间
            // 指定存放用户Id
            builder.withClaim("userId",userId);
            // 存放其他数据
            map.forEach((key, value) -> {
                if (value instanceof Integer) {
                    builder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(key, (Long) value);
                } else if (value instanceof Boolean) {
                    builder.withClaim(key, (Boolean) value);
                } else if (value instanceof String) {
                    builder.withClaim(key, String.valueOf(value));
                } else if (value instanceof Double) {
                    builder.withClaim(key, (Double) value);
                } else if (value instanceof Date) {
                    builder.withClaim(key, (Date) value);
                }
            });
            return builder.sign(algorithm);

        } catch (Exception e) {
            throw new AuthorizationException("授权异常");
//            return null;
        }
    }



    // 校验Token
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
//            System.out.println("header:  " + jwt.getHeader());
//            System.out.println(JSON.toJSONString(jwt.getClaims()));
//            System.out.println("Claims:  ");
//            System.out.println("userId:  "+jwt.getClaims().get("userId").asString());
//            System.out.println("age:     "+jwt.getClaims().get("age").asString());
//            System.out.println("phone:   "+jwt.getClaims().get("phone").asString());
//            System.out.println("Iss:     "+jwt.getIssuedAt());
//            System.out.println("exp:     "+jwt.getExpiresAt());
//            System.out.println("Audience: "+jwt.getAudience());
            return true;
        } catch (JWTVerificationException exception) {
            System.out.println(exception.getMessage());
            System.out.println("token 无效");
            return false;
        }
    }


    //  获取Audience (String的list)
    public static List<String> getAudience(String token) {
        try {
            return JWT.decode(token).getAudience();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    // 获取UserId
    public static String getUserId(String token) {
        try {
            return JWT.decode(token).getClaims().get("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    // 获取用户自定义Claim集合 (得到登陆时传入的Map数据)
    public static Map<String, Claim> getClaims(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token).getClaims();
    }


    // 获取过期时间
    public static Date getExpiresAt(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return  JWT.require(algorithm).build().verify(token).getExpiresAt();
    }

    // 获取发行时间
    public static Date getIssuedAt(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return  JWT.require(algorithm).build().verify(token).getIssuedAt();
    }

    // 验证是否失效
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiresAt(token);
            return expiration.before(new Date());
        }catch (TokenExpiredException e) {
            // e.printStackTrace();
            return true;
        }

    }


    // 直接解密获取Header
    public static String getHeaderByBase64(String token){
        if (StringUtils.isEmpty(token)){
            return null;
        }else {
            byte[] header_byte = Base64.decodeBase64(token.split("\\.")[0]);
            return new String(header_byte);
        }

    }

    // 直接Base64解密获取payload内容
    public static String getPayloadByBase64(String token){
        if (token.isEmpty()){
            return null;
        }else {
            byte[] payload_byte = Base64.decodeBase64(token.split("\\.")[1]);
            String payload = new String(payload_byte);
            return payload;
        }
    }


    // 获取签证
    public static String getSecretCode(String token){
        if (token.isEmpty()){
            return null;
        }else {
            return new String(token.split("\\.")[2]);
        }
    }



    public static void main(String[] args) throws AccountException {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username","张三");
        map.put("userId","123456789");

        String sign = JwtUtil.sign(map);

        System.out.println("生成的签名为:  "+sign);
        System.out.println(verify(sign));
        System.out.println(getHeaderByBase64(sign));
        System.out.println(getPayloadByBase64(sign));
        System.out.println(getSecretCode(sign));
    }


    /* HS256 加密
        try {
    Algorithm algorithm = Algorithm.HMAC256("secret");
    String token = JWT.create()
                .withIssuer("rstyro")   //发布者
                .withSubject("test")    //主题
                .withAudience(audience)     //观众，相当于接受者
                .withIssuedAt(new Date())   // 生成签名的时间
                .withExpiresAt(DateUtils.offset(new Date(),2, Calendar.MINUTE))    // 生成签名的有效期,分钟
                .withClaim("data", JSON.toJSONString("Object")) //自定义字段存数据
                .withNotBefore(new Date())  //生效时间
                .withJWTId(UUID.randomUUID().toString())    //编号
                .sign(algorithm);
    } catch (JWTCreationException exception){
        //签名不匹配
    }
    */
}