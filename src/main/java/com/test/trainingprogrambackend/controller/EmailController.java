package com.test.trainingprogrambackend.controller;


import com.test.trainingprogrambackend.Service.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class EmailController {
    @Autowired
    private emailService emailService;

    @GetMapping("/register")
    public String sendCode(String to, HttpSession httpSession) {
        try{
            String randomCode=emailService.sendMail(to);
            httpSession.setAttribute("Code", randomCode);
            System.out.println(httpSession.getId());
            return "验证码已发送到指定邮箱"+randomCode;

        }catch(Exception e){
            e.printStackTrace();
            return "验证码发送失败";
        }

    }
    @GetMapping("/checkCode")
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
