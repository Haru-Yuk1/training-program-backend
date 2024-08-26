package com.test.trainingprogrambackend.util;

import lombok.Data;

import java.io.Serializable;


@Data
public class R implements Serializable {
    //响应状态码
    private int code;
    //消息提示
    private String msg;
    //响应数据
    private Object data;

    //执行成功
    public static R success(int code, String msg, Object data){
        return ok(code, msg ,data);
    }

    //执行失败
    public static R fail(int code, String msg) {
        return err(code,msg);
    }

    public static R ok(int code, String msg, Object data) {
        R r= new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static R err(int code,String msg) {
        R r= new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
