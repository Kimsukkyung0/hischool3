package com.green.secondproject.subject;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.teacher.subject.SubjectMapper;
import com.green.secondproject.teacher.subject.SubjectService;
import com.green.secondproject.teacher.subject.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({SubjectService.class})
public class SubjectServiceTest {

    @MockBean
    private SubjectMapper mapper;
    @MockBean
    private AuthenticationFacade FACADE;
    @Autowired
    private SubjectService service;

    @Test
    @DisplayName("SubjectServiceTest - subcate()과목계열")
    void subCate(){
        List<SubjectVo> list = new ArrayList<>();
        SubjectVo vo = new SubjectVo();
        vo.setCategoryid(1L);
        vo.setNm("국어");
        list.add(vo);
        when(mapper.subCate()).thenReturn(list);
        List<SubjectVo> list1 = service.subcate();

        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
        assertEquals(list.get(0).getCategoryid(),list1.get(0).getCategoryid());

        verify(mapper).subCate();
    }
    @Test
    @DisplayName("SubjectServiceTest - subject() 세부과목")
    void subject(){
        List<SubjectDetailVo> list = new ArrayList<>();
        SubjectDetailVo vo = new SubjectDetailVo();
        vo.setSubjectid(1L);
        vo.setNm("화법과 언어");
        vo.setCategoryid(1L);
        list.add(vo);
        when(mapper.subject(1L)).thenReturn(list);
        List<SubjectDetailVo> list1 = service.subject(1L);

        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
        assertEquals(list.get(0).getSubjectid(),list1.get(0).getSubjectid());
        assertEquals(list.get(0).getCategoryid(),list1.get(0).getCategoryid());
        verify(mapper).subject(1L);
    }
    @Test
    @DisplayName("SubjectDetailTest2 - tcslist() 등록후 과목계열list")
    void tcslist() {
        List<SubjectDetailVo2> list = new ArrayList<>();
        SubjectDetailVo2 vo2 = new SubjectDetailVo2();
        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUserid(FACADE.getLoginUserPk());
        vo2.setNm("국어");
        vo2.setSubjectid(1L);
        vo2.setUserid(FACADE.getLoginUserPk());
        list.add(vo2);
        when(mapper.tcslist(dto)).thenReturn(list);
//        List<SubjectDetailVo2> list1 =  service.;
//
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//        assertEquals(list.get(0).getUserid(),list1.get(0).getUserid());
//        assertEquals(list.get(0).getSubjectid(),list1.get(0).getSubjectid());

    }
}
