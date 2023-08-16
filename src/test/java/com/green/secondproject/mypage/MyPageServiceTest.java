package com.green.secondproject.mypage;


import static org.junit.jupiter.api.Assertions.*;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.mypage.model.SelUserMyPageDto;
import com.green.secondproject.mypage.model.SelUserMyPageVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({MyPageService.class})
class MyPageServiceTest {

    @MockBean
    private MyPageMapper mapper;
    @Autowired
    private MyPageService service;
    @MockBean
    private AuthenticationFacade FACADE;




    @Test
    void selMyPage() {

        SelUserMyPageVo vo = new SelUserMyPageVo();
        SelUserMyPageDto dto = new SelUserMyPageDto();
        vo.setUserId(FACADE.getLoginUserPk());
        vo.setUnm("아아");
        vo.setEmail("123");
        vo.setRole("TC");
        vo.setPic("asd");
        vo.setBirth(LocalDate.now());
        vo.setPhone("010");
        vo.setAddress("집");
        vo.setDetailAddr("디테일");
        vo.setClassId(1L);
        vo.setGrade("1");
        vo.setVan("2");
        vo.setSchoolId(1L);
        vo.setSchnm("ㅋ");

        when(mapper.selUserMyPage(dto)).thenReturn(vo);
        SelUserMyPageVo result = service.selUserMyPage(MyUserDetails.builder().build());

        assertEquals(vo.getUserId(),result.getUserId());

        verify(mapper).selUserMyPage(dto);
    }
}
