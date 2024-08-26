package com.test.trainingprogrambackend.controller;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth")
public class RestAuthController {
    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest=getAuthRequest();
//        System.out.println("生成登录链接："+authRequest.authorize(AuthStateUtils.createState()));
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }
    @RequestMapping("/callback")
    public Object login(AuthCallback authCallback) {
        AuthRequest authRequest=getAuthRequest();
        return authRequest.login(authCallback);
    }

    private AuthRequest getAuthRequest() {
        return new AuthQqRequest(AuthConfig.builder()
                .clientId("102321305")
                .clientSecret("paEiz2rVw9B6pKbf")
                .redirectUri("http://localhost:8080/hello")
                .build());
    }
}
