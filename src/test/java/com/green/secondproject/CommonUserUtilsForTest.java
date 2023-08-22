package com.green.secondproject;

import com.green.secondproject.common.config.security.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CommonUserUtilsForTest {
    private UserDetails studentUser() throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_STD");
        UserDetails userStd = MyUserDetails.builder()
                .userId(40L)
                .email("aa@test.com")
                .pw("123")
                .schoolNm("오성고등학교")
                .grade("1")
                .classNum("1")
                .pic("test.jpg")
                .roles(roles)
                .build();
        return userStd;
    }

    public UserDetails getStudentUserDetails(){
        return studentUser();
    }

    private UserDetails teacherUser() throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_TC");
        UserDetails userTc = MyUserDetails.builder()
                .userId(80L)
                .email("bb@test.com")
                .pw("123")
                .schoolNm("오성고등학교")
                .grade("3")
                .classNum("1")
                .pic("test.jpg")
                .roles(roles)
                .build();
        return userTc;
    }

    public UserDetails getTeacherUserDetails(){
        return teacherUser();
    }
}
