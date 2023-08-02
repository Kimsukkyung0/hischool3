package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.List;

@Service
@ToString
@RequiredArgsConstructor
public class SubjectSerivce {
    private final SubjectMapper mapper;


    List<SubjectVo> subcate(){
        return mapper.subCate();
    }

    List<SubjectDetailVo> subject(Long categoryid){

        return mapper.subject(categoryid);
    }

    int instcsbj(SubjectInsDto dto){

        return mapper.instcsbj(dto);
    }

    List<SubjectDetailVo2> tcslist(SubjectDetailDto dto){
        return mapper.tcslist(dto);
    }
    List<SubjectVo2> smalllist(Long userid){
        SubjectDto dto = new SubjectDto();
        dto.setUserid(userid);

        return mapper.smalllist(dto);
    }
    int classnum(StudentClassDto dto){
        return mapper.classnum(dto);
    }
    int schoolnum(StudentSchoolDto dto){
        return mapper.schoolnum(dto);
    }
    int acasubject(AcaSubjectDto dto){
        return mapper.acasubject(dto);
    }
    
    //모의고사 시작
    List<MockSubjectBigVo> mockbiglist(){
        return mapper.mockbiglist();
    }
    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid){
        return mapper.mocksmalllist(categoryid);
    }
    int mockins(MockSubjectDto dto){
        return mapper.mockins(dto);
    }
}