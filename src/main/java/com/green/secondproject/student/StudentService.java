package com.green.secondproject.student;

import com.green.secondproject.admin.model.NoticeTeacherListVo;
import com.green.secondproject.admin.model.NoticeTeacherVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.MockResultRepository;
import com.green.secondproject.common.repository.NoticeRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.student.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper mapper;
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final MockResultRepository mockResultRepository;

    public List<StudentMockSumResultWithIdVo> selMockTestResultByDates(StudentSummarySubjectDto dto) {
        return mockResultRepository.searchMockResult(dto);
        //return mapper.selMockTestResultByDates(dto);
    }

    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId) {
        return mapper.getHighestRatingsOfMockTest(userId);
    }

    public StudentSumContainerVo getLatestRatingsOfMockTest(StudentSummarySubjectDto dto) {
        LocalDate now = LocalDate.now();
        dto.setYear(String.valueOf(now.getYear()));
        dto.setMon(String.valueOf(now.getMonthValue()));
        try{
        List<StudentTestSumGraphVo> sub = mapper.getLatestRatingsOfMockTest(dto);
        List<StudentSummarySubjectVo> result = new ArrayList<StudentSummarySubjectVo>();

        for (StudentTestSumGraphVo vo : sub) {
            StudentSummarySubjectVo tmpVo = new StudentSummarySubjectVo(vo.getNm(),vo.getRating());
            result.add(tmpVo);
        }

        StringBuffer sb = new StringBuffer(sub.get(0).getDate());

        return new StudentSumContainerVo(sb.insert(4,'-').toString(),result);}
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
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

    public List<StudentTestSumGraphVo> getMockTestGraph(StudentSummarySubjectDto dto) {
        //dto로 날짜 전달
        LocalDate now = LocalDate.now();
        dto.setYear(String.valueOf(now.getYear()));
        dto.setMon(String.valueOf(now.getMonthValue()));

        try {
            List<StudentTestSumGraphVo> sub = mapper.getMockTestGraph(dto);
            List<StudentTestSumGraphVo> result = new ArrayList<StudentTestSumGraphVo>();
            for (StudentTestSumGraphVo vo : sub) {
                log.info("vo : {}", vo);
                StringBuffer sb = new StringBuffer(vo.getDate());
                vo.setDate(sb.insert(4, "-").toString());
                result.add(vo);
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
        }


//
//        //controller로 원하는 값 전달
//
//        //날자, 이름, 등급만 담은 리스트
//        List<StudentMockSumGraphVo> tmp = mapper.getMockTestGraph(dto);
//
//        List<StudentSummarySubjectVo> sublist = new ArrayList<StudentSummarySubjectVo>();
//        List<StudentSummaryContainerVo> result = new ArrayList<StudentSummaryContainerVo>(); //일자 + 과목이름
//
//                loop:
//                for (int i = 0; i < tmp.size(); i++) {
//                 StudentSummarySubjectVo vo = new StudentSummarySubjectVo(tmp.get(i).getNm(),tmp.get(i).getRating());
//                 log.info("vo : {}",vo);
//                 sublist.add(vo);
//                 if(tmp.get(i).getDate() != tmp.get(i+1).getDate())////검사
//                     continue loop;
//                }//시험응시월 수로 studentsummarysubjectVo 가 만들어짐
//
//        for (int i = 0; i < sublist.size(); i++) {
//            //날짜형식 변경 후 데이터 보내주기
//            StringBuffer sb = new StringBuffer(tmp.get(i).getDate());
//            String tmpDate = sb.insert(3,"-").toString();
//            StudentSummaryContainerVo tmpresult = new StudentSummaryContainerVo(tmpDate,sublist);
//            result.add(tmpresult);
//        }


    //내신 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(StudentAcaResultsParam param) {
        return mapper.selAcaTestResultByDatesAndPeriod(param);
    }

    public List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest(StudentSummarySubjectDto dto) {
        LocalDate now = LocalDate.now();
        dto.setYear(String.valueOf(now.getYear()));
        return mapper.getHighestRatingsOfAcaTest(dto);
    }

    public StudentSumContainerVo getLatestRatingsOfAcaTest(Long userId) {
        //결과값 : List<2023 2-2 국수영한 등급>
        try{
        List<StudentTestSumGraphVo> subList = mapper.getLatestRatingsOfAcaTest(userId);

        List<StudentSummarySubjectVo> tmp = new ArrayList<StudentSummarySubjectVo>();

        for(StudentTestSumGraphVo vo : subList){
            StudentSummarySubjectVo tmpVo = new StudentSummarySubjectVo(vo.getNm(),vo.getRating());
            tmp.add(tmpVo);
        }

         String date = getMidFinalFormOfDate(subList.get(0).getDate());

         return new StudentSumContainerVo(date,tmp);}
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentTestSumGraphVo> getAcaTestGraph(StudentSummarySubjectDto dto){
        LocalDate now = LocalDate.now();
        dto.setYear(String.valueOf(now.getYear()));

        //mapper로 부터 가져온 리스트
        try {
            List<StudentTestSumGraphVo> subList = mapper.getAcaTestGraph(dto);
            log.info("subList : {}", subList);
            List<StudentTestSumGraphVo> result = new ArrayList<StudentTestSumGraphVo>();

                //for문에서 날짜수정작업
                for (StudentTestSumGraphVo vo : subList) {
                    StudentTestSumGraphVo subResult = new StudentTestSumGraphVo();
                    subResult.setDate(getMidFinalFormOfDate(vo.getDate()));
                    subResult.setNm(vo.getNm());
                    subResult.setRating(vo.getRating());
                    result.add(subResult);
                }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getMidFinalFormOfDate(String date){

        StringBuffer sb = new StringBuffer(date);
        String dateStrTmp = sb.insert(4,'-').toString();
        log.info("dateStrTmp : {}",dateStrTmp);

        int len = dateStrTmp.length()-1;
        if(dateStrTmp.endsWith("1")){
            //중간고사
            dateStrTmp = dateStrTmp.substring(0,len);
            dateStrTmp += " 중간";
        }
        else if(dateStrTmp.endsWith("2")){
            //기말일때
//            StringUtils.removeEnd(dateStrTmp, "2");
            dateStrTmp = dateStrTmp.substring(0,len);
            dateStrTmp += " 기말";
        }
        return dateStrTmp;
    }

    public NoticeTeacherListVo NoticeTeacher(){
        MyUserDetails userDetails = facade.getLoginUser();  // 현재 로그인한 사용자의 정보

        UserEntity currentUser = userRepository.findById(userDetails.getUserId()).orElse(null);  // 사용자의 상세 정보를 가져옴

        if (currentUser == null) {
            throw new RuntimeException("로그인한 사용자 정보가 없습니다.");
        }
        Long currentVanId = currentUser.getVanEntity().getVanId();  // 로그인한 사용자의 vanId 값을 가져옴

        List<NoticeEntity> imptList = noticeRepository.findImportantNoticesByVanId(currentVanId);
        List<NoticeEntity> normalList = noticeRepository.findTopByImptYnAndSchoolEntityOrderByNoticeIdDesc(currentVanId);

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
}