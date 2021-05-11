package com.competition.project.handler;

import com.competition.project.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 自定义全局异常处理返回类
@Slf4j
@ControllerAdvice  // 全局异常处理 | 全局数据绑定 | 全局数据预处理
public class MyExceptionHandler {
    // 处理shiro 权限异常

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result handleShiroException(Exception ex){
        log.error("权限不足", ex);
        if (ex.getMessage().equals("Subject does not have permission [user:perm_1]")||
                ex.getMessage().equals("Subject does not have permission [user:perm_2]")||
                ex.getMessage().equals("Subject does not have permission [user:perm_3]")){
            return Result.error().message("权限不足");
        }
        return Result.error().message(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Result AuthorizationException(Exception ex) {
        log.error("权限认证失败", ex);
        return Result.error().message("权限认证失败 "+ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccountException.class)
    public Result AccountException(Exception ex) {
        log.error(ex.getMessage());
        return Result.error().message(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnknownAccountException.class)
    public Result UnknownAccountException(Exception ex) {
        log.error(ex.getMessage());
        return Result.error().message(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result IncorrectCredentialsException(Exception ex) {
        log.error(ex.getMessage());
        return Result.error().message(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(LockedAccountException.class)
    public Result LockedAccountException(Exception ex) {
        log.error("账户已锁定",ex);
        return Result.error().message("账户已锁定");
    }

    @ResponseBody
    @ExceptionHandler(ExcessiveAttemptsException.class)
    public Result ExcessiveAttemptsException(Exception ex) {
        log.error("用户名或密码错误次数过多",ex);
        return Result.error().message("用户名或密码错误次数过多");
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result Exception(Exception ex){
        log.error(ex.getMessage());
        return Result.error().message(ex.getMessage());
    }
}
