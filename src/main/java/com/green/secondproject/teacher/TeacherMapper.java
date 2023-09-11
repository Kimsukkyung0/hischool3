package com.green.secondproject.teacher;

import com.green.secondproject.student.model.*;
import com.green.secondproject.teacher.model.*;
import com.green.secondproject.teacher.subject.model.*;
import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {

    List<SelAcaResultVo> selAcaResult(SelAcaResultDto dto);
    List<SelMockResultVo> selMockResult(SelMockResultDto dto);
    int updAcaResult(UpdAcaResultDto dto);
    int delAcaResult(DelResultDto dto);


    //===========JPA 전환완료 =================================================
    //    List<TeacherGraphVo> teacherAcaGraph(Long classId,Long categoryId);
    //    String getLatestTest();

    //수천
    //    int delMockResult(DelResultDto dto);
    //    int classStudent(ClassStudentDto dto);
    //    int aprStudent(ClassStudentDto dto);
    //    int acasubject(List<AcaSubjectVo> list);

    //정민
    //    int acceptStudent(AcceptStudentDto dto);
    //    int rejectStudent(AcceptStudentDto dto);
    //    int cancelAcceptStd(AcceptStudentDto dto);
    //    int updMockResult(UpdMockResultDto dto);
    //    List<SelSignedStudentVo> selSignedStudent(SelSignedStudentDto dto);
    //    List<SelUnsignedStudentVo> selUnsignedStudent(SelUnsignedStudentDto dto);



    //===============subject- > teacher ======================================

    int mockins(List<MockSubjectVo> list);
    Integer mocktotal(Long vanId); // 모의고사 퍼센트 계산용
    List<MockGraphVo2> mockgraph(MockGraphDto dto);
    List<ResultAcaVo> selaca(ResultAcaDto dto);
    List<ResultMockVo> selmock(ResultMockDto dto);
}
