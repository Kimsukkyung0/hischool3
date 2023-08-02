package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface SubjectMapper {
    List<SubjectVo> subCate();

    List<SubjectDetailVo> subject();

    int insTcsbj(SubjectInsDto dto);

    List<SubjectDetailVo2> tcslist(SubjectDetailDto dto);

    List<SubjectVo2> smalllist(SubjectDto dto
    );
    int classnum(StudentClassDto dto);

    int schoolnum(StudentSchoolDto dto);

    int acasubject(AcaSubjectDto dto);

    //모의고사 시작
    List<MockSubjectBigVo> mockbiglist();

    List<MockSubjcetSmallVo> mocksmalllist();

    int mockins(MockSubjectDto dto);
}
