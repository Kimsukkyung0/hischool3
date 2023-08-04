package com.green.secondproject.teacher.subject;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

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
        return mapper.instcsbj(dto.getList());
    }

    List<SubjectDetailVo2> tcslist(@AuthenticationPrincipal MyUserDetails user){
        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUserid(user.getUserId());
        return mapper.tcslist(dto);
    }
    List<SubjectVo2> smalllist(@AuthenticationPrincipal MyUserDetails user){
        SubjectDto dto = new SubjectDto();
        dto.setUserid(user.getUserId());
        return mapper.smalllist(dto);
    }
    int classnum(@AuthenticationPrincipal MyUserDetails user){
        StudentClassDto dto = new StudentClassDto();
        dto.setClassid(user.getClassNum());
        dto.setSchoolid(user.getUserId());
        return mapper.classnum(dto);
    }
    int schoolnum(@AuthenticationPrincipal MyUserDetails user){
        StudentSchoolDto dto = new StudentSchoolDto();
        dto.setGrade(user.getGrade());
        dto.setSchoolNm(user.getSchoolNm());
        return mapper.schoolnum(dto);
    }
    int acasubject(AcalistDto dto){
        return mapper.acasubject(dto.getList());
    }
    
    //모의고사 시작
    List<MockSubjectBigVo> mockbiglist(){
        return mapper.mockbiglist();
    }

    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid){
        return mapper.mocksmalllist(categoryid);
    }

    int mockins(mockDto dto){
        return mapper.mockins(dto.getList());
    }
}