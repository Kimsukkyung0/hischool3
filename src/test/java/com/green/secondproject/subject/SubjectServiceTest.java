//package com.green.secondproject.subject;
//
//import com.green.secondproject.common.config.redis.RedisService;
//import com.green.secondproject.common.config.security.AuthenticationFacade;
//import com.green.secondproject.common.config.security.model.MyUserDetails;
//import com.green.secondproject.teacher.subject.SubjectMapper;
//import com.green.secondproject.teacher.subject.SubjectService;
//import com.green.secondproject.teacher.subject.model.*;
//import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
//import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@Import({SubjectService.class})
//public class SubjectServiceTest {
//
//    @MockBean
//    private SubjectMapper mapper;
//    @MockBean
//    private AuthenticationFacade FACADE;
//    @Autowired
//    private SubjectService service;
//
//    @MockBean
//    private RedisService redisService;
//
//    @BeforeEach
//    void beforeEach() {
//        UserDetails user = createUserDetails();
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//
//    }
//
//    private UserDetails createUserDetails() {
//        List<String> roles = new ArrayList<>();
//        roles.add("ROLE_STU");
//        roles.add("ROLE_TC");
//
//        UserDetails userDetails = MyUserDetails.builder()
//                .userId(1L)
//                .classNum("1")
//                .roles(roles)
//                .build();
//        return userDetails;
//    }
//
//    @Test
//    @DisplayName("SubjectServiceTest - subcate()과목계열")
//    void subCate(){
//        List<SubjectVo> list = new ArrayList<>();
//        SubjectVo vo = new SubjectVo();
//        vo.setCategoryid(1L);
//        vo.setNm("국어");
//        list.add(vo);
//        when(mapper.subCate()).thenReturn(list);
//        List<SubjectVo> list1 = service.subcate();
//
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//        assertEquals(list.get(0).getCategoryid(),list1.get(0).getCategoryid());
//
//        verify(mapper).subCate();
//    }
//    @Test
//    @DisplayName("SubjectServiceTest - subject() 세부과목")
//    void subject(){
//        List<SubjectDetailVo> list = new ArrayList<>();
//        SubjectDetailVo vo = new SubjectDetailVo();
//        vo.setSubjectid(1L);
//        vo.setNm("화법과 언어");
//        vo.setCategoryid(1L);
//        list.add(vo);
//        when(mapper.subject(1L)).thenReturn(list);
//        List<SubjectDetailVo> list1 = service.subject(1L);
//
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//        assertEquals(list.get(0).getSubjectid(),list1.get(0).getSubjectid());
//        assertEquals(list.get(0).getCategoryid(),list1.get(0).getCategoryid());
//        verify(mapper).subject(1L);
//    }
//    @Test
//    @DisplayName("SubjectDetailTest2 - tcslist() 등록후 과목계열list")
//    void tcslist() {
//        when(FACADE.getLoginUserPk()).thenReturn(1L);
//        List<SubjectDetailVo2> list = new ArrayList<>();
//        SubjectDetailVo2 vo2 = new SubjectDetailVo2();
//        SubjectDetailDto dto = new SubjectDetailDto();
//        dto.setUserid(FACADE.getLoginUserPk());
//        vo2.setNm("국어");
//        vo2.setSubjectid(1L);
//        vo2.setUserid(FACADE.getLoginUserPk());
//
//        list.add(vo2);
//        when(mapper.tcslist(dto)).thenReturn(list);
//        List<SubjectDetailVo2> list1 =  service.tcslist((MyUserDetails) createUserDetails());
//
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//        assertEquals(list.get(0).getUserid(),list1.get(0).getUserid());
//        assertEquals(list.get(0).getSubjectid(),list1.get(0).getSubjectid());
//    }
//    @Test
//    @DisplayName("smallList - 등록후 세부과목 리스트")
//    void smalllist(){
//        when(FACADE.getLoginUserPk()).thenReturn(1L);
//        List<SubjectVo2> list = new ArrayList<>();
//        SubjectVo2 vo2 = new SubjectVo2();
//        SubjectDto dto = new SubjectDto();
//        dto.setUserid(FACADE.getLoginUserPk());
//        vo2.setNm("화법과 언어");
//        vo2.setSubjectid(1L);
//        vo2.setUserid(FACADE.getLoginUserPk());
//        list.add(vo2);
//
//        when(mapper.smalllist(dto)).thenReturn(list);
//        List<SubjectVo2> list1 = service.smalllist(dto);
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//        assertEquals(list.get(0).getUserid(),list1.get(0).getUserid());
//        assertEquals(list.get(0).getSubjectid(),list1.get(0).getSubjectid());
//    }
//    @Test
//    @DisplayName("smallList - 학급수(반)")
//    void classnum(){
//        MyUserDetails aaa = (MyUserDetails) createUserDetails();
//
//        when(FACADE.getLoginUserPk()).thenReturn(2L);
//        StudentClassDto dto = new StudentClassDto();
//        dto.setSchoolid(FACADE.getLoginUserPk());
//        dto.setClassid(aaa.getClassNum());
//
//        when(mapper.classnum(dto)).thenReturn(1);
//
//        int num = service.classnum(dto);
//        assertEquals(dto.getSchoolid(),num);
//    }
//
//    @Test
//    @DisplayName("smallList - 학급수(학교학년)")
//    void schoolnum(){
//        MyUserDetails user = (MyUserDetails) createUserDetails();
//
//        user.setSchoolNm("1");
//        user.setGrade("1");
//        when(FACADE.getLoginUserPk()).thenReturn(1L);
//        StudentSchoolDto dto = new StudentSchoolDto();
//        dto.setSchoolNm(user.getSchoolNm());
//        dto.setGrade(user.getGrade());
//
//        when(mapper.schoolnum(dto)).thenReturn(1);
//
//        int num = service.schoolnum(user);
//        assertEquals(Integer.parseInt(dto.getSchoolNm()),num);
//        assertEquals(Integer.parseInt(dto.getGrade()),num);
//    }
//    @Test
//    @DisplayName("mocksmalllist - 모의고사 세부과목리스트")
//    void mocksmalllist(){
//        List<MockSubjcetSmallVo> list = new ArrayList<>();
//            MockSubjcetSmallVo vo = new MockSubjcetSmallVo();
//            vo.setCategoryid(1L);
//            vo.setNm("국어");
//            vo.setSubjectid(1L);
//
//            list.add(vo);
//            when(mapper.mocksmalllist(1L)).thenReturn(list);
//
//            List<MockSubjcetSmallVo> list1 = service.mocksmalllist(1L);
//            assertEquals(list.get(0).getCategoryid(),list1.get(0).getCategoryid());
//            assertEquals(list.get(0).getSubjectid(),list1.get(0).getSubjectid());
//            assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//    }
//    @Test
//    @DisplayName("mockbiglist - 모의고사 계열리스트")
//    void mockbiglist(){
//        List<MockSubjectBigVo> list = new ArrayList<>();
//        MockSubjectBigVo vo = new MockSubjectBigVo();
//        vo.setCategoryid(1L);
//        vo.setNm("국어");
//
//        list.add(vo);
//        when(mapper.mockbiglist()).thenReturn(list);
//
//        List<MockSubjectBigVo> list1 = service.mockbiglist();
//
//        assertEquals(list.get(0).getCategoryid(),list1.get(0).getCategoryid());
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//    }
//    @Test
//    @DisplayName("mockins - 모의고사 성적입력")
//    void mockins(){
//
//        mockDto2 dto2 = new mockDto2();
//        List<MockSubjectVo2> list = new ArrayList<>();
//        MockSubjectVo2 vo = new MockSubjectVo2();
//        vo.setPercent(1);
//        vo.setStandardscore(1);
//        vo.setRating(1);
//        vo.setMon(1);
//        vo.setUserid(FACADE.getLoginUserPk());
//        vo.setSubjectid(1L);
//        list.add(vo);
//
//        dto2.setList(list);
//
//        when(mapper.mockins(any())).thenReturn(1);
//
//        int list1 = service.mockins(dto2);
//        assertEquals(list.get(0).getMon(),list1);
//        assertEquals(list.get(0).getRating(),list1);
//
//    }
//    @Test
//    @DisplayName("instcsbj -  과목등록(선생님등록)")
//    void instcsbj(){
//        SubjectInsDto dto = new SubjectInsDto();
//        List<SubjectInsVo> list = new ArrayList<>();
//        SubjectInsVo vo = new SubjectInsVo();
//
//        SubjectInsDto2 dto2 = new SubjectInsDto2();
//        List<SubjectInsVo2> list1 = new ArrayList<>();
//        SubjectInsVo2 vo2 = new SubjectInsVo2();
//
//        vo2.setSubjectid(1L);
//
//        list1.add(vo2);
//        dto2.setList(list1);
//
//
//        vo.setSubjectid(1L);
//        vo.setUserid(FACADE.getLoginUserPk());
//        list.add(vo);
//        dto.setList(list);
//
//        when(mapper.instcsbj(any())).thenReturn(1);
//        int list2 = service.instcsbj(dto2);
//
//        assertEquals(list.get(0).getUserid(),FACADE.getLoginUserPk());
//        assertEquals(list.get(0).getSubjectid(),list2);
//    }
//    @Test
//    @DisplayName("학생별 내신성적 등록")
//    void acasubject() {
//        AcalistDto2 dto2 = new AcalistDto2();
//        AcaSubjectVo2 vo2 = new AcaSubjectVo2();
//        List<AcaSubjectVo2> list = new ArrayList<>();
//
//        vo2.setUserid(FACADE.getLoginUserPk());
//        vo2.setSubjectid(1L);
//        vo2.setSemester(1);
//        vo2.setClassrank(1);
//        vo2.setMidfinal(1);
//        vo2.setScore(1);
//        vo2.setRating(1);
//        vo2.setWholerank(1);
//        list.add(vo2);
//
//        dto2.setList(list);
//
//        when(mapper.acasubject(any())).thenReturn(1);
//        int list1 = service.acasubject(dto2);
//        assertEquals(list.get(0).getUserid(),FACADE.getLoginUserPk());
//        assertEquals(list.get(0).getSubjectid(),list1);
//        assertEquals(list.get(0).getSemester(),list1);
//        assertEquals(list.get(0).getMidfinal(),list1);
//        assertEquals(list.get(0).getScore(),list1);
//        assertEquals(list.get(0).getRating(),list1);
//        assertEquals(list.get(0).getWholerank(),list1);
//    }
//    @Test
//    @DisplayName("mockgraph - 모의고사 그래프{학년,반별 등급(국어,수학,영어,한국사)}")
//    void mockgraph(){
//        MyUserDetails user = (MyUserDetails) createUserDetails();
//        List<MockGraphVo2> kolist = new ArrayList<>();
//        MockGraphVo2 vo2 = MockGraphVo2.builder().build();
//        MockGraphVo vo = MockGraphVo.builder().build();
//        vo2.setRatio(1);
//        vo2.setNm("1");
//        vo2.setRating(1);
//          kolist.add(vo2);
//user.setNm("1");
//user.setGrade("1");
//          vo.setKoList(kolist);
//        when(mapper.mockgraph(any())).thenReturn(kolist);
//        assertEquals(kolist.get(0).getRating(),Integer.parseInt(user.getGrade()));
//        assertEquals(kolist.get(0).getNm(),user.getNm());
//        assertEquals(kolist.get(0).getRatio(),1);
//    }
//    @Test
//    @DisplayName("stulist - 학생 리스트")
//    void stulist(){
//        List<StudentListVo> list = new ArrayList<>();
//        StudentListVo vo = new StudentListVo();
//        StudentListDto dto = new StudentListDto();
//        vo.setUserid(FACADE.getLoginUserPk());
//        vo.setNm("국어");
//        list.add(vo);
//        dto.setClassid(1L);
//        dto.setUserid(FACADE.getLoginUserPk());
//
//        when(mapper.stulist(any())).thenReturn(list);
//        List<StudentListVo> list1 = service.stulist(dto);
//
//        assertEquals(list.get(0).getNm(),list1.get(0).getNm());
//        assertEquals(list.get(0).getUserid(),list1.get(0).getUserid());
//    }
//    @Test
//    @DisplayName("selaca - 학생내신리스트")
//    void selaca() {
//        List<ResultAcaVo> list = new ArrayList<>();
//        ResultAcaVo vo = new ResultAcaVo();
//        ResultAcaDto dto = new ResultAcaDto();
//        vo.setSubjectId(1L);
//        vo.setScore(100);
//        vo.setRating(1);
//        vo.setMidfinal(1);
//        vo.setSemester(1);
//        vo.setCategoryId(1L);
//        vo.setClassRank(1);
//        vo.setWholeRank(1);
//        list.add(vo);
//
//        when(mapper.selaca(dto)).thenReturn(list);
//    List<ResultAcaVo> list1 = service.selaca(dto);
//    assertEquals(list.get(0).getSubjectId(),list1.get(0).getSubjectId());
//    assertEquals(list.get(0).getScore(),list1.get(0).getScore());
//    assertEquals(list.get(0).getRating(),list1.get(0).getRating());
//    assertEquals(list.get(0).getMidfinal(),list1.get(0).getMidfinal());
//    assertEquals(list.get(0).getSemester(),list1.get(0).getSemester());
//    assertEquals(list.get(0).getCategoryId(),list1.get(0).getCategoryId());
//    assertEquals(list.get(0).getClassRank(),list1.get(0).getClassRank());
//    assertEquals(list.get(0).getWholeRank(),list1.get(0).getWholeRank());
//    }
//    @Test
//    @DisplayName("selmock - 모의고사List")
//    void selmock(){
//        List<ResultMockVo> list = new ArrayList<>();
//        ResultMockVo vo = new ResultMockVo();
//        ResultMockDto dto = new ResultMockDto();
//
//        vo.setSubjectId(1L);
//        vo.setMon(1);
//        vo.setRating(1);
//        vo.setPercent(1);
//        vo.setCategoryId(1L);
//        vo.setStandardScore(1);
//        list.add(vo);
//
//        when(mapper.selmock(dto)).thenReturn(list);
//
//        List<ResultMockVo> list1 = service.selmock(dto);
//        assertEquals(list.get(0).getSubjectId(),list1.get(0).getSubjectId());
//        assertEquals(list.get(0).getRating(),list1.get(0).getRating());
//        assertEquals(list.get(0).getMon(),list1.get(0).getMon());
//        assertEquals(list.get(0).getPercent(),list1.get(0).getPercent());
//        assertEquals(list.get(0).getStandardScore(),list1.get(0).getStandardScore());
//        assertEquals(list.get(0).getCategoryId(),list1.get(0).getCategoryId());
//
//    }
//
//
//}
//
