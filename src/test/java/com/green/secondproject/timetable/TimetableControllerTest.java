package com.green.secondproject.timetable;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.timetable.model.TimeTableContainerVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TimetableController.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TimetableControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    protected WebApplicationContext context;

    @MockBean
    private TimetableService service;
    private TestUserDetailsService testUser;

    @BeforeAll
    @DisplayName("init-mvc")
    void init() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print()).build();
        //new PreAuthenticatedAuthenticationToken
    }

    @Test
    @DisplayName("시간표테스트-권한없음")
    @IfProfileValue(name = "spring.profile.active", value = "local")
    void getTimeTableByClassOfTheWeekWithoutAuth() throws Exception {
        //권한 없는 유저
        //emptyContext : when

//        ResultActions actions = mvc.perform(get("/api/timetable").with()
//                .andExpectAll(isNull(), status()
//                        .is4xxClientError(), status()
//                        .isUnauthorized())
//                .andDo(print());


//        verify(service).getTimeTableByClassAndTheWeek(myUserDetails);

        SecurityContextHolder.clearContext();

    }


    @Test
    @DisplayName("시간표테스트-학생권한")
    void getTimeTableByClassOfTheWeekWithAuth() throws Exception {

        //권한없는 유저 test
        TimeTableContainerVo vo = service.getTimeTableByClassAndTheWeek(testUser.getUnAuthUser());
        log.info("vo : {}", vo);

        if (vo != null) {

            ResultActions ra = mvc.perform(get("/api/timetable")
                    .contentType(MediaType.APPLICATION_JSON_UTF8));


            ra.andExpectAll(status().is4xxClientError(),status().is2xxSuccessful())//200을 기대하면..안되나
                    .andDo(print());


        }
        else{
            mvc.perform(get("/api/timetable").contentType(MediaType.APPLICATION_JSON))
                    .andExpectAll(status().isOk())//200을 기대하면..안되나
                    .andDo(print());
        }
    }
}