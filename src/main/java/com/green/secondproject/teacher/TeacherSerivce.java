package com.green.secondproject.teacher;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherSerivce {
    private final TeacherMapper mapper;
    private final AuthenticationFacade facade;


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


    public List<SelAcaResultVo> selAcaResult(Long userId) {
        SelAcaResultDto dto = new SelAcaResultDto();
        dto.setUserId(userId);
        return mapper.selAcaResult(dto);
    }


    public List<SelMockResultVo> selMockResult(Long userId) {
        SelMockResultDto dto = new SelMockResultDto();
        dto.setUserId(userId);
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


    public int delTeacher(MyUserDetails myuser) {
        TeacherDelDto dto = new TeacherDelDto();
        dto.setUserId(myuser.getUserId());
        return mapper.delTeacher(dto);
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

    public List<TeacherGraphVo2> teacherAcaGraph(Long classId){
        //DB쪽에 올해연도 및 반 아이디전달
//        LocalDate now = LocalDate.now();
//        String year = String.valueOf(now.getYear());
//        List<TeacherGraphVo> tmpList = mapper.teacherAcaGraph(classId,year);
//        log.info(" tmpList : {}",tmpList);
//        //반학급 총 인원
//        ClassStudentDto classStudentDto = new ClassStudentDto();
//        classStudentDto.setClassid(classId);
//        int numberOfClassMembers = classStudent(classStudentDto);
//
//        //퍼센트/subjectNm
//        List<TeacherGraphVo2> result = new ArrayList<>();
//        for(TeacherGraphVo vo : tmpList){
//            int percent = getPercentage(vo.getCount(),numberOfClassMembers);
//            TeacherGraphVo2 vo2 = TeacherGraphVo2.builder()
//                    .date(vo.getDate())
//                    .subjectNm(vo.getSubjectNm())
//                    .percent(percent)
//                    .rating(vo.getRating()).build();
//            result.add(vo2);
//        }
//
//        return result;
        return null;
    }

    private int getPercentage (int count,int numberOfClassMembers){
        return (int) Math.round((numberOfClassMembers*0.1) * count);
    }
}

