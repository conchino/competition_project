package com.competition.project.utils.log;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.competition.project.annotation.Log;
import com.competition.project.entity.History;
import com.competition.project.enumeration.OperationType;
import com.competition.project.service.HistoryService;
import com.competition.project.utils.IpUtils;
import com.competition.project.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

// Controller日志
@Aspect
@Component
public class WebLogAspect {
    @Autowired
    private HistoryService historyService;

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.competition.project.controller..*.*(..))")
    public void controllerLog(){}

    /* 选中 EmployeesController中所有的方法 */
    @Pointcut("execution(public * com.competition.project.controller.EmployeesController.*(..))")
    public void EmployeesControllerLog(){}

//    @Before("controllerLog() || EmployeesControllerLog()")
    @Before("EmployeesControllerLog()")
    public void logBeforeController(JoinPoint joinPoint) throws UnknownHostException {
        // 历史记录对象
        History historyObj = new History();

        String timestamp = String.valueOf(new Date().getTime());
        historyObj.setHistoryId(timestamp);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        logger.info("URL : " + request.getRequestURL().toString());
        historyObj.setOperatorAddress(request.getRequestURL().toString());

        logger.info("request方法 : " + request.getMethod());

        logger.info("IP地址 : " + IpUtils.getIpAddress(request) + " || " + InetAddress.getLocalHost().getHostAddress());
        logger.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
        /*  getSignature().getDeclaringTypeName()  获取包+类名
            joinPoint.getSignature.getName()  获取方法名
        */
        logger.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("具体方法: "+joinPoint.getSignature().getName());
        historyObj.setOperationMethod(joinPoint.getSignature().getName());

        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        logger.info("sessionId -- "+session.getId());

        // 获取请求参数名以及对应值
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>();

        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        String parameter = JSON.toJSONString(parameterMap);
        logger.info("参数parameter: "+parameter);
        historyObj.setParameter(parameter);

        // 获取Cookies中的token
        Cookie[] cookies = request.getCookies();
        String token = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token =  cookie.getValue();
                }
            }
        }

        // 获取到切面方法上的 @Log注解中的值
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();

        // 仅在方法存在 @Log注解且 token不为空时，将该次操作记录下来
        if (method.isAnnotationPresent(Log.class) && !token.isEmpty()) {

            Map<String, Claim> claims = JwtUtil.getClaims(token);
            String name = claims.get("name").asString();
            String workId = claims.get("workId").asString();
            logger.info("操作用户: "+name);
            historyObj.setOperator(name+"|"+workId);

            OperationType operationType = method.getAnnotation(Log.class).operationType();
            String operationName = method.getAnnotation(Log.class).operationName();

            historyObj.setOperationType(operationType.getType());
            historyObj.setDescription(operationName);

            logger.info("操作方法类型: "+operationType);
            logger.info("操作方法描述: "+operationName);

            logger.info("时间戳序号: "+ timestamp);
            logger.info("操作时间: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
            // 记录操作信息
            historyService.addHistoryRecords(historyObj);
        }


    }
}
