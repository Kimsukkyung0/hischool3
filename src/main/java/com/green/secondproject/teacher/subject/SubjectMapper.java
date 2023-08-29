package com.green.secondproject.teacher.subject;
import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import com.green.secondproject.teacher.subject.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface SubjectMapper {
    List<SubjectVo> subCate();

    List<SubjectDetailVo> subject(Long categoryid);

    List<SubjectDetailVo2> tcslist(SubjectDetailDto dto);

    int instcsbj(List<SubjectInsVo> list);



    List<SubjectVo2> smalllist(SubjectDto dto);

    int classnum(StudentClassDto dto);

    int schoolnum(StudentSchoolDto dto);

    int acasubject(List<AcaSubjectVo> list);

    //모의고사 시작
    List<MockSubjectBigVo> mockbiglist();

    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid);

    int mockins(List<MockSubjectVo> list);

    List<MockGraphVo2> mockgraph(MockGraphDto dto);

    List<StudentListVo> stulist(StudentListDto dto);

    Integer mocktotal(Long vanId); // 모의고사 퍼센트 계산용

    List<ResultAcaVo> selaca(ResultAcaDto dto);

    List<ResultMockVo> selmock(ResultMockDto dto);

}
