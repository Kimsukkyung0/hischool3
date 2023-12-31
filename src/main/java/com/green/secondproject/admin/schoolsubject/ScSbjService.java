package com.green.secondproject.admin.schoolsubject;

import com.green.secondproject.admin.schoolsubject.model.*;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.*;
import java.util.stream.Collectors;

@Service
@ToString
@RequiredArgsConstructor
@Slf4j
@DynamicUpdate
public class ScSbjService {
    private final ScSbjRepository sbjRep;
    private final AuthenticationFacade facade;
    private final UserRepository usrRep;
    private final SchoolAdminRepository scAdRep;
    private final SubjectRepository sbjtRep;
    private final SbjCategoryRepository cateRep;
    private final SchoolRepository scRep;
    private final EntityManager em;

    public List<ScSbjListVo> saveAll(ScSbjListDto list, int grade) {
        List<ScSbjListVo> fkResult = new ArrayList<>();

        if (grade > 0 && grade <= 3) {
            List<ScSbjEntity> sb = new ArrayList<>();
            SchoolEntity scEnti = scAdRep.findById(facade.getLoginUserPk()).get().getSchoolEntity();
//        List<SubjectEntity> sbjEnti = sbjtRep.findAllById(subjectIdList);

            for (SbjDto subjectId : list.getList()) {
                sb.add(ScSbjEntity
                        .builder()
                        .subjectEntity(sbjtRep.getReferenceById(subjectId.getSubjectId()))
                        .schoolEntity(scEnti)
                        .grade(String.valueOf(grade))
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

    public List<ScSbjGradeVo> adminSbjList(int grade) {
        List<ScSbjGradeVo> result = new ArrayList<>();

        if (grade > 0 && grade <= 3) {
            List<ScSbjEntity> sbjEnti = sbjRep.findAllBySchoolEntityAndGrade(scAdRep.findById(facade.getLoginUserPk())
                    .get().getSchoolEntity(), String.valueOf(grade));

            result = sbjEnti.stream().map(item -> ScSbjGradeVo.builder()
//                    .categoryId(item.getSubjectEntity().getSbjCategoryEntity().getCategoryId())
//                    .categoryNm(item.getSubjectEntity().getSbjCategoryEntity().getNm())
                    .subjectId(item.getSubjectEntity().getSubjectId())
                    .subjectNm(item.getSubjectEntity().getNm())
                    .categoryId(item.getSubjectEntity().getSbjCategoryEntity().getCategoryId())
                    .categoryNm(item.getSubjectEntity().getSbjCategoryEntity().getNm())
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

        Optional<ScSbjEntity> optEntity = sbjRep.findById(scSbjId);

        if (optEntity.isPresent()) {
            sbjRep.deleteById(scSbjId);
            return 1;
        } else {
            return 0;
        }
    }

    public List<ScCateVo> getCateList() {
        List<SbjCategoryEntity> categoryEntities = cateRep.findAllByTypeIsOrderByNm(1);
        return categoryEntities.stream().map(item -> ScCateVo.builder()
                .categoryNm(item.getNm())
                .categoryId(item.getCategoryId()).build()).toList();
    }

    public List<ScSbjVo> getSubjectListByCate2(Long categoryId) {
        SbjCategoryEntity cateEnti = cateRep.findById(categoryId).get();
        List<SubjectEntity> sbjEntityList = sbjtRep.findBySbjCategoryEntityOrderByNm(cateEnti);

        return sbjEntityList.stream().map(item -> ScSbjVo.builder()
                .subjectId(item.getSubjectId())
                .subjectNm(item.getNm()).build()).toList();
//    }
//            SbjCategoryEntity cateEnti = cateRep.findById(categoryId).get();
//
//            MyUserDetails userDetails = facade.getLoginUser();
//            Long loggedInSchoolId = userDetails.getSchoolId();
//
//            List<SubjectEntity> sbjEntityList = sbjtRep.findSubjectsNotInScSbjByCategoryAndSchoolId(cateEnti, loggedInSchoolId);
//
//            return sbjEntityList.stream().map(item -> ScSbjVo.builder()
//                    .subjectId(item.getSubjectId())
//                    .subjectNm(item.getNm()).build()).toList();
    }


public Map<Integer, List<ScSbjVo>> getSubjectListByCateAndGrade(Long categoryId) {
    SbjCategoryEntity cateEnti = cateRep.findById(categoryId).get();
    MyUserDetails userDetails = facade.getLoginUser();
    Long loggedInSchoolId = userDetails.getSchoolId();

    Map<Integer, List<ScSbjVo>> resultMap = new HashMap<>();

    for (int grade = 1; grade <= 3; grade++) {
        List<SubjectEntity> sbjEntityList = sbjtRep.findSubjectsNotInScSbjByCategoryAndSchoolIdAndGrade(cateEnti, loggedInSchoolId, String.valueOf(grade));
        List<ScSbjVo> voList = sbjEntityList.stream().map(item -> ScSbjVo.builder()
                .subjectId(item.getSubjectId())
                .subjectNm(item.getNm())
                .build()).collect(Collectors.toList());

        resultMap.put(grade, voList);
    }

    return resultMap;
}
    public List<ScSbjListVo2> updSubjectsBySchoolAndGrade(List<SbjDto> dto, int grade) {
        List<ScSbjEntity> midResult = new ArrayList<>();
        List<ScSbjListVo2> finResult = new ArrayList<>();
        List<Long> sbjIdList = new ArrayList<>();

        if (grade > 0 && grade <= 3) {

            SchoolEntity scEnti = scRep.findBySchoolId(facade.getLoginUser().getSchoolId());

            //요청받은 리스트->Long List 로 변환
            for (int i = 0; i < dto.size(); i++) {
                sbjIdList.add(dto.get(i).getSubjectId());
            }

            //기존에 등록되어 있는 리스트를 Long List로
            List<ScSbjEntity> preListEnti = sbjRep.findAllBySchoolEntityAndGrade(scEnti,String.valueOf(grade));
            List<Long> preListSubjectId = sbjRep.findAllSubjectIdBySchoolEntityAndGrade(scEnti,String.valueOf(grade));

            //case 1 : 1차로 기존등록리스트에서, newlist를 대비해 존재하는 아이디찾기
            List<Long> excludedIdList = preListSubjectId.stream().filter(item -> !sbjIdList.contains(item)).toList();
            for(Long ex: excludedIdList){
                log.info("ex : {}",ex);
            }
            List<SubjectEntity> targets4Del = sbjtRep.findAllBySubjectIdList(excludedIdList);
            for(SubjectEntity tg: targets4Del){
                log.info("tg : {}",tg);
            }
            int delResult = sbjRep.deleteAllBySubjectEntity(targets4Del);
            log.info("delResult : {}",delResult);


            //case 2 : 새로운 부분 찾아내기 -요청받은 리스트에서 새로운 부분 찾기
            List<Long> newSbjList = sbjIdList.stream().filter(item -> !preListSubjectId.contains(item)).toList();
            for(Long n: newSbjList){
                log.info("new : {}",n);
            }
//
//            midResult = sbjRep.saveAll(newSbjList.stream().map(item->ScSbjEntity.builder().schoolEntity(scEnti).grade(String.valueOf(grade))
//                    .subjectEntity(SubjectEntity.builder().subjectId(item).build()).build()).toList());
//            for(ScSbjEntity sb : midResult){
//                log.info("sb : {}",sb);
//            }
//            finResult = midResult.stream()
//                    .map(item -> ScSbjListVo2.builder()
//                            .scSbjId(item.getSchoolSbjId())
//                            .subjectId(item.getSubjectEntity().getSubjectId())
//                            .subjectNm(item.getSubjectEntity().getNm())
////                            .categoryNm(item.getSubjectEntity().getSbjCategoryEntity().getNm())
//                            .build())
//                    .collect(Collectors.toList());
//            for(ScSbjListVo2 sb : finResult){
//                log.info("vo : {}",sb);
//            }
//            return finResult;

            midResult = sbjRep.saveAll(newSbjList.stream().map(item->ScSbjEntity.builder().schoolEntity(scEnti).grade(String.valueOf(grade))
                    .subjectEntity(SubjectEntity.builder().subjectId(item).build()).build()).toList());
            for(ScSbjEntity sb : midResult){
                log.info("sb : {}",sb);
            }
            finResult = preListEnti.stream()
                    .map(item -> ScSbjListVo2.builder()
                            .scSbjId(item.getSchoolSbjId())
                            .subjectId(item.getSubjectEntity().getSubjectId())
                            .subjectNm(item.getSubjectEntity().getNm())
                            .categoryId(item.getSubjectEntity().getSbjCategoryEntity().getCategoryId())
                            .categoryNm(item.getSubjectEntity().getSbjCategoryEntity().getNm())
                            .build())
                    .collect(Collectors.toList());
            return finResult;
//다 들어오는게 아니라 , 수정된 애들만 들어오게되어있음.
        } else {
            throw new RuntimeException("올바른 요청 값이 아닙니다");
        }
    }
}

