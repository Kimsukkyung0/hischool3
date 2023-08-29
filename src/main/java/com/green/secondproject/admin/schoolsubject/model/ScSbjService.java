package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final SchoolRepository scRep;


    public List<ScSbjVo> saveAll(List<Long> subjectIdList, int grade) {
        List<ScSbjVo> fkResult = new ArrayList<>();

        if(grade>0 && grade<=3){
            List<ScSbjEntity> sb = new ArrayList<>();
            String strGrade = String.valueOf(grade);
            SchoolEntity scEnti = scAdRep.findById(facade.getLoginUserPk()).get().getSchoolEntity();
//        List<SubjectEntity> sbjEnti = sbjtRep.findAllById(subjectIdList);


        for(Long subjectId : subjectIdList){
            sb.add(ScSbjEntity
                    .builder()
                    .subjectEntity(sbjtRep.getReferenceById(subjectId))
                    .schoolEntity(scEnti)
                    .grade(strGrade)
                    .build());
        }
            sbjRep.saveAll(sb);

            return sb.stream().map(item-> ScSbjVo.builder()
                    .subjectId(item.getSubjectEntity().getSubjectId())
                    .grade(item.getGrade())
                    .scSbjId(item.getSchoolSbjId())
                    .build()).toList();
        }
        else{
            return fkResult;
        }
//        sbjEnti.stream()
//                .map(item -> sb.add(ScSbjEntity
//                        .builder()
//                        .subjectEntity(item)
//                        .schoolEntity(scEnti)
//                        .grade(grade)
//                        .build()));


        }

//    public List<ScSbjEntity> saveAll(List<Long> subjectIdList, Grade grade) {
//        SchoolAdminEntity scAdEnti = scAdRep.findById(facade.getLoginUserPk()).get();
//
//        List<ScSbjEntity> sb = new ArrayList<>();
//
//        for(Long sbjId : subjectIdList){
//            sb.add(new ScSbjEntity().setGrade(grade);)
//        }
//        ScSbjEntity enti = new ScSbjEntity()
//        // providerEntity.getProductEntityList().addAll(Lists.newArrayList(p1,p2,p3));
//        subjectIdList.stream()
//                .map(item -> sb.get(0)
//                        .builder()
//                        .subjectId(item)
//                        .schoolId(scAdEnti.getSchoolEntity().getSchoolId())
//                        .grade(grade)
//                        .build()));
//
//        return sbjRep.saveAll(sb);
//
//        //(1)return type switch to list subject entity or subjectvoList
//    }
    }

