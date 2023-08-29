package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import com.green.secondproject.teacher.subject.model.SubjectDetailDto;
import com.green.secondproject.teacher.subject.model.SubjectDetailVo2;
import com.green.secondproject.teacher.subject.model.SubjectDto;
import com.green.secondproject.teacher.subject.model.SubjectVo2;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@ToString
@RequiredArgsConstructor
@Slf4j
public class ScSbjService {
    private final ScSbjRepository sbjRep;
    private final AuthenticationFacade facade;
    private final UserRepository usrRep;
    private final SchoolAdminRepository scAdRep;
    private final SubjectRepository sbjtRep;
    private final SbjCategoryRepository cateRep;
    private final SchoolRepository scRep;

    public List<ScSbjListVo> saveAll(List<Long> subjectIdList, int grade) {
        List<ScSbjListVo> fkResult = new ArrayList<>();

        if (grade > 0 && grade <= 3) {
            List<ScSbjEntity> sb = new ArrayList<>();
            String strGrade = String.valueOf(grade);
            SchoolEntity scEnti = scAdRep.findById(facade.getLoginUserPk()).get().getSchoolEntity();
//        List<SubjectEntity> sbjEnti = sbjtRep.findAllById(subjectIdList);

            for (Long subjectId : subjectIdList) {
                sb.add(ScSbjEntity
                        .builder()
                        .subjectEntity(sbjtRep.getReferenceById(subjectId))
                        .schoolEntity(scEnti)
                        .grade(strGrade)
                        .build());
            }
            sbjRep.saveAll(sb);

            return sb.stream().map(item -> ScSbjListVo.builder()
                    .subjectId(item.getSubjectEntity().getSubjectId())
                    .grade(item.getGrade())
                    .scSbjId(item.getSchoolSbjId())
                    .build()).toList();
        } else {
            return fkResult;
        }
    }
    public List<ScSbjVo> tcslist() {
        //schoolEntity 를 가져와서 학교별 저장된 목록을 가져온다
        List<ScSbjEntity> sbjEnti  = sbjRep.findAllBySchoolEntity(usrRep.findByUserId(facade.getLoginUserPk())
                .getVanEntity()
                .getSchoolEntity());

        return sbjEnti.stream().map(item -> ScSbjVo.builder()
                .id(item.getSubjectEntity().getSbjCategoryEntity().getCategoryId())
                .nm(item.getSubjectEntity().getSbjCategoryEntity().getNm()).build()).toList();
    }

//    public List<ScSbjVo> smalllist(Long categoryId) {
////        SbjCategoryEntity sbjCategoryEntity = sbjRep.findBy(categoryId);
//        List<SubjectEntity> subjectEntityList = sbjtRep.findAllBySbjCategoryEntity(categoryId);
//
//        return subjectEntityList.stream().map(item -> ScSbjVo.builder()
//                .id(item.getSubjectId())
//                .nm(item.getNm()).build()).toList();
//    }


}

