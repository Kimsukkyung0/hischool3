package com.green.secondproject.teacher.subject;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;


@Service
@ToString
@RequiredArgsConstructor
public class SubjectSerivce {
    private final SubjectMapper mapper;
    private final AuthenticationFacade facade;


    List<SubjectVo> subcate() {
        return mapper.subCate();
    }

    List<SubjectDetailVo> subject(Long categoryid) {

        return mapper.subject(categoryid);
    }

    List<SubjectDetailVo2> tcslist(@AuthenticationPrincipal MyUserDetails user) {
        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUserid(user.getUserId());
        return mapper.tcslist(dto);
    }

    List<SubjectVo2> smalllist(@AuthenticationPrincipal MyUserDetails user) {
        SubjectDto dto = new SubjectDto();
        dto.setUserid(user.getUserId());
        return mapper.smalllist(dto);
    }

    int classnum(@AuthenticationPrincipal MyUserDetails user) {
        StudentClassDto dto = new StudentClassDto();
        dto.setClassid(user.getClassNum());
        dto.setSchoolid(user.getUserId());
        return mapper.classnum(dto);
    }

    int schoolnum(@AuthenticationPrincipal MyUserDetails user) {
        StudentSchoolDto dto = new StudentSchoolDto();
        dto.setGrade(user.getGrade());
        dto.setSchoolNm(user.getSchoolNm());
        return mapper.schoolnum(dto);
    }

    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid) {
        return mapper.mocksmalllist(categoryid);
    }

    List<MockSubjectBigVo> mockbiglist() {
        return mapper.mockbiglist();
    }


    //Post(insert)

    int mockins(mockDto2 dto) {
        List<MockSubjectVo> list = new ArrayList<>();

        for (int i = 0; i < dto.getList().size(); i++) {
            MockSubjectVo vo = new MockSubjectVo();
            vo.setRating(dto.getList().get(i).getRating());
            vo.setMon(dto.getList().get(i).getMon());
            vo.setStandardscore(dto.getList().get(i).getStandardscore());
            vo.setSubjectid(dto.getList().get(i).getSubjectid());
            vo.setPercent(dto.getList().get(i).getPercent());
            vo.setUserid(facade.getLoginUserPk());
            list.add(vo);
        }
        return mapper.mockins(list);
    }

    int instcsbj(SubjectInsDto dto) {

        return mapper.instcsbj(dto.getList());
    }

    int acasubject(AcalistDto dto) {
        return mapper.acasubject(dto.getList());
    }

}

