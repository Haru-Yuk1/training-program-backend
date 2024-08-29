package com.test.trainingprogrambackend.controller;


import com.test.trainingprogrambackend.Service.EmailService;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.Result;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "邮箱验证码操作")
@CrossOrigin(maxAge = 100000)
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;


    @ApiOperation("发送邮箱验证码")
    @GetMapping("/sendCode")
    public Result sendCode(String to) {
        if(to==null){
            return Result.error().message("邮箱为空");
        }
        try{
            String randomCode=emailService.sendMail(to);
            String token= JwtUtils.generateEmailToken(to,randomCode);
            System.out.println(token);
            return Result.ok().data("token",token).message("邮箱验证码发送成功");


        }catch(Exception e){
            e.printStackTrace();
            return Result.error().message("邮箱验证码发送失败");
        }
    }
    @ApiOperation("验证邮箱验证码")
    @GetMapping("/checkCode")
    public Result checkCode(String code, @RequestHeader("Authorization") String token) {
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
