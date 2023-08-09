package com.green.secondproject.teacher;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
        Long koCateId = 1L;
        Long maCateId = 3L;
        Long enCateId = 6L;
        Long htCateId = 8L;
        List<TeacherGraphVo> koList = mapper.teacherAcaGraph(classId,koCateId);
        List<TeacherGraphVo> maList = mapper.teacherAcaGraph(classId,maCateId);
        List<TeacherGraphVo> enList = mapper.teacherAcaGraph(classId,enCateId);
        List<TeacherGraphVo> htList = mapper.teacherAcaGraph(classId,htCateId);

        List<List<TeacherGraphVo>> subResult = new ArrayList<>();

        //그래프 형식
        subResult.add(getSubResult(koList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(koCateId).classId(classId).build())));
        subResult.add(getSubResult(maList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(maCateId).classId(classId).build())));
        subResult.add(getSubResult(enList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(enCateId).classId(classId).build())));
        subResult.add(getSubResult(htList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(htCateId).classId(classId).build())));

        return TeacherGraphContainerVo.builder().date("2023-2 기말").list(subResult).build();
    }


    private double getPercentage (double count, double numberOfClassMembers) {
        log.info("numberOfClassMembers : {}",numberOfClassMembers);
        log.info("count : {}",count);
        double tmp = (count /numberOfClassMembers)*100;
        log.info("tmp : {}",tmp);
        return tmp;
    }

    private List<TeacherGraphVo> getSubResult(List<TeacherGraphVo> vo, double numberOfClassMembers){
            for (int i = 0; i < vo.size(); i++) {
            TeacherGraphVo subList = vo.get(i);
            log.info("subList : {}" ,subList);

            subList.setPercentage(getPercentage(subList.getPercentage(),numberOfClassMembers));
                log.info("subListpercentage : {}" ,subList.getPercentage());
        }
        return vo;
    }
}

