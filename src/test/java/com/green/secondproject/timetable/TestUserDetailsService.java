package com.green.secondproject.timetable;

import com.green.secondproject.config.security.MyUserDetailsServiceImpl;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.config.security.model.MyUserDetails;
import jakarta.annotation.Priority;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Profile("userTest")
@Primary
public class TestUserDetailsService extends MyUserDetailsServiceImpl {

    public TestUserDetailsService(UserMapper mapper) {
        super(mapper);
    }


    public MyUserDetails getUnAuthUser() throws UsernameNotFoundException {
        return MyUserDetails.builder()
                .userId(1424L)
                .email("test@test.com")
                .pw("test")
                .schoolNm("testHighschool")
                .grade("1")
                .classNum("1")
                .pic("test.jpg")
                .roles(Collections.singletonList("STD"))
                .build();
    }

    public MyUserDetails getAuthUserStd()  throws UsernameNotFoundException{
        return  MyUserDetails.builder()
                .userId(1L)
                .email("test@test.co.kr")
                .pw("test")
                .nm("김테스트")
                .schoolNm("테스트고등학교")
                .grade("1")
                .classNum("1")
                .pic("pic.jpg")
                .roles(Collections.singletonList("ROLE_STD"))
                .build();
    }

    public MyUserDetails getAuthUserTcr()  throws UsernameNotFoundException{
        return  MyUserDetails.builder()
                .userId(1L)
                .email("test@test.co.kr")
                .pw("test")
                .nm("김테스트")
                .schoolNm("테스트고등학교")
                .grade("1")
                .classNum("1")
                .pic("pic.jpg")
                .roles(Collections.singletonList("ROLE_TC"))
                .build();
    }
}
