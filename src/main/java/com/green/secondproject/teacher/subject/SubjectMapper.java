package com.green.secondproject.teacher.subject;
import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import com.green.secondproject.teacher.subject.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface SubjectMapper {
//    List<SubjectVo> subCate();

    List<SubjectDetailVo> subject(Long categoryid);

    int instcsbj(List<SubjectInsVo> list);

    int classnum(StudentClassDto dto);

    int schoolnum(StudentSchoolDto dto);


    //모의고사 시작
    List<MockSubjectBigVo> mockbiglist();

    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid);
    List<StudentListVo> stulist(StudentListDto dto);

    List<SubjectVo> tcslist(String grade, Long schoolId);

    List<SubjectVo3> smallList(Long categoryId, String grade, Long schoolId);








}
