package com.green.secondproject.student;

import com.green.secondproject.student.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentMockSumResultVo> selMockTestResultByDates(StudentSummarySubjectDto dto);
    List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId);
    List<StudentTestSumGraphVo> getLatestRatingsOfMockTest(StudentSummarySubjectDto dto);
    List<StudentTestSumGraphVo> getMockTestGraph(StudentSummarySubjectDto dto);

    List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(StudentAcaResultsParam param);
    List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest(StudentSummarySubjectDto dto);
    List<StudentTestSumGraphVo> getLatestRatingsOfAcaTest(Long userId);
    List<StudentTestSumGraphVo> getAcaTestGraph(StudentSummarySubjectDto dto);

}
