package com.competition.project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

// 错误请求页面配置
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar{
    private static final Logger logger = LoggerFactory.getLogger(ErrorPageConfig.class);

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage page403 = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
        ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        registry.addErrorPages(page404,page403,page500);
    }
}
