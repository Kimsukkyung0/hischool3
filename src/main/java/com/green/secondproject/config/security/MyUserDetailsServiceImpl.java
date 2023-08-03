package com.green.secondproject.config.security;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.sign.model.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo vo = mapper.selUserByEmail(username);
        log.info("vo: {}", vo);
        return MyUserDetails.builder()
                .userId(vo.getUserId())
                .email(vo.getEmail())
                .pw(vo.getPw())
                .schoolNm(vo.getSchoolNm())
                .grade(vo.getGrade())
                .classNum(vo.getClassNum())
                .pic(vo.getPic())
                .roles(Collections.singletonList(vo.getRole()))
                .build();
    }
}
