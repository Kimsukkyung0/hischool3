package com.green.secondproject;

import com.green.secondproject.config.security.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CommonUserUtilsForTest {
    private UserDetails fkUser() throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_STD");
        UserDetails user = MyUserDetails.builder()
                .userId(40L)
                .email("aa@test.com")
                .pw("123")
                .schoolNm("오성고등학교")
                .grade("1")
                .classNum("1")
                .pic("test.jpg")
                .roles(roles)
                .build();
        return user;
    }

    public UserDetails getFkUserDetails(){
        return fkUser();
    }
}
