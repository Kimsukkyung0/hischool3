package com.green.secondproject.subject;

import com.green.secondproject.config.RedisService;
import com.green.secondproject.config.security.JwtTokenProvider;
import com.green.secondproject.config.security.SecurityConfiguration;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.SubjectController;
import com.green.secondproject.teacher.subject.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = SubjectController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})

public class SubjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SubjectService serivce;

    @MockBean
    private RedisService redisService;

    @BeforeEach
    void beforeEach() {
        UserDetails user = createUserDetails();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));

    }

    private UserDetails createUserDetails() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_TC");
        //roles.add("ROLE_ADMIN");

        UserDetails userDetails = MyUserDetails.builder()
                .userId(3L)
                .roles(roles)
                .build();
        return userDetails;
    }


}
