package com.test.trainingprogrambackend.Service;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailService {
    private Integer code;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    public String sendMail(String to) {
        code = RandomUtil.randomInt(10000,99999);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Hiya新生报到平台");
        message.setText("你的验证码为："+code.toString()+"请勿将验证码泄露给他人！");
        mailSender.send(message);
        return code.toString();
    }
}
