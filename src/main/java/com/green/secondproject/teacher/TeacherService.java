package com.green.secondproject.teacher;

import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.student.StudentService;
import com.green.secondproject.student.model.*;
import com.green.secondproject.teacher.model.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherMapper mapper;
    private final StudentService stService;
    private final UserRepository userRepository;

    public List<SelSignedStudentVo> selSignedStudent(MyUserDetails myuser) {
        List<UserEntity> stdList = userRepository.findAllByVanEntityAndAprYnAndEnrollStateAndRoleType(
                VanEntity.builder()
                .vanId(myuser.getVanId())
                .build(), 1, EnrollState.ENROLL, RoleType.STD);

        return stdList.stream().map(userEntity -> SelSignedStudentVo.builder()
                .userId(userEntity.getUserId())
                .classId(userEntity.getVanEntity().getVanId())
                .aprYn(userEntity.getAprYn())
                .snm(userEntity.getNm())
                .birth(userEntity.getBirth())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .build()).toList();
    }


    public List<SelUnsignedStudentVo> selUnsignedStudent(MyUserDetails myuser) {
        SelUnsignedStudentDto dto = new SelUnsignedStudentDto();
        dto.setClassId(myuser.getVanId());
        return mapper.selUnsignedStudent(dto);
    }


    public List<SelAcaResultVo> selAcaResult(SelAcaResultDto dto) {
        return mapper.selAcaResult(dto);
    }


    public List<SelMockResultVo> selMockResult(SelMockResultDto dto) {
        return mapper.selMockResult(dto);
    }


    public int acceptStudent(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("해당하는 학생이 없습니다.");
        }

        UserEntity userEntity = user.get();
        userEntity.setAprYn(1);
        userRepository.save(userEntity);
        return 1;
    }


//    public int rejectStudent(Long userId) {
//        AcceptStudentDto dto = new AcceptStudentDto();
//        dto.setUserId(userId);
//        return mapper.rejectStudent(dto);
//    }

    public int cancelAcceptStd(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("해당하는 학생이 없습니다.");
        }

        UserEntity userEntity = user.get();
        userEntity.setAprYn(0);
        userRepository.save(userEntity);
        return 1;
    }


    public int updMockResult(UpdMockResultDto dto) {
        dto.setResultId(dto.getResultId());
        return mapper.updMockResult(dto);
    }

    public int updAcaResult(UpdAcaResultDto dto) {
        dto.setResultId(dto.getResultId());
        return mapper.updAcaResult(dto);
    }


    public int delMockRusult(Long resultId) {
        DelResultDto dto = new DelResultDto();
        dto.setResultId(resultId);
        return mapper.delMockResult(dto);
    }


    public int delAcaRusult(Long resultId) {
        DelResultDto dto = new DelResultDto();
        dto.setResultId(resultId);
        return mapper.delAcaResult(dto);
    }


    public int classStudent(MyUserDetails myuser){
        return userRepository.findAllByVanEntityAndAprYnAndEnrollStateAndRoleType(
                VanEntity.builder()
                        .vanId(myuser.getVanId())
                        .build(), 1, EnrollState.ENROLL, RoleType.STD).size();
    }

    public int aprStudent(MyUserDetails myuser){
        return userRepository.findAllByVanEntityAndAprYnAndEnrollStateAndRoleType(
                VanEntity.builder()
                        .vanId(myuser.getVanId())
                        .build(), 0, EnrollState.ENROLL, RoleType.STD).size();
    }

    public TeacherGraphContainerVo teacherAcaGraph(Long classId) {
        MyGradeGraphUtils mg = new MyGradeGraphUtils();
        Long[] cateIdForAca = mg.getCateIdForAca();//1367
        String[] cateNm = MyGradeGraphUtils.cateNm;//국수영한
        int RATING_NUM = mg.RATING_NUM;
        List<List<TeacherGraphVo>> subResult = MyGradeGraphUtils.teacherGraphListAtb();
        //과목 4 * 등급 9  (0%가 들어있는 리스트)

        //과목을 넣기위한 임시거처..?
        List<List<TeacherGraphVo>> tmpSubResult = new ArrayList<>();
        List<TeacherGraphVo> tmpSubList = new ArrayList<>();

        for (int i = 0; i < cateNm.length; i++) { //4번 돌것이다.
            //국수영한 4개 * 9개의 리스트 받아오기
            //일단 국 9 수 9 영 9 한 9
            List<TeacherGraphVo> subSubList = subResult.get(i);
//            Long tmpCateIdForAca = cateIdForAca[i];
            Long tmpCateIdForAca = cateIdForAca[i];
            double vanMemNum = mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().
                    categoryId(tmpCateIdForAca)
                    .classId(classId)
                    .build());
            log.info("vanMemNum : {}", vanMemNum);

            //mapperList -  percentage = 인원수
            List<TeacherGraphVo> tmpVoList = mapper.teacherAcaGraph(classId,tmpCateIdForAca);//1번
            log.info("tmpVoList : {}", tmpVoList);
            //과목 id
            log.info("cateIdForAca[i] : {}", cateIdForAca[i]);

            for (int j = 0; j < RATING_NUM; j++) {
                //0리스트 과목명/등급/명수만 갖고있는 리스트 9개로 분해
                TeacherGraphVo subSubSubList = subSubList.get(j);

                for (TeacherGraphVo voComp : tmpVoList){
                    if(subSubSubList.getRating() == voComp.getRating()){
                        subSubSubList.setPercentage(getPercentage(voComp.getPercentage()
                                ,vanMemNum));
                    }
                    tmpSubList.add(subSubSubList);
                }
                //mapper로부터 온 리스트 9개로 분해하려고 했지만 9보다 작을수도있음
                tmpSubResult.add(tmpSubList);
            }
        }



//0점 없이 작동하는 애들
//        for (int i = 0; i < cateIdForAca.length; i++) {
//            List<TeacherGraphVo> tmpVo = mapper.teacherAcaGraph(classId,cateIdForAca[i]);
//            subResult.add(tmpVo);
//        }
//        for (int i = 0; i < cateIdForAca.length; i++) {
//            subResult.add(getSubResult(subResult.get(i), mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(cateIdForAca[i]).classId(classId).build())));
//        }
//
//안되는애들 하다 포기한 애들
//        for (int i = 0; i < cateNm.length; i++) {
//            List<TeacherGraphVo> tmpVo = mapper.teacherAcaGraph(classId,cateIdForAca[i]);
//            subResult.add(tmpVo);
//            getSubResult(subResult.get(i), mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(cateIdForAca[i]).classId(classId).build()));
//
////            for (int j = 1; j <=RATING_NUM; j++) {
////                subResult.add(getSubResult(subResult.get(i), mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(cateIdForAca[i]).classId(classId).build())));
////            }
//
//        }
        String date = stService.getMidFinalFormOfDate(mapper.getLatestTest());
        return TeacherGraphContainerVo.builder().date(date).list(subResult).build();
    }

    private double getPercentage (double count, double numberOfClassMembers) {
//        double tmp = (double)(Math.round((count / numberOfClassMembers) * 100) /100);
        double tmp =  Math.round((count/numberOfClassMembers) * 10000) / 100.0;
        return tmp;
    }

    private List<TeacherGraphVo> getSubResult(List<TeacherGraphVo> vo, double numberOfClassMembers){
            for (int i = 0; i < vo.size(); i++) {
            TeacherGraphVo subList = vo.get(i);
            subList.setPercentage(getPercentage(subList.getPercentage(),numberOfClassMembers));
        }
        return vo;
    }

    public List<StudentAcaResultWithIdVo> selAcaTestResultByDatesAndPeriodAndStudent(StudentAcaResultsParam param) {
        return mapper.selAcaTestResultByDatesAndPeriodAndStudent(param);
    }

    public List<StudentMockSumResultWithIdVo> selMockTestResultByDates(StudentSummarySubjectDto dto) {
        return mapper.selMockTestResultByDates(dto);
    }
}

