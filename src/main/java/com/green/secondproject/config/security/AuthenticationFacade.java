package com.green.secondproject.config.security;

import com.green.secondproject.config.security.model.MyUserDetails;
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
        return (MyUserDetails) auth.getPrincipal();
    }

    public Long getLoginUserPk() {
        return getLoginUser().getUserId();
    }
}
