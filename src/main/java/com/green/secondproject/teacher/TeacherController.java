package com.green.secondproject.teacher;

import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.result.model.AcaResultInsDto;
import com.green.secondproject.result.model.CalcClassRankParam;
import com.green.secondproject.admin.model.NoticeTeacherListVo;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.student.StudentService;
import com.green.secondproject.student.model.*;
import com.green.secondproject.teacher.model.*;
import com.green.secondproject.teacher.subject.model.*;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@Tag(name = "선생님")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService service;
    private final StudentService stdService;
    private final UserRepository userRep;

    @GetMapping("/signed")
    @Operation(summary = "승인된 학생 리스트",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>" +
                    "출력값 : <br>(1)userId - 학생 PK값<br>(2)classId - 학급 PK값<br>" +
                    "(3)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)<br>(4)snm - 학생 이름<br>" +
                    "(5)birth - 생일<br>(6)phone - 연락처<br>(7)email - 이메일")
    public List<SelSignedStudentVo> selectSignedStudent(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.selSignedStudent(myuser);
    }


    @GetMapping("/unsigned")
    @Operation(summary = "승인 대기 명단",
            description = "출력값 : <br>(1)classId - 학급 PK값<br><br>" +
                    "(3)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)" +
                    "<br>(4)snm - 학생 이름<br>(5)birth - 생일<br>(6)phone - 연락처<br>(7)email - 이메일")
    public List<SelUnsignedStudentVo> selectUnsignedStudent(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.selUnsignedStudent(myuser);
    }





    @PatchMapping("/acpt-std")
    @Operation(summary = "학생 가입 승인",
            description = "요구값 : <br>(1)userId - 학생 PK값")
    public int acceptStudent(@RequestParam Long userId) {
        return service.acceptStudent(userId);
    }

//    @PatchMapping("/reject-student")
//    @Operation(summary = "학생 가입 거절")
//    public int rejectStudent(@RequestParam Long userId) {
//        return service.rejectStudent(userId);
//    }


    @PatchMapping("/can-std")
    @Operation(summary = "학생 가입 승인 취소",
            description = "요구값 : <br>(1)userId - 학생 PK값")
    public int cancelAcceptStd(@RequestParam Long userId) {
        return service.cancelAcceptStd(userId);
    }


    @PatchMapping("/mock")
    @Operation(summary = "모의고사 성적 수정",
            description = "요구값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도" +
                    "<br>(3)mon - 월<br>(4)standardScore - 표준점수<br>(5)rating - 등급<br>(6)percent - 백분위")
    public int updMockResult(@RequestBody UpdMockResultDto dto) {
        return service.updMockResult(dto);
    }


    @PatchMapping("/aca")
    @Operation(summary = "내신 성적 수정",
            description = "요구값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도" +
                    "<br>(3)semester - 학기<br>(4)mf - [0 = 중간/1 = 기말]<br>" +
                    "(5)score - 점수<br>(6)rating - 등급<br>(7)classRank - 반 석차<br>(8)wholeRank - 전교 석차")
    public int updAcaResult(@RequestBody UpdAcaResultDto dto) {
        return service.updAcaResult(dto);
    }


    @DeleteMapping("/mock")
    @Operation(summary = "모의고사 성적 삭제",
            description = "요구값 : <br>(1)resultId - 모의고사 성적 PK값<br><br>")
    public int delMockResult(@RequestParam Long resultId) {
        return service.delMockResult(resultId);
    }


    @DeleteMapping("/aca")
    @Operation(summary = "내신 성적 삭제",
            description = "요구값 : <br>(1)resultId - 내신 성적 PK값<br><br>")
    public int delAcaResult(@RequestParam Long resultId) {
        return service.delAcaRusult(resultId);
    }


    @GetMapping("/class-student")
    @Operation(summary = "반 학생총원")
    public int classStudent(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.classStudent(myuser);
    }


    @GetMapping("/apr-student")
    @Operation(summary = "승인 대기 인원(n명)")
    public int aprStudent(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.aprStudent(myuser);
    }


    @GetMapping("/aca-graph")
    @Operation(summary = "선생님 - 담당학급 최근성적내신 등급비율 출력", description = "출력값 : (1)cateNm - 과목계열명 <br>(2)rating - 등급<br>(3)percentage - 학생비율<br><br>")
    public TeacherGraphContainerVo teacherAcaGraph(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.teacherAcaGraph(myuser.getVanId());
    }

    @GetMapping("/mock-result")
    @Operation(summary = "선생님-학생별 모의고사성적관리 테이블(하단)", description = "요구값(필수) : <br>\" + \"(1)userId - 학생pk <br>요구값 : <br>" + "(1)year - 조회기준연도(yyyy) <br>" + "(2)mon - 조회기준월(1~12)<br><br>" +
            "<br><br>출력값 : <br>" + "(1)year - 연도<br>" + "(2)mon - 월<br>" +
            "(3)nm - 과목이름<br>" + "(4)cateNm - 과목계열이름 <br>" + "(5)standardScore - 표준점수<br> " + "(6)rating - 등급<br> (7)percent - 백분위 ※ 연도 - 월 - 과목계열 내림차순 / 수정완료※<br>")
    public List<StudentMockSumResultWithIdVo> selMockTestResultByDates(@RequestParam Long userId, @RequestParam(required = false) String year, @RequestParam(required = false) String mon) {
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(userId);
        dto.setYear(year);
        dto.setMon(mon);
        return service.selMockTestResultByDates(dto);
    }


    @GetMapping("/aca-result")
    @Operation(summary = "선생님-학생별 내신성적관리 테이블(하단)", description = "요구값(필수) : <br>\" + \"(1)userId - 학생pk <br>요구값(선택) : <br>" + "(1)year - 조회기준연도(yyyy) <br>(2)semester - 학기(1,2)<br>(3)midFinal - (1:중간,2:기말)<br><br>"
            + "※※학생 아이디 외 요구값들은 선택사항입니다. 요구값없이 조회시 전체조회※※<br><br>" +
            "출력값 : <br>" + "(1)year - 연도<br>(2)semester - 학기(1,2)<br>" +
            "(3)midFinal - 1:중간2:기말<br>" + "(4)cateNm - 계열이름 <br>" + "(5)nm - 과목명 <br>" + "(6)score - 원점수<br>" + "(7)rating - 등급<br> (8)classRank - 반석차<br>(9)classRank - 전교석차<br><br>※ 연도-학기-시험구분 최신순으로 / 수정완료※<br>")
    public List<StudentAcaResultWithIdVo> selAcaTestResultByDatesAndPeriod(@RequestParam Long userId, @RequestParam(required = false) String year, @RequestParam(required = false) Integer semester, @RequestParam(required = false) Integer midFinal) {
        StudentAcaResultsParam param = new StudentAcaResultsParam();
        param.setUserId(userId);
        param.setYear(year);
        param.setSemester(semester);
        param.setMidFinal(midFinal);
        return service.selAcaTestResultByDatesAndPeriodAndStudent(param);
    }

//==============================================3차 추가된부분 =====

    @GetMapping("/mock-graph/{userId}")
    @Operation(summary = "모의고사그래프-학생 별 올해 응시시험 성적",  description =
            "출력값 : <br>" + "(1)nm - 과목명<br>"+"(2)rating - 등급(1-9)<br>"
                    +"※국수영한 순서/수정완료※")
    public List<StudentTestSumGraphVo> getMockTestSumGraph(@PathVariable Long userId){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(userId);
        return stdService.getMockTestGraph(dto);
    }

    @GetMapping("/aca-graph/{userId}")
    @Operation(summary = "내신그래프- 학생별 올해 응시시험 성적",  description ="출력값 : <br>"+ "(1)date - (연도)-(학기) (중간/기말)<br> (2)nm - 과목계열이름<br>(3)rating - 등급<br>※수정완료※<br>")
    List<StudentTestSumGraphVo> getAcaTestGraph(@PathVariable Long userId){
        UserEntity user = userRep.findByUserId(userId);
        return stdService.getAcaTestGraph();
    }

    @GetMapping("/notice")
    @Operation(summary = "선생님 페이지 공지사항", description = """
            "imptList": 중요공지 리스트<br>
            "normalList": 일반공지 리스트<br>
            "noticeId": 공지사항 PK,<br>
            "imptYn": 중요(1) 일반(0),<br>
            "title": 제목,<br>
            "createdAt": 작성일,<br>
            "hits": 조회수 """)
    public NoticeTeacherListVo NoticeTeacher(){
        return service.NoticeTeacher();
    }


    //===============subject- > teacher ======================================

    @PostMapping("/subject/mock-ins")
    @Operation(summary = "모의고사 성적등록"
            , description = "subjectid - 과목 번호<br>" +
            "mon - 달<br>" +
            "standardscore - 표준점수"
            + "<br> rating - 등급"
            + "<br> percent - 백분율")
    int mockins(@RequestBody mockDto2 dto) {
        return service.mockins(dto);
    }

    @GetMapping("/subject/mock-graph")
    @Operation(summary = "6월 모의고사 성적조회",
            description = "nm - 과목이름<br>"+
                    "rating - 등급" +
                    "ratio - 등급별 인원 퍼센트(100%기준)")
    MockGraphVo mockgraph(@AuthenticationPrincipal MyUserDetails user) {
        return service.mockgraph(user);
    }

    @GetMapping("/subject/aca-result")
    @Operation(summary = "내신성적 출력")
    List<ResultAcaVo> selaca(@AuthenticationPrincipal MyUserDetails user, @RequestParam Long resultId) {
        ResultAcaDto dto = new ResultAcaDto();
        dto.setUserId(user.getUserId());
        dto.setResultId(resultId);
        return service.selaca(dto);
    }

    @GetMapping("/subject/mock-result")
    @Operation(summary = "모의고사 성적출력")
    List<ResultMockVo> selmock(@AuthenticationPrincipal MyUserDetails user, @RequestParam Long resultId) {

        ResultMockDto dto = new ResultMockDto();
        dto.setUserId(user.getUserId());
        dto.setResultId(resultId);
        return service.selmock(dto);
    }

    //====================성적자동 입력시 수정필요한 부분

    @PostMapping("/aca-result")
    @Operation(summary = "학생별 내신 성적 등록", description = """
                    userId - 학생 PK<br>
                    semester - 학기<br>
                    midFinal - 중간(1), 기말(2)<br><br>
                    subjectId - 과목 PK<br>
                    score - 점수
                    """)
    public List<AcaResultEntity> postAcaResult(@RequestBody AcaResultInsDto dto) {
        return service.saveAcaResult(dto);
    }

    @PatchMapping("/rank")
    @Operation(summary = "반, 전교 석차, 등급 계산", description = """
            semester : 학기<br>
            midFinal : 중간(1), 기말(2)
            """)
    public int calcRank(@RequestBody CalcClassRankParam p) {
        return service.calcRank(p);
    }

    @GetMapping("/student-list")
    @Operation(summary = "반 학생 목록")
    public List<StudentVo> getStudentList(@Parameter(description = "학생 이름") @RequestParam(required = false) String name) {
        return service.getStudentList(name);
    }
}


//    @GetMapping("/acaresult")
//    @Operation(summary = "학생 내신 성적 조회",
//            description = "요구값 : <br>(1)userId - 유저(학생) PK값" +
//                    "<br>(2)year - 조회기준연도(yyyy)" +
//                    "<br>(3)semester - 학기(1,2)<br>" +
//                    "(4)midFinal - (1:중간,2:기말)<br><br>" +
//                    "※※요구값들은 선택사항입니다(PK값 제외). 요구값없이 학생성적 조회시 전체조회※※<br><br>"+
//                    "출력값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도<br>(3)semester - 학기<br>" +
//                    "(4)cateId - 과목 계열 PK<br>(5)nm - 과목 계열명<br>(6)detailCateId - 세부과목 PK<br>(7)detailNm - 세부과목명<br>" +
//                    "(8)mf - 시험구분[1-중간/2-기말]<br>(9)score - 점수<br>(10)rating - 등급<br>" +
//                    "(11)cr - 반 석차<br>(12)wr - 전교석차")
//    public List<SelAcaResultVo> selectAcaResult(@RequestParam Long userId
//                                , @RequestParam(required = false)String year
//                                , @RequestParam(required = false)Integer semester
//                                , @RequestParam(required = false)String mf) {
//        SelAcaResultDto dto = new SelAcaResultDto();
//        dto.setUserId(userId);
//        dto.setYear(year);
//        dto.setSemester(semester);
//        dto.setMf(mf);
//        return service.selAcaResult(dto);
//    }


//    @GetMapping("/mockresult")
//    @Operation(summary = "학생 모의고사 성적 조회",
//            description = "출력값 : <br>(1)userId - 유저(학생) PK값" +
//                    "<br>(2)year - 조회기준연도(yyyy)" +
//                    "<br>(3)mon - 월" +
//                    "<br><br>※※요구값들은 선택사항입니다(PK값 제외). 요구값없이 학생성적 조회시 전체조회※※<br><br>"+
//                    "출력값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도<br>(3)mon - 월<br>" +
//                    "(4)cateId - 과목 계열 PK<br>(5)nm - 과목 계열명<br>(6)detailCateId - 세부과목 PK<br>" +
//                    "(7)detailNm - 세부 과목명<br>(8)sc - 표준 점수<br>(9)rating - 등급<br>(10)percent - 백분위")
//    public List<SelMockResultVo> selectMockResult(@RequestParam Long userId
//                                , @RequestParam(required = false)String year
//                                , @RequestParam(required = false)Integer mon) {
//        SelMockResultDto dto = new SelMockResultDto();
//        dto.setUserId(userId);
//        dto.setYear(year);
//        dto.setMon(mon);
//        return service.selMockResult(dto);
//    }

//    @GetMapping("/aca/{id}")
//    @Operation(summary = "선생님-학생별 내신성적관리 테이블(하단)",  description = "요구값(필수) : <br>\" + \"(1)userId - 학생pk <br>요구값(선택) : <br>" + "(1)year - 조회기준연도(yyyy) <br>(2)semester - 학기(1,2)<br>(3)midFinal - (1:중간,2:기말)<br><br>"
//            +"※※학생 아이디 외 요구값들은 선택사항입니다. 요구값없이 조회시 전체조회※※<br><br>"+
//            "출력값 : <br>" + "(1)year - 연도<br>(2)semester - 학기(1,2)<br>"+
//            "(3)midFinal - 1:중간2:기말<br>"+"(4)cateNm - 계열이름 <br>"+"(5)nm - 과목명 <br>"+"(6)score - 원점수<br>"+"(7)rating - 등급<br> (8)classRank - 반석차<br>(9)classRank - 전교석차<br><br>※ 연도-학기-시험구분 최신순으로 / 수정완료※<br>")
//    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(@PathVariable(value = "id") Long userId, @RequestParam(required = false) String year, @RequestParam(required = false) Integer semester, @RequestParam(required = false) Integer midFinal){
//        StudentAcaResultsParam param = new StudentAcaResultsParam();
//        param.setUserId(userId);
//        param.setYear(year);
//        param.setSemester(semester);
//        param.setMidFinal(midFinal);
//        return service.selAcaTestResultByDatesAndPeriodAndStudent(param);
//    }

//    @GetMapping("/mock/{id}")
//    @Operation(summary = "선생님-학생별 모의고사성적관리 테이블(하단)",  description = "요구값(필수) : <br>\" + \"(1)userId - 학생pk <br>요구값 : <br>" + "(1)year - 조회기준연도(yyyy) <br>"+"(2)mon - 조회기준월(1~12)<br><br>"+
//            "<br><br>출력값 : <br>" + "(1)year - 연도<br>"+"(2)mon - 월<br>"+
//            "(3)nm - 과목이름<br>"+"(4)cateNm - 과목계열이름 <br>"+"(5)standardScore - 표준점수<br> "+"(6)rating - 등급<br> (7)percent - 백분위 ※ 연도 - 월 - 과목계열 내림차순 / 수정완료※<br>")
//    public List<StudentMockSumResultVo> selMockTestResultByDates(@PathVariable(value = "id") Long userId,@RequestParam(required = false) String year, @RequestParam(required = false) String mon){
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(userId);
//        dto.setYear(year);
//        dto.setMon(mon);
//        return service.selMockTestResultByDates(dto);
//    }