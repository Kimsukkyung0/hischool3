package com.green.secondproject.admin.schoolsubject;

import com.green.secondproject.admin.schoolsubject.model.ScCateVo;
import com.green.secondproject.admin.schoolsubject.model.ScSbjListVo;
import com.green.secondproject.admin.schoolsubject.model.ScSbjListVo2;
import com.green.secondproject.admin.schoolsubject.model.ScSbjVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import com.green.secondproject.teacher.subject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<ScSbjListVo2> adminSbjList(int grade) {
        //schoolEntity 를 가져와서 학교별 저장된 목록을 가져온다
//        List<ScSbjEntity> sbjEnti  = sbjRep.findAllBySchoolEntity(usrRep.findByUserId(facade.getLoginUserPk())
//                .getVanEntity()
//                .getSchoolEntity());
//   (2)     List<ScSbjEntity> sbjEnti  = sbjRep.findAllBySchoolEntityAndGrade(
//                usrRep.findByUserId(facade.getLoginUserPk())
//                        .getVanEntity()
//                        .getSchoolEntity(),String.valueOf(grade));

        List<ScSbjListVo2> result = new ArrayList<>();

        if(grade>0 && grade<=3) {
            List<ScSbjEntity> sbjEnti = sbjRep.findDistinctBySchoolEntityAndGrade(
                    usrRep.findByUserId(facade.getLoginUserPk())
                            .getVanEntity()
                            .getSchoolEntity(), String.valueOf(grade));

            result = sbjEnti.stream().map(item -> ScSbjListVo2.builder()
                    .categoryId(item.getSubjectEntity().getSbjCategoryEntity().getCategoryId())
                    .categoryNm(item.getSubjectEntity().getSbjCategoryEntity().getNm())
                    .subjectId(item.getSubjectEntity().getSubjectId())
                    .subjectNm(item.getSubjectEntity().getNm())
                    .scSbjId(item.getSchoolSbjId())
                    .build()).toList();
            return result;
        }

        return result;
    }


//    public List<ScSbjVo> smalllist(Long categoryId) {
////        SbjCategoryEntity sbjCategoryEntity = sbjRep.findBy(categoryId);
//        List<SubjectEntity> subjectEntityList = sbjtRep.fin
//                findAllBySbjCategoryEntity(categoryId);
//
//        return subjectEntityList.stream().map(item -> ScSbjVo.builder()
//                .id(item.getSubjectId())
//                .nm(item.getNm()).build()).toList();
//    }

    public int delete(Long scSbjId) {

        Optional<ScSbjEntity> optEntity =  sbjRep.findById(scSbjId);

        if (optEntity.isPresent()){
            sbjRep.deleteById(scSbjId);
            return 1;
        }
        else{
            return 0;
        }
    }

    public List<ScCateVo> getCateList() {
        List<SbjCategoryEntity> categoryEntities =  cateRep.findAllByTypeIsOrderByNm(1);
        return categoryEntities.stream().map(item -> ScCateVo.builder()
                .categoryNm(item.getNm())
                .categoryId(item.getCategoryId()).build()).toList();
    }

    public List<ScSbjVo> getSubjectListByCate(Long categoryId){
        SbjCategoryEntity cateEnti = cateRep.findById(categoryId).get();
        List<SubjectEntity> sbjEntityList = sbjtRep.findBySbjCategoryEntityOrderByNm(cateEnti);

        return sbjEntityList.stream().map(item -> ScSbjVo.builder()
                .subjectId(item.getSubjectId())
                .subjectNm(item.getNm()).build()).toList();
    }
}

