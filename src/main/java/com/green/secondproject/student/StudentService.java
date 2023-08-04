package com.green.secondproject.student;

import com.green.secondproject.student.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper mapper;

    public int delStudent(int userId) {
        StudentDelDto dto = new StudentDelDto();
        dto.setUserId(userId);
        return mapper.delStudent(dto);
    }

    public List<StudentMockResultVo> selMockTestResultByDates(StudentMockResultsParam param){
        return mapper.selMockTestResultByDates(param);
    }
    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(StudentAcaResultsParam param){
        return mapper.selAcaTestResultByDatesAndPeriod(param);
    };

    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(StudentSummaryParam param){
        return mapper.getHighestRatingsOfMockTest(param);
    }

    public List<StudentSummarySubjectVo> getLatestRatingsOfMockTest(StudentSummarySubjectDto dto){
        LocalDate now = LocalDate.now();
        dto.setYear(String.valueOf(now.getYear()));
        dto.setMon(String.valueOf(now.getMonthValue()));
        log.info("mon : {}" , now.getMonthValue());
        return mapper.getLatestRatingsOfMockTest(dto);
    }

//    public List<StudentSummaryContainerVo> getMockTestGraph(StudentSummaryParam param){
//        List<StudentMockSumGraphVo> tmp = mapper.getMockTestGraph(param);
//
//        List<StudentSummaryContainerVo> result=null;
//        for (StudentMockSumGraphVo vo : tmp) {
////            String date = String.format("%s%s",vo.getYear(),vo.getMon());
////
//            StudentMockSumGraphVo mockSumGraphVo = new StudentMockSumGraphVo();
//            mockSumGraphVo.setNm(vo.getNm());
//            mockSumGraphVo.setRating(vo.getRating());
//            tmp.add(mockSumGraphVo);
//        }
//
//
//        return result;
//    }

    public List<StudentSummaryContainerVo> getMockTestGraph(StudentSummarySubjectDto dto){

        List<StudentMockSumGraphVo> tmp = mapper.getMockTestGraph(dto);
        List<StudentSummarySubjectVo> sublist = null;
        List<StudentSummaryContainerVo> result = null; //일자 + 과목이름

                loop:
                for (int i = 0; i < tmp.size(); i++) {
                 StudentSummarySubjectVo vo = new StudentSummarySubjectVo(tmp.get(i).getNm(),tmp.get(i).getRating());
                 log.info("vo : {}",vo);
                 sublist.add(vo);
                 if(tmp.get(i).getDate() != tmp.get(i+1).getDate())////검사
                     continue loop;
                }//시험응시월 수로 studentsummarysubjectVo 가 만들어짐

        for (int i = 0; i < sublist.size(); i++) {
            //날짜형식 변경 후 데이터 보내주기
            StringBuffer sb = new StringBuffer(tmp.get(i).getDate());
            String tmpDate = sb.insert(3,"-").toString();
            StudentSummaryContainerVo tmpresult = new StudentSummaryContainerVo(tmpDate,sublist);
            result.add(tmpresult);
        }
        return result;
    }
}
