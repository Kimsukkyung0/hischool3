//package com.green.secondproject.header;
//
//import com.green.secondproject.common.config.security.AuthenticationFacade;
//import com.green.secondproject.common.config.security.model.MyUserDetails;
//import com.green.secondproject.header.model.SelSchoolInfoDto;
//import com.green.secondproject.header.model.SelSchoolInfoVo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@Import({HeaderService.class})
//class HeaderServiceTest {
//
//    @MockBean
//    private HeaderMapper mapper;
//    @Autowired
//    private HeaderService service;
//    @MockBean
//    private AuthenticationFacade FACADE;
//
//
//    @Test
//    void selSchoolInfo() {
//        SelSchoolInfoDto dto = new SelSchoolInfoDto();
//        SelSchoolInfoVo vo = new SelSchoolInfoVo();
//        vo.setUserId(FACADE.getLoginUserPk());
//        vo.setSchoolId(1L);
//        vo.setGrade("1");
//        vo.setVan("1");
//        vo.setNm("오성고등학교");
//
//        when(mapper.selSchoolInfo(dto)).thenReturn(vo);
//        SelSchoolInfoVo result = service.selSchoolInfo(MyUserDetails.builder().build());
//
//
//        assertEquals(vo.getUserId(), result.getUserId());
//        assertEquals(vo.getSchoolId(), result.getSchoolId());
//
//        verify(mapper).selSchoolInfo(dto);
//    }
//
//    @Test
//    void selSchoolLogo() {
//    }
//}