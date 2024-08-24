package com.test.trainingprogrambackend.controller;


import com.test.trainingprogrambackend.util.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "手机验证码操作")
public class PhoneController {
    @ApiOperation("发送手机验证码（别轻易调用，花费次数的）")
    @GetMapping("/phone/sendCode")
    public String getPhoneCode(String phone, HttpSession httpSession) {
        if(phone==null){
            return "手机未填写";
        }
        try{
            String authcode="1"+ RandomStringUtils.randomNumeric(5);
            httpSession.setAttribute("Code", authcode);
            Message.messagePost(phone,authcode);
            return "发送成功"+authcode;
        }catch(Exception e){
            e.printStackTrace();
            return "发送失败，请重试";
        }

    }
    @ApiOperation("验证手机验证码")
    @GetMapping("/phone/checkCode")
    public String checkPhoneCode(String code, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "验证码未发送";
        }
        System.out.println("Session Id:"+session.getId());
        String Code=(String)session.getAttribute("Code");
        if(code.equals(Code)) {
            return "验证码正确";
        }
        return "验证码错误";
    }
}
