package com.test.trainingprogrambackend.util;


import java.util.HashMap;
import java.util.Map;

//统一返回结果的类
public class Result {
    private Boolean success;
    private Integer code;
    private String message;

    private Map<String,Object> data=new HashMap<String,Object>();
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    private Result(){}

    //成功静态方法
    public static Result ok(){
        Result r=new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    //失败静态方法
    public static Result error(){
        Result r=new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.FAILURE);
        r.setMessage("失败");
        return r;
    }
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }
    public Result message(String message){
        this.setMessage(message);
        return this;
    }
    public Result data(Map<String, Object> data){
        this.setData(data);
        return this;
    }
    public Result data(String key,Object value){
        this.data.put(key, value);
        return this;
    }
}
