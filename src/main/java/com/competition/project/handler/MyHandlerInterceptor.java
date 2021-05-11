package com.competition.project.handler;

import com.auth0.jwt.interfaces.Claim;
import com.competition.project.utils.IpUtils;
import com.competition.project.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
public class MyHandlerInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){
        String username = (String) request.getSession().getAttribute("username");
        String path = request.getRequestURI();
        String IpPath = IpUtils.getIpAddress(request);

        List<String> list = new ArrayList<>();

//        logger.info("MyHandlerInterceptor -- 拦截器生效...");
        list.add("访问path: "+path+",  Ip: "+IpPath);

        //如果发现是css或者js文件，直接放行
        if(path.contains(".css") || path.contains(".js")
                || path.contains(".png")|| path.contains(".jpg")
                ||path.contains(".ico") || path.contains(".map")
                || path.contains(".gif")){
            return true;
        }

        String token = "无token...";
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token =  cookie.getValue();
                }
            }
        }


        if (JwtUtil.verify(token)){
            Map<String, Claim> claimMap = JwtUtil.getClaims(token);

            for (Map.Entry<String, Claim> entry : claimMap.entrySet()) {
                String key = entry.getKey();
                Claim value = entry.getValue();
                if (key.equals("name") || key.equals("account") || key.equals("perm")){
                    list.add(key + " : " + value);
                }
            }
            logger.info("访问信息: \n"+list.toString());
            return true;
        }else {
            list.add("尚未登录...");
            logger.info("访问信息: \n"+list.toString());
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
