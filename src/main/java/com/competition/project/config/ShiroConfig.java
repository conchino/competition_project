package com.competition.project.config;

import com.competition.project.filter.MyFormAuthenticationFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author conchino
 * @create 2021-04-07-13:38
 */
@Configuration
public class ShiroConfig {

    //设置对应的过滤条件和跳转条件
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

//        shiroFilterFactoryBean.setLoginUrl("/login");                   // 登录页面

        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc",new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");          // 授权错误跳转请求

        Map<String, String> map = new LinkedHashMap<>();
        /*  anon: 无需授权  */
        map.put("/login", "anon");
        map.put("/", "anon");
        map.put("/static/**", "anon");
        map.put("/project/login-account/applyAccount", "anon");     // 开放申请账号请求
        map.put("/test1","anon");

        // 配置swagger-ui资源过滤
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/api-docs/**", "anon");
        map.put("/webjars/springfox-swagger-ui/**", "anon");

        /*  authc: 需要授权  */
        map.put("/project/department/**", "authc");
        map.put("/project/employees/**", "authc");
        map.put("/project/login-account/**", "authc");
        map.put("/project/permission/**", "authc");
        map.put("/project/position/**", "authc");
        map.put("/project/subsidiary/**", "authc");


        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

//    权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        //SecurityManager是一个接口类，我们使用其具体实现类来使用
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        // 配置缓存, 减少重复读取, 提高性能
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }


    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    // shiro 配置redis
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1:6379");     // 主机与端口号
        redisManager.setTimeout(86400);     // 过期时间为24小时
        return redisManager;
    }

    // shiro - redis 缓存
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
        // shiro-redis要求放在session里面的实体类必须有个id标识
        // 这是组成redis中所存储数据的key的一部分
        redisCacheManager.setPrincipalIdFieldName("username");
        redisCacheManager.setExpire(2*60*60);
        return redisCacheManager;
    }


//    @Bean
//    public CustomRealm customRealm() {
//        CustomRealm customRealm = new CustomRealm();
//        // 告诉realm,使用credentialsMatcher加密算法类来验证密文
//        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        customRealm.setCachingEnabled(false);
//        return customRealm;
//    }


    /* 开启注解配置权限 */

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * *
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * *
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * * @return
     */

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


//    /* 密码加密配置 */
//    @Bean(name = "credentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        // 散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        // 散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setHashIterations(2);
//        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return hashedCredentialsMatcher;
//    }


//    /*  shiro提供的SimpleHash加密算法  */
//    public static String MD5Pwd(String pwd, String salt) {
//        // 加密算法 MD5
//        // salt盐
//        // 迭代次数
////        String md5Pwd = new SimpleHash("MD5", pwd,
////                ByteSource.Util.bytes(username + "salt"), 2).toHex();
//        // new SimpleHash("加密算法名",[密码],[盐],加密次数)
//        String md5Pwd = new SimpleHash("MD5", pwd, salt, 2).toHex();
//        return md5Pwd;
//    }

}

