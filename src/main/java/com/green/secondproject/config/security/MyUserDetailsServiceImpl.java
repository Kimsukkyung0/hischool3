package com.green.secondproject.config.security;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.config.security.model.UserEntity;
import com.green.secondproject.sign.model.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo vo = mapper.selUserByEmail(username);
        return MyUserDetails.builder()
                .email(vo.getEmail())
                .userId(vo.getUserId())
                .pw(vo.getPw())
                .roles(Collections.singletonList(vo.getRole()))
                .build();
    }
}
