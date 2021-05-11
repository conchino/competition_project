package com.competition.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.competition.project.service.EmployeesService;
import com.competition.project.service.LoginAccountService;
import com.competition.project.utils.JwtUtil;
import com.competition.project.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Controller
public class LoginController {
    @Autowired
    private LoginAccountService loginAccountService;
    @Autowired
    private EmployeesService employeesService;


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result defaultLogin() {
        return Result.error().message("请先登录!");
    }


    @ApiOperation("登录方法")
//    @CrossOrigin    // 允许跨域请求
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@ApiParam("username, password") @RequestBody JSONObject jsonObject,
                        HttpServletResponse response){
        // post 表单提交的信息在请求体中，而且是json对象，需要使用JSONObject 来对其进行操作
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        Subject subject = SecurityUtils.getSubject();
        // token 令牌
        UsernamePasswordToken shiro_token = new UsernamePasswordToken(username, password);
        System.out.println("token_Principal:  "+shiro_token.getPrincipal());
        // 登录认证
        subject.login(shiro_token);
        if (subject.isAuthenticated()) {
            String account = shiro_token.getPrincipal().toString();
            // 获取使用者工号
            String workIdByAccount = loginAccountService.getWorkIdByAccount(account);
            // 登陆成功后根据账号获取使用者姓名
            String nameByWorkId = employeesService.getNameByWorkId(workIdByAccount);
            // 获取账号权限
            Integer userPerm = loginAccountService.getPermissionByAccount(account);
            // 上一次 登录/更新信息 时间
            Date lastLoginTime = loginAccountService.LastLoginTime(account);
            // 更新登陆时间
            loginAccountService.updateLoginTime(account);

            HashMap<String, Object> infoMap = new HashMap<>();
            infoMap.put("account",account);
            infoMap.put("name",nameByWorkId);
            infoMap.put("workId", workIdByAccount);
            infoMap.put("perm",userPerm);


            System.out.printf("用户 %s 登录!\n",nameByWorkId);

            HashMap<String, Object> map = new HashMap<>();
            map.put("username",nameByWorkId);
            map.put("workId",workIdByAccount);
            map.put("lastLoginTime",lastLoginTime);

            Session shiro_session = subject.getSession();
            shiro_session.setAttribute("username",nameByWorkId);
            System.out.println("shiro_session->username: "+shiro_session.getAttribute("username"));

            // 将token放入cookies中并返回
            Cookie cookie = new Cookie("token",JwtUtil.sign(infoMap));
            cookie.setMaxAge(60*60);    // cookies 过期时间: 1h
            cookie.setPath("/");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.addCookie(cookie);

            return Result.ok().message("  欢迎,  "+nameByWorkId+"!").data(map);
        } else {
            shiro_token.clear();
            return Result.error().message("登录失败!");
        }
    }


    @ApiOperation("退出登录")
    @ResponseBody
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Result logout(HttpServletRequest request,HttpServletResponse response){
        SecurityManager manager = SecurityUtils.getSecurityManager();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            System.out.println("已登录, 准备登出...");
//            Session session = subject.getSession();
//            HttpSession session = request.getSession();
//            System.out.println("logout->获取到session-username: "+session.getAttribute("username"));
//            session.invalidate();
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")){
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        System.out.println("cookies已销毁...");
                        response.addCookie(cookie);
                    }
                }
            }
            manager.logout(subject);
            /* 登出后 shiro_session 也会被销毁 */
            System.out.println("shiro_session已销毁...");
            System.out.println("退出登录...");
            return Result.ok().message("退出登录成功");
        }else System.out.println("未登录...");
        return Result.error().message("退出登录失败");
    }


    @ApiOperation("可直接访问的请求")
    @ResponseBody
    @RequestMapping("/test1")
    public Result FilterTest(){
        return Result.ok().message("该请求可以直接访问!");
    }

}
