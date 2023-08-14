package com.green.secondproject.timetable;

import com.green.secondproject.config.RedisService;
import com.green.secondproject.config.security.JwtAuthenticationFilter;
import com.green.secondproject.config.security.JwtTokenProvider;
import com.green.secondproject.config.security.SecurityConfiguration;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.timetable.model.TimeTableContainerVo;
import com.green.secondproject.timetable.model.TimeTableGetDto;
import com.green.secondproject.timetable.model.TimeTableVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

//@AutoConfigureMockMvc // 아래 init 이랑 중복
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)


@WebMvcTest(TimetableController.class)
//        excludeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
//)
@WebAppConfiguration
@Disabled
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
class TimetableControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TimetableService service;


    @BeforeEach
    @DisplayName("create mockUserDetails for Test")
    void beforeEach() {
        UserDetails user = createUserDetails();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
    }

    private UserDetails createUserDetails() throws UsernameNotFoundException {
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

    @Test
    @DisplayName("시간표테스트")
    @Disabled
//    @IfProfileValue(name = "springs.profile.active", value = "local")
    void getTimeTableByClassOfTheWeekWithAuth() throws Exception {
        //given
        List<TimeTableVo> list = new ArrayList<>();

        TimeTableContainerVo subResult = new TimeTableContainerVo("오성고등학교","1","1","1",list);
        TimeTableGetDto dto = new TimeTableGetDto();
        dto.setClassNum("1");
        dto.setGrade("1");
        dto.setSchoolNm("오성고등학교");
        given(service.getTimeTableByClassAndTheWeek(any())).willReturn(subResult);
        //when
        mvc.perform(get("/api/timetable"))
                .andExpect(status().isOk())
                .andDo(print());

        TimeTableGetDto dto2 = new TimeTableGetDto();
        verify(service).getTimeTableByClassAndTheWeek(dto);
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("시간표테스트-익명유저")
    @Disabled
    void getTimeTableByClassOfTheWeekWithoutAuth() throws Exception {
    }
}


