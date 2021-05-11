package com.competition.project.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/* 配置重写shiro ShiroFilterFactoryBean 登录过滤拦截类 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter{
    private static final Logger log = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

            if (this.isLoginRequest(request, response)) {
                if (this.isLoginSubmission(request, response)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Login submission detected.  Attempting to execute login.");
                    }

                    return this.executeLogin(request, response);
                } else {
                    if (log.isTraceEnabled()) {
                        log.trace("Login page view.");
                    }

                    return true;
                }
            }else {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse resp = (HttpServletResponse) response;
                if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
                    resp.setStatus(HttpStatus.OK.value());
                    return true;
                } else {
                    if (log.isTraceEnabled()) {
                        log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [{}]", this.getLoginUrl());
                    }

                    log.info("FormAuthenticationFilter 生效... || 未登录...");

                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.setStatus(200);
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletResponse.getHeader("Origin"));
                    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                    httpServletResponse.setHeader("Cache-Control", "no-cache");

                    PrintWriter writer = httpServletResponse.getWriter();
                    JSONObject json = new JSONObject();
                    json.put("state", "403");
                    json.put("success", false);
                    json.put("code", 20001);
                    json.put("message", "请先登录!");
                    writer.println(json);
                    writer.flush();
                    writer.close();

                    return false;
                }
            }
        }
}
