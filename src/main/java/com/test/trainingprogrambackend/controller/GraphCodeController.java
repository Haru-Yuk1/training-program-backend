package com.test.trainingprogrambackend.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void getGraphCode(HttpServletResponse response,HttpSession httpSession) {
        //随机生成4位验证码
        RandomGenerator randomGenerator= new RandomGenerator("0123456789",4);

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        try{
            lineCaptcha.setGenerator(randomGenerator);
            lineCaptcha.write(response.getOutputStream());
            System.out.println("生成的验证码："+lineCaptcha.getCode());

            httpSession.setAttribute("Code", lineCaptcha.getCode());
            System.out.println(httpSession.getId());
            response.getOutputStream().close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @ApiOperation("验证图片验证码")
    @GetMapping("/checkGraphCode")
    public String checkCode(String code, HttpServletRequest request) {
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
