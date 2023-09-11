package com.green.secondproject.teacher;

import com.green.secondproject.result.AcaResultMapper;
import com.green.secondproject.result.model.AcaResultInsDto;
import com.green.secondproject.result.model.CalcClassRankParam;
import com.green.secondproject.result.model.CalcWholeRankParam;
import com.green.secondproject.admin.model.NoticeTeacherListVo;
import com.green.secondproject.admin.model.NoticeTeacherVo;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import com.green.secondproject.student.StudentService;
import com.green.secondproject.student.model.*;
import com.green.secondproject.teacher.model.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.teacher.subject.model.*;
import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherMapper mapper;
    private final UserRepository userRepository;
    private final MockResultRepository mockResultRepository;
    private final AuthenticationFacade facade;
    private final AcaResultRepository acaResultRepository;
    private final NoticeRepository noticeRepository;
    private final AcaResultMapper acaResultMapper;
    private final StudentService stService;

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
        List<UserEntity> stdList = userRepository.findAllByVanEntityAndAprYnAndEnrollStateAndRoleType(
                VanEntity.builder()
                        .vanId(myuser.getVanId())
                        .build(), 0, EnrollState.ENROLL, RoleType.STD);

        return stdList.stream().map(userEntity -> SelUnsignedStudentVo.builder()
                .userId(userEntity.getUserId())
                .classId(userEntity.getVanEntity().getVanId())
                .aprYn(userEntity.getAprYn())
                .snm(userEntity.getNm())
                .birth(userEntity.getBirth())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .build()).toList();
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
        Optional<MockResultEntity> opt = mockResultRepository.findById(dto.getResultId());
        if (opt.isEmpty()) {
            return 0;
        }

        MockResultEntity entity = opt.get();
        entity.setSubjectEntity(SubjectEntity.builder().subjectId(dto.getSubjectId()).build());
        entity.setYear(dto.getYear());
        entity.setMon(dto.getMon());
        entity.setStandardScore(dto.getStandardScore());
        entity.setRating(dto.getRating());
        entity.setPercent(dto.getPercent());
        mockResultRepository.save(entity);

        return 1;
    }

    public int updAcaResult(UpdAcaResultDto dto) {
        dto.setResultId(dto.getResultId());
        return mapper.updAcaResult(dto);
    }


    public int delMockResult(Long resultId) {
        Optional<MockResultEntity> opt = mockResultRepository.findById(resultId);
        if (opt.isEmpty()) {
            return 0;
        }

        mockResultRepository.deleteById(resultId);
        return 1;
    }


    public int delAcaRusult(Long resultId) {
        Optional<AcaResultEntity> opt = acaResultRepository.findById(resultId);
        if(opt.isEmpty()) {
            return 0;
        }
        acaResultRepository.deleteById(resultId);
        return 1;
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

//            acaResultRepository.getLatestTest()

            double vanMemNum = acaResultRepository.countStudentsNumByVanAndCate(classId, RoleType.STD, 1,tmpCateIdForAca);

//            double vanMemNum = mapper.getNumberOfStudentByCate(TeacherGraphDto.builder().
//                    categoryId(tmpCateIdForAca)
//                    .classId(classId)
//                    .build());
            log.info("vanMemNum : {}", vanMemNum);

            //mapperList -  percentage = 인원수
            List<TeacherGraphVo> tmpVoList = acaResultRepository.teacherAcaGraph(classId,tmpCateIdForAca);
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
        //acaResultRepository.getLatestTest(u)
        String date = stService.getMidFinalFormOfDateByString("202321");
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
        UserEntity user = userRepository.findById(param.getUserId()).get();
        VanEntity van = user.getVanEntity();
        param.setVanId(van.getVanId());
        param.setGrade(van.getGrade());
        param.setSchoolId(van.getSchoolEntity().getSchoolId());

        return acaResultRepository.searchAcaResult(param);

//        return resList.stream().map(acaResultEntity -> StudentAcaResultWithIdVo.builder()
//                .resultId(acaResultEntity.getResultId())
//                .year(acaResultEntity.getYear())
//                .semester(acaResultEntity.getSemester())
//                .midFinal(acaResultEntity.getMidFinal())
//                .cateName(acaResultEntity.getSubjectEntity().getSbjCategoryEntity().getNm())
//                .nm(acaResultEntity.getSubjectEntity().getNm())
//                .score(acaResultEntity.getScore())
//                .rating(acaResultEntity.getRating())
//                .classRank(acaResultEntity.getClassRank())
//                .wholeRank(acaResultEntity.getWholeRank())
//                .build()).toList();
        //return mapper.selAcaTestResultByDatesAndPeriodAndStudent(param);
    }

    public List<StudentMockSumResultWithIdVo> selMockTestResultByDates(StudentSummarySubjectDto dto) {
        return mockResultRepository.searchMockResult(dto);

//        return resList.stream().map(mockResultEntity -> StudentMockSumResultWithIdVo.builder()
//                .resultId(mockResultEntity.getResultId())
//                .year(mockResultEntity.getYear())
//                .mon(mockResultEntity.getMon())
//                .cateName(mockResultEntity.getSubjectEntity().getSbjCategoryEntity().getNm())
//                .nm(mockResultEntity.getSubjectEntity().getNm())
//                .standardScore(mockResultEntity.getStandardScore())
//                .rating(mockResultEntity.getRating())
//                .percent(mockResultEntity.getPercent())
//                .build()).toList();
        //return mapper.selMockTestResultByDates(dto);
    }
    //공지사항


    public NoticeTeacherListVo NoticeTeacher(){
        MyUserDetails userDetails = facade.getLoginUser();  // 현재 로그인한 사용자의 정보

        UserEntity currentUser = userRepository.findById(userDetails.getUserId()).orElse(null);  // 사용자의 상세 정보를 가져옴

        if (currentUser == null) {
            throw new RuntimeException("로그인한 사용자 정보가 없습니다.");
        }
        Long currentVanId = currentUser.getVanEntity().getVanId();  // 로그인한 사용자의 vanId 값을 가져옴
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<NoticeEntity> imptList = noticeRepository.findImportantNoticesByVanId(currentVanId,sort);
        List<NoticeEntity> normalList = noticeRepository.findTopByImptYnAndSchoolEntityOrderByNoticeIdDesc(currentVanId,sort);

        return NoticeTeacherListVo.builder()
                .imptList(imptList.stream().map(noticeEntity -> NoticeTeacherVo
                        .builder()
                        .noticeId(noticeEntity.getNoticeId())
                        .title(noticeEntity.getTitle())
                        .imptYn(noticeEntity.getImptYn())
                        .hits(noticeEntity.getHits())
                        .createdAt(LocalDate.from(noticeEntity.getCreatedAt()))
                        .build()).collect(Collectors.toList()))
                .normalList(normalList.stream().map(noticeEntity -> NoticeTeacherVo
                        .builder()
                        .noticeId(noticeEntity.getNoticeId())
                        .title(noticeEntity.getTitle())
                        .imptYn(noticeEntity.getImptYn())
                        .hits(noticeEntity.getHits())
                        .createdAt(LocalDate.from(noticeEntity.getCreatedAt()))
                        .build()).collect(Collectors.toList()))
                .build();
    }

    //===============subject- > teacher ======================================

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


    public List<ResultAcaVo> selaca(ResultAcaDto dto){

        return mapper.selaca(dto);
    }
    public List<ResultMockVo> selmock(ResultMockDto dto){
        return mapper.selmock(dto);
    }

//=========================================수정요 ======================================================

    public List<AcaResultEntity> saveAcaResult(AcaResultInsDto dto) {
        List<AcaResultEntity> list = dto.getList().stream().map(item -> AcaResultEntity.builder()
                .userEntity(UserEntity.builder().userId(dto.getUserId()).build())
                .subjectEntity(SubjectEntity.builder().subjectId(item.getSubjectId()).build())
                .semester(dto.getSemester())
                .midFinal(dto.getMidFinal())
                .score(item.getScore())
                .year(String.valueOf(LocalDate.now().getYear()))
                .build()).collect(Collectors.toList());

        return acaResultRepository.saveAll(list);
//        List<AcaSubjectVo> list = new LinkedList<>();
//        for (int i = 0; i <dto.getList().size() ; i++) {
//            AcaSubjectVo vo = new AcaSubjectVo();
//
//            vo.setMidfinal(dto.getList().get(i).getMidfinal());
//            vo.setClassrank(dto.getList().get(i).getClassrank());
//            vo.setScore(dto.getList().get(i).getScore());
//            vo.setSemester(dto.getList().get(i).getSemester());
//            vo.setSubjectid(dto.getList().get(i).getSubjectid());
//            vo.setRating(dto.getList().get(i).getRating());
//            vo.setWholerank(dto.getList().get(i).getWholerank());
//            vo.setUserid(dto.getList().get(i).getUserid());
//            list.add(vo);
//        }
//        return mapper.acasubject(list);
    }

    public int calcRank(CalcClassRankParam p) {
        String year = String.valueOf(LocalDate.now().getYear());
        MyUserDetails user = facade.getLoginUser();

        p.setVanId(user.getVanId());
        p.setYear(year);
        acaResultMapper.calcClassRank(p);

        return acaResultMapper.calcWholeRankAndRating(CalcWholeRankParam.builder()
                .semester(p.getSemester())
                .midFinal(p.getMidFinal())
                .year(year)
                .schoolId(user.getSchoolId())
                .grade(user.getGrade())
                .build());
    }

    public List<StudentVo> getStudentList(String name) {
        Long vanId = facade.getLoginUser().getVanId();
        return userRepository.studentList(name, vanId);
    }
}

