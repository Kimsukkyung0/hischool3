package com.green.secondproject.teacher;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.student.StudentMapper;
import com.green.secondproject.student.StudentService;
import com.green.secondproject.teacher.model.*;
import com.green.secondproject.utils.MyGradeGraphUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherSerivce {
    private final TeacherMapper mapper;
    private final AuthenticationFacade facade;
    private final StudentService stService;



    public List<SelSignedStudentVo> selSignedStudent(MyUserDetails myuser) {
        SelSignedStudentDto dto = new SelSignedStudentDto();
        dto.setClassId(myuser.getClassId());
        return mapper.selSignedStudent(dto);
    }


    public List<SelUnsignedStudentVo> selUnsignedStudent(MyUserDetails myuser) {
        SelUnsignedStudentDto dto = new SelUnsignedStudentDto();
        dto.setClassId(myuser.getClassId());
        return mapper.selUnsignedStudent(dto);
    }


    public List<SelAcaResultVo> selAcaResult(SelAcaResultDto dto) {
        return mapper.selAcaResult(dto);
    }


    public List<SelMockResultVo> selMockResult(SelMockResultDto dto) {
        return mapper.selMockResult(dto);
    }


    public int acceptStudent(Long userId) {
        AcceptStudentDto dto = new AcceptStudentDto();
        dto.setUserId(userId);
        return mapper.acceptStudent(dto);
    }


//    public int rejectStudent(Long userId) {
//        AcceptStudentDto dto = new AcceptStudentDto();
//        dto.setUserId(userId);
//        return mapper.rejectStudent(dto);
//    }


    public int cancelAcceptStd(Long userId) {
        AcceptStudentDto dto = new AcceptStudentDto();
        dto.setUserId(userId);
        return mapper.cancelAcceptStd(dto);
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


    public int classStudent(ClassStudentDto dto){
        return mapper.classStudent(dto);
    }
    public int aprStudent(Long classid){
        ClassStudentDto dto = new ClassStudentDto();
        dto.setClassid(classid);
        return mapper.aprStudent(dto);
    }

    public TeacherGraphContainerVo teacherAcaGraph(Long classId) {
        MyGradeGraphUtils mg = new MyGradeGraphUtils();
        Long[] cateIdForAca = mg.getCateIdForAca();//1367
        String[] cateNm = MyGradeGraphUtils.cateNm;//국수영한
        int RATING_NUM = mg.RATING_NUM;
        DecimalFormat df = new DecimalFormat("0.00");
        List<List<TeacherGraphVo>> subResult = MyGradeGraphUtils.teacherGraphListAtb();
        //과목 4 * 등급 9  (0%가 들어있는 리스트)

        //과목을 넣기위한 임시거처..?
        List<List<TeacherGraphVo>> tmpSubResult = new ArrayList<>();
        List<TeacherGraphVo> tmpSubList = new ArrayList<>();

        for (int i = 0; i < cateNm.length; i++) { //4번 돌것이다.
            //국수영한 4개 * 9개의 리스트 받아오기
            //일단 국 9 수 9 영 9 한 9
            List<TeacherGraphVo> subSubList = subResult.get(i);
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
}

