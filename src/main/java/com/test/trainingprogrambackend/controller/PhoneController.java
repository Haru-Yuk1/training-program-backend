package com.test.trainingprogrambackend.controller;


import com.test.trainingprogrambackend.Service.EmailService;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.Message;
import com.test.trainingprogrambackend.util.Result;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "手机验证码操作")
@RequestMapping("/phone")
public class PhoneController {


    @ApiOperation("发送手机验证码（别轻易调用，花费次数的）")
    @GetMapping("/sendCode")
    public Result getPhoneCode(String phone) {
        if(phone==null){
            return Result.error().message("电话为空");
        }
        try{
            String authcode="1"+ RandomStringUtils.randomNumeric(5);
            String token= JwtUtils.generateEmailToken(phone,authcode);
            System.out.println(authcode);
            System.out.println(token);
//            Message.messagePost(phone,authcode);
            return Result.ok().data("token",token).message("短信验证码发送成功");
        }catch(Exception e){
            e.printStackTrace();
            return Result.error().message("短信验证码发送失败");
        }

    }
    @ApiOperation("验证手机验证码")
    @GetMapping("/checkCode")
    public Result checkPhoneCode(String code, @RequestHeader("Authorization") String token) {
        Claims claims = JwtUtils.getClaimsByToken(token);
        System.out.println(claims.get("code"));
        String storedCode = (String) claims.get("code");
        if(storedCode==null){
            return Result.error().message("验证码找不到了");
        }
        if(storedCode.equals(code)) {
            return Result.ok().message("验证码正确");
        }
        return Result.error().message("验证码错误");
    }
}
