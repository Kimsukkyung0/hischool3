package com.green.secondproject.common.config.security;

import com.green.secondproject.common.config.security.model.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFacade {
    public MyUserDetails getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth: {}", auth);
        if ("anonymousUser".equals(auth.getPrincipal())) {
            throw new RuntimeException("로그인 필요");
        }
        return (MyUserDetails) auth.getPrincipal();
    }

    public Long getLoginUserPk() {
        return getLoginUser().getUserId();
    }
}
