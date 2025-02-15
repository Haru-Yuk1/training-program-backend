package com.test.trainingprogrambackend.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.Result;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@CrossOrigin(maxAge = 3600)
@Api(tags = "图片验证码")
public class GraphCodeController {

    @ApiOperation("获取图片验证码")
    @GetMapping("/getGraphCode")
    public void getGraphCode(HttpServletResponse response) {
        // 随机生成4位验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        try {
            lineCaptcha.setGenerator(randomGenerator);

            // 生成 token 并设置到响应头中
            String token = JwtUtils.generateToken(lineCaptcha.getCode());
            response.setHeader("Authorization", token);

            // 写入图片数据
            lineCaptcha.write(response.getOutputStream());
            System.out.println("生成的验证码：" + lineCaptcha.getCode());
            System.out.println(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ApiOperation("验证图片验证码")
    @GetMapping("/checkGraphCode")
    public Result checkCode(String code, @RequestHeader("Authorization") String token) {

        Claims claims = JwtUtils.getClaimsByToken(token);
        String storedCode= claims.getSubject();
        System.out.println(storedCode);
        if(storedCode==null){
            return Result.error().message("验证码找不到了");
        }

        if(storedCode.equals(code)) {
            return Result.ok().message("验证码正确");
        }
        return Result.error().message("验证码错误");
    }


}
