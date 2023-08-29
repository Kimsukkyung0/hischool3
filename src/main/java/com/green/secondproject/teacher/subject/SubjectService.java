package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.SbjCategoryEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.common.repository.SubjectRepository;
import com.green.secondproject.teacher.subject.model.*;
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
    private final SubjectRepository subjectRepository;


   public List<SubjectVo> subcate() {
        return mapper.subCate();
    }

    public List<SubjectDetailVo> subject(Long categoryid) {

        return mapper.subject(categoryid);
    }

public List<SubjectDetailVo2> tcslist(@AuthenticationPrincipal MyUserDetails user) {
        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUserid(user.getUserId());
        return mapper.tcslist(dto);
    }

    public List<SubjectVo2> smalllist(SubjectDto dto) {
        return mapper.smalllist(dto);
    }

    public int classnum(StudentClassDto dto) {
        return mapper.classnum(dto);
    }

    public int schoolnum(@AuthenticationPrincipal MyUserDetails user) {
        StudentSchoolDto dto = new StudentSchoolDto();
        dto.setGrade(user.getGrade());
        dto.setSchoolNm(user.getSchoolNm());
        return mapper.schoolnum(dto);
    }

public List<MockSubjcetSmallVo> mocksmalllist(Long categoryid) {
    SbjCategoryEntity sbjCategoryEntity= SbjCategoryEntity.builder()
            .categoryId(categoryid)
            .build();

       List<SubjectEntity> subjectList = subjectRepository.findBySbjCategoryEntity(sbjCategoryEntity);
       List<MockSubjcetSmallVo> list = new ArrayList<>();

    for (SubjectEntity subjectEntity : subjectList) {
        list.add(MockSubjcetSmallVo.builder()
                .categoryid(subjectEntity.getSbjCategoryEntity().getCategoryId())
                .subjectid(subjectEntity.getSubjectId())
                .nm(subjectEntity.getNm())
                .build());
    }
    return list;
//
//        return subjectList.stream().map(subjectEntity -> MockSubjcetSmallVo.builder()
//                .categoryid(subjectEntity.getSbjCategoryEntity().getCategoryId())
//                .subjectid(subjectEntity.getSubjectId())
//                .nm(subjectEntity.getNm())
//                .build()).toList();
    }

    public List<MockSubjectBigVo> mockbiglist() {
        return mapper.mockbiglist();
    }


    //Post(insert)

    public int mockins(mockDto2 dto) {
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

    public int instcsbj(SubjectInsDto2 dto) {
        List<SubjectInsVo> list = new LinkedList<>();

        for (int i = 0; i < dto.getList().size(); i++) {
            SubjectInsVo vo = new SubjectInsVo();
            vo.setSubjectid(dto.getList().get(i).getSubjectid());
            vo.setUserid(facade.getLoginUserPk());
            list.add(vo);
        }
        return mapper.instcsbj(list);
    }

    public int acasubject(AcalistDto2 dto) {
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
       Long classId = user.getVanId();
       Integer mockTotal = mapper.mocktotal(classId);
      List<MockGraphVo2> koList = mapper.mockgraph(new MockGraphDto(classId,2L));
      List<MockGraphVo2> mtList = mapper.mockgraph(new MockGraphDto(classId, 4L));
      List<MockGraphVo2> enList = mapper.mockgraph(new MockGraphDto(classId, 5L));
      List<MockGraphVo2> hiList = mapper.mockgraph(new MockGraphDto(classId, 8L));

      List<MockGraphVo2> koList1 = new ArrayList<>();
      List<MockGraphVo2> mtList1 = new ArrayList<>();
      List<MockGraphVo2> enList1 = new ArrayList<>();
      List<MockGraphVo2> hiList1 = new ArrayList<>();

      String nm = "국어";
      for (int i = 1; i <= 9; i++) {
          koList1.add(MockGraphVo2.builder()
                  .nm(nm)
                  .rating(i)
                  .ratio(0)
                  .build());
      }
      for (MockGraphVo2 vo2: koList1) {
          for (MockGraphVo2 vo3 : koList) {
              if (vo2.getRating() == vo3.getRating()) {
                  vo2.setRating(vo3.getRating());
                  vo2.setRatio(vo3.getRatio());
              }
          }
      }

      String nm1 = "수학";
      for (int i = 1; i <= 9; i++) {
          mtList1.add(MockGraphVo2.builder()
                  .nm(nm1)
                  .rating(i)
                  .ratio(0)
                  .build());
      }
      for (MockGraphVo2 vo2: mtList1) {
          for (MockGraphVo2 vo3: mtList) {
              if (vo2.getRating() == vo3.getRating()){
                  vo2.setRating(vo3.getRating());
                  vo2.setRatio(vo3.getRatio());
              }
          }
      }
      String nm2 = "영어";
      for (int i = 1; i <= 9; i++) {
          enList1.add(MockGraphVo2.builder().nm(nm2).rating(i).ratio(0).build());
      }
      for (MockGraphVo2 vo2: enList1) {
          for (MockGraphVo2 vo3 : enList) {
              if(vo2.getRating() == vo3.getRating()){
                  vo2.setRating(vo3.getRating());
                  vo2.setRatio(vo3.getRatio());
              }
          }
      }
      String nm3 = "한국사";
      for (int i = 1; i <= 9; i++) {
          hiList1.add(MockGraphVo2.builder().nm(nm3).rating(i).ratio(0).build());
      }
      for (MockGraphVo2 vo2 :hiList1) {
          for (MockGraphVo2 vo3: hiList) {
              if(vo2.getRating() == vo3.getRating()){
                  vo2.setRating(vo3.getRating());
                  vo2.setRatio(vo3.getRatio());
              }

          }

      }
//      if (mtList.size() == 0){
//          String nm = "수학";
//          for (int i = 1; i <= 9; i++) {
//              mtList.add(MockGraphVo2.builder()
//                      .nm(nm)
//                      .rating(i)
//                      .ratio(0)
//                      .build());
//          }
//      }
//      if(enList.size() == 0){
//          String nm = "영어";
//          for (int i = 1; i <= 9; i++) {
//              enList.add(MockGraphVo2.builder()
//                      .nm(nm)
//                      .rating(i)
//                      .ratio(0).build());
//          }
//      }  if(hiList.size() == 0){
//          String nm = "한국사";
//          for (int i = 1; i <= 9; i++) {
//              hiList.add(MockGraphVo2.builder()
//                      .nm(nm)
//                      .rating(i)
//                      .ratio(0).build());
//          }
//      }


      if (mockTotal != null) {
          for (MockGraphVo2 vo : koList1) {
              vo.setRatio(vo.getRatio() / mockTotal * 100);
          }
          for (MockGraphVo2 vo : mtList1) {
              vo.setRatio(vo.getRatio() / mockTotal * 100);
          }
          for (MockGraphVo2 vo : enList1) {
              vo.setRatio(vo.getRatio() / mockTotal * 100);
          }
          for (MockGraphVo2 vo : hiList1) {
              vo.setRatio(vo.getRatio() / mockTotal * 100);
          }
      }

        return MockGraphVo.builder()
                .koList(koList1)
                .mtList(mtList1)
                .enList(enList1)
                .hiList(hiList1)
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

