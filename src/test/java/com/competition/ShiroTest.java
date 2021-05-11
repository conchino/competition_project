package com.competition;
;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.security.Security;

@SpringBootTest
public class ShiroTest {

    @Test
    public void Test01(){
//        String username = "admin_root";
//        String password = "123456";
//
//        UsernamePasswordToken shiro_token = new UsernamePasswordToken(username, password);

        System.out.println("报你先人错呢");

        Subject subject = SecurityUtils.getSubject();
        System.out.println("还报错?");
        System.out.println("已登录?  "+subject.isAuthenticated());

//        Session session = subject.getSession();
//        System.out.println(session.getId());

//        SecurityManager manager = SecurityUtils.getSecurityManager();
    }

    /*
     *  报错:  No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.
     *        ThreadContext or as a vm static singleton.  This is an invalid application configuration.
     *
     *  原因: 在当前的环境中获取的 shiro 的SecurityManager 为空
     */
}
