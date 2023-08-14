package com.green.secondproject.teacher;

import com.green.secondproject.student.StudentMapper;
import com.green.secondproject.student.StudentService;
import com.green.secondproject.teacher.model.TeacherGraphVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({TeacherMapper.class,TeacherService.class})
@ContextConfiguration(classes = {TeacherMapper.class,TeacherService.class})
class TeacherServiceTest {
    @MockBean
    TeacherMapper mapper;
    @MockBean
    TeacherService service;


    @Test
    void teacherAcaGraph() {
        //필요값 set
        Long classId = 1L;
        Long[] cateIdForAca = {1L,3L,6L,7L};
        String[] cateNm = {"국어","수학","영어","한국사"};
        int RATING_NUM = 9;
        double vanMemNum = (double) 20;

        List<List<TeacherGraphVo>> subResult = new ArrayList<>();

        List<List<TeacherGraphVo>> tmpSubResult = new ArrayList<>();
        List<TeacherGraphVo> tmpSubList = new ArrayList<>();
        for (int i = 0; i < cateIdForAca.length; i++) {
            tmpSubList.add(TeacherGraphVo.builder().cateNm(cateNm[i]).rating(i+1).percentage(i*10).build());
            when(mapper.teacherAcaGraph(classId,cateIdForAca[i])).thenReturn(tmpSubList);
        }
        assertEquals(cateNm[0],tmpSubList.get(0).getCateNm());
        assertEquals(4,tmpSubList.size());
        service.teacherAcaGraph(classId);

    }
}