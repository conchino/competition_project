package com.competition.project.controller;

import com.competition.project.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ErrorPageController {
    // 适配错误访问请求

    @RequestMapping(value = "/403")
    @ResponseStatus(HttpStatus.OK)
    public Result error403(){
        return Result.error().message("403 Forbidden !");
    }

    @RequestMapping(value = "/404")
    @ResponseStatus(HttpStatus.OK)
    public Result error404(){
        return Result.error().message("404 Not Found !");
    }

    @RequestMapping(value = "/500")
    @ResponseStatus(HttpStatus.OK)
    public Result error500(){
        return Result.error().message("500 Page Error !");
    }
}
