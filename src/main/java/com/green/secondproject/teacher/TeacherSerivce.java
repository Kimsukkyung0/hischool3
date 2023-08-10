package com.green.secondproject.teacher;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.student.StudentMapper;
import com.green.secondproject.student.StudentService;
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
        Long[] cateIdForAca = {1L,3L,6L,8L};

        List<List<TeacherGraphVo>> subResult = new ArrayList<>();

        for (int i = 0; i < cateIdForAca.length; i++) {
            List<TeacherGraphVo> tmpVo = mapper.teacherAcaGraph(classId,cateIdForAca[i]);
            subResult.add(tmpVo);
        }
        for (int i = 0; i < cateIdForAca.length; i++) {
            subResult.add(getSubResult(subResult.get(i), mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(cateIdForAca[i]).classId(classId).build())));
        }

        String date = stService.getMidFinalFormOfDate(mapper.getLatestTest());
        return TeacherGraphContainerVo.builder().date(date).list(subResult).build();
    }

//        List<TeacherGraphVo> koList =
//        List<TeacherGraphVo> maList = mapper.teacherAcaGraph(classId,maCateId);
//        List<TeacherGraphVo> enList = mapper.teacherAcaGraph(classId,enCateId);
//        List<TeacherGraphVo> htList = mapper.teacherAcaGraph(classId,htCateId);


//        //그래프 형식
//        subResult.add(getSubResult(koList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(koCateId).classId(classId).build())));
//        subResult.add(getSubResult(maList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(maCateId).classId(classId).build())));
//        subResult.add(getSubResult(enList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(enCateId).classId(classId).build())));
//        subResult.add(getSubResult(htList, mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().categoryId(htCateId).classId(classId).build())));


    private double getPercentage (double count, double numberOfClassMembers) {
        double tmp = (count /numberOfClassMembers)*100;
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

