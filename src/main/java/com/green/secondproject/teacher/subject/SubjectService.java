package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.SbjCategoryEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.common.repository.SubjectRepository;
import com.green.secondproject.teacher.subject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
@ToString
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectMapper mapper;
    private final AuthenticationFacade facade;
    private final SubjectRepository subjectRepository;


   public List<SubjectVo> subcate() {
        return mapper.subCate();
    }

    public List<SubjectDetailVo> subject(Long categoryid) {

        return mapper.subject(categoryid);
    }


    public List<SubjectVo2> smalllist(SubjectDto dto) {
        return mapper.smalllist(dto);
    }

    public int classnum(StudentClassDto dto) {
        return mapper.classnum(dto);
    }

    public int schoolnum(@AuthenticationPrincipal MyUserDetails user) {
        StudentSchoolDto dto = new StudentSchoolDto();
        dto.setGrade(user.getGrade());
        dto.setSchoolNm(user.getSchoolNm());
        return mapper.schoolnum(dto);
    }

public List<MockSubjcetSmallVo> mocksmalllist(Long categoryid) {
    SbjCategoryEntity sbjCategoryEntity= SbjCategoryEntity.builder()
            .categoryId(categoryid)
            .build();

       List<SubjectEntity> subjectList = subjectRepository.findBySbjCategoryEntity(sbjCategoryEntity);
       List<MockSubjcetSmallVo> list = new ArrayList<>();

    for (SubjectEntity subjectEntity : subjectList) {
        list.add(MockSubjcetSmallVo.builder()
                .categoryid(subjectEntity.getSbjCategoryEntity().getCategoryId())
                .subjectid(subjectEntity.getSubjectId())
                .nm(subjectEntity.getNm())
                .build());
    }
    return list;
//
//        return subjectList.stream().map(subjectEntity -> MockSubjcetSmallVo.builder()
//                .categoryid(subjectEntity.getSbjCategoryEntity().getCategoryId())
//                .subjectid(subjectEntity.getSubjectId())
//                .nm(subjectEntity.getNm())
//                .build()).toList();
    }

    public List<MockSubjectBigVo> mockbiglist() {
        return mapper.mockbiglist();
    }


    //Post(insert)



    public int instcsbj(SubjectInsDto2 dto) {
        List<SubjectInsVo> list = new LinkedList<>();

        for (int i = 0; i < dto.getList().size(); i++) {
            SubjectInsVo vo = new SubjectInsVo();
            vo.setSubjectid(dto.getList().get(i).getSubjectId());
            vo.setUserid(facade.getLoginUserPk());
            list.add(vo);
        }

        return mapper.instcsbj(list);
    }

    public List<StudentListVo> stulist(StudentListDto dto){
       return mapper.stulist(dto);
    };

   //===================================================================================
   public List<SubjectVo> tcslist(int grade) {

       return mapper.tcslist(grade,facade.getLoginUser().getSchoolId());

   }
}

