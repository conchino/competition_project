package com.competition.project.utils;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

// 数据传递类
@Data
public class Result {
        /*
            "是否成功"
         */
        private Boolean success;
        /*
            "返回码"
         */
        private Integer code;
        /*
            "返回消息"
         */
        private String message;
        /*
            "返回数据"
         */
        private Map<String, Object> data = new HashMap<>();

        private Result(){}

        public static Result ok(){
            Result  r = new Result ();
            r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
            r.setCode(ResultCodeEnum.SUCCESS.getCode());
            r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
            return r;
        }

        public static Result error(){
            Result  r = new Result ();
            r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
            r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
            r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
            return r;
        }

        public static Result setResult(ResultCodeEnum resultCodeEnum){
            Result  r = new Result ();
            r.setSuccess(resultCodeEnum.getSuccess());
            r.setCode(resultCodeEnum.getCode());
            r.setMessage(resultCodeEnum.getMessage());
            return r;
        }

        public Result success(Boolean success){
            this.setSuccess(success);
            return this;
        }

        public Result message(String message){
            this.setMessage(message);
            return this;
        }

        public Result code(Integer code){
            this.setCode(code);
            return this;
        }

        public Result data(String key, Object value){
            this.data.put(key, value);
            return this;
        }

        public Result data(Map<String, Object> map){
            this.setData(map);
            return this;
        }
}
