package com.test.trainingprogrambackend.interceptor;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.trainingprogrambackend.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map = new HashMap<>();
        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        //获取header里的token
        String token = request.getHeader("authorization");
        if (token==null){

            String result = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json:charset=UTF=8");
            response.getWriter().println(result);
            return false;
        }
        //验证token是否正确
        Claims claims=JwtUtils.getClaimsByToken(token);
        if (claims==null){

            String json = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json:charset=UTF=8");
            response.getWriter().println(json);
            return false;
        }
        //拦截器 拿到用户信息，放到request中
        String idCard=claims.getSubject();
        request.setAttribute("idCard",idCard);

        return true;
    }
}
