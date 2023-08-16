package com.green.secondproject.header;

import com.green.secondproject.CommonUserUtilsForTest;
import com.green.secondproject.config.RedisService;
import com.green.secondproject.header.model.SelSchoolInfoDto;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@Disabled
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
class HeaderControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HeaderService service;

    @MockBean
    private RedisService redisService;


    @BeforeEach
    void beforeEach() {
        CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
        UserDetails user = testUser.getStudentUserDetails();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
    }


    @Test
    @Disabled
    void selectSchoolInfo()  throws Exception {
        SelSchoolInfoVo vo = new SelSchoolInfoVo();
        vo.setSchoolId(1L);
        vo.setUserId(1L);
        vo.setNm("김선생");
        vo.setGrade("1");
        vo.setVan("1");

        given(service.selSchoolInfo(any())).willReturn(vo);

        mvc.perform(get("/api/header/school-info"))
                .andExpect(status().isOk())
                .andDo(print());

        SelSchoolInfoDto dto = new SelSchoolInfoDto();
        verify(service).selSchoolInfo(any());
        SecurityContextHolder.clearContext();
    }

    @Test
    void selectSchoolLogo() throws Exception {
        SelSchoolLogoVo vo = new SelSchoolLogoVo();
        vo.setLogo("오성고.png");

        given(service.selSchoolLogo(any())).willReturn(vo);

        mvc.perform(get("/api/header/school-logo"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).selSchoolLogo(any());
        SecurityContextHolder.clearContext();
    }
}