package com.green.secondproject.subject;

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
}
