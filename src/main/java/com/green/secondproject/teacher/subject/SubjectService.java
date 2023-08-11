package com.green.secondproject.teacher.subject;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.*;
import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
@ToString
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectMapper mapper;
    private final AuthenticationFacade facade;


   public List<SubjectVo> subcate() {
        return mapper.subCate();
    }

    public List<SubjectDetailVo> subject(Long categoryid) {

        return mapper.subject(categoryid);
    }

    List<SubjectDetailVo2> tcslist(@AuthenticationPrincipal MyUserDetails user) {
        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUserid(user.getUserId());
        return mapper.tcslist(dto);
    }

    List<SubjectVo2> smalllist(@AuthenticationPrincipal MyUserDetails user) {
        SubjectDto dto = new SubjectDto();
        dto.setUserid(user.getUserId());

        return mapper.smalllist(dto);
    }

    int classnum(@AuthenticationPrincipal MyUserDetails user) {
        StudentClassDto dto = new StudentClassDto();
        dto.setClassid(user.getClassNum());
        dto.setSchoolid(user.getUserId());
        return mapper.classnum(dto);
    }

    int schoolnum(@AuthenticationPrincipal MyUserDetails user) {
        StudentSchoolDto dto = new StudentSchoolDto();
        dto.setGrade(user.getGrade());
        dto.setSchoolNm(user.getSchoolNm());
        return mapper.schoolnum(dto);
    }

    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid) {
        return mapper.mocksmalllist(categoryid);
    }

    List<MockSubjectBigVo> mockbiglist() {
        return mapper.mockbiglist();
    }


    //Post(insert)

    int mockins(mockDto2 dto) {
        List<MockSubjectVo> list = new ArrayList<>();

        for (int i = 0; i < dto.getList().size(); i++) {
            MockSubjectVo vo = new MockSubjectVo();
            vo.setRating(dto.getList().get(i).getRating());
            vo.setMon(dto.getList().get(i).getMon());
            vo.setStandardscore(dto.getList().get(i).getStandardscore());
            vo.setSubjectid(dto.getList().get(i).getSubjectid());
            vo.setPercent(dto.getList().get(i).getPercent());
            vo.setUserid(dto.getList().get(i).getUserid());
            list.add(vo);
        }
        return mapper.mockins(list);
    }

    int instcsbj(SubjectInsDto2 dto) {
        List<SubjectInsVo> list = new LinkedList<>();

        for (int i = 0; i < dto.getList().size(); i++) {
            SubjectInsVo vo = new SubjectInsVo();
            vo.setSubjectid(dto.getList().get(i).getSubjectid());
            vo.setUserid(facade.getLoginUserPk());
            list.add(vo);
        }
        return mapper.instcsbj(list);
    }

    int acasubject(AcalistDto2 dto) {
    List<AcaSubjectVo> list = new LinkedList<>();
        for (int i = 0; i <dto.getList().size() ; i++) {
            AcaSubjectVo vo = new AcaSubjectVo();

            vo.setMidfinal(dto.getList().get(i).getMidfinal());
            vo.setClassrank(dto.getList().get(i).getClassrank());
            vo.setScore(dto.getList().get(i).getScore());
            vo.setSemester(dto.getList().get(i).getSemester());
            vo.setSubjectid(dto.getList().get(i).getSubjectid());
            vo.setRating(dto.getList().get(i).getRating());
            vo.setWholerank(dto.getList().get(i).getWholerank());
            vo.setUserid(dto.getList().get(i).getUserid());
            list.add(vo);
        }
        return mapper.acasubject(list);
    }

  public MockGraphVo mockgraph(@AuthenticationPrincipal MyUserDetails user){
       Long classId = user.getClassId();
       int mockTotal = mapper.mocktotal(classId);
      List<MockGraphVo2> koList = mapper.mockgraph(new MockGraphDto(classId,2L));
      List<MockGraphVo2> mtList = mapper.mockgraph(new MockGraphDto(classId, 4L));
      List<MockGraphVo2> enList = mapper.mockgraph(new MockGraphDto(classId, 5L));
      List<MockGraphVo2> hiList = mapper.mockgraph(new MockGraphDto(classId, 8L));
      for (MockGraphVo2 vo : koList) {
          vo.setRatio(vo.getRatio() / mockTotal * 100);
      }
      for (MockGraphVo2 vo : mtList) {
          vo.setRatio(vo.getRatio() / mockTotal * 100);
      }
      for (MockGraphVo2 vo : enList) {
          vo.setRatio(vo.getRatio() / mockTotal * 100);
      }
      for (MockGraphVo2 vo : hiList) {
          vo.setRatio(vo.getRatio() / mockTotal * 100);
      }

        return MockGraphVo.builder()
                .koList(koList)
                .mtList(mtList)
                .enList(enList)
                .hiList(hiList)
                .build();
    }

    public List<StudentListVo> stulist(StudentListDto dto){
       return mapper.stulist(dto);
    };

    public List<ResultAcaVo> selaca(ResultAcaDto dto){

        return mapper.selaca(dto);
    }

    public List<ResultMockVo> selmock(ResultMockDto dto){
        return mapper.selmock(dto);
    }

}
