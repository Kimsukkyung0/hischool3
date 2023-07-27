//package com.green.secondproject.config.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.green.security.config.security.model.EntryPointErrorResponse;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import java.io.IOException;
//
//@Slf4j
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        log.info("[commence] 인증 실패로 response.sendError 발생");
//
//        EntryPointErrorResponse msg = new EntryPointErrorResponse();
//        msg.setMsg("인증이 실패하였습니다.");
//
//        res.setStatus(401);
//        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        res.setCharacterEncoding("UTF-8");
//        res.getWriter().write(objectMapper.writeValueAsString(msg));
//    }
//}
