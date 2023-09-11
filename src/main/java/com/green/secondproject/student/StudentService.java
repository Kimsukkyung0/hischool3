package com.green.secondproject.student;

import com.green.secondproject.admin.model.NoticeTeacherListVo;
import com.green.secondproject.admin.model.NoticeTeacherVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.AcaResultRepository;
import com.green.secondproject.common.repository.MockResultRepository;
import com.green.secondproject.common.repository.NoticeRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.student.model.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final MockResultRepository mockResultRepository;
    private final AcaResultRepository acaResultRepository;


    //내신 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<StudentAcaResultWithIdVo> selAcaTestResultByDatesAndPeriod(StudentAcaResultsParam param) {
        return acaResultRepository.searchAcaResult(param);
        // return mapper.selAcaTestResultByDatesAndPeriod(param);
    }

    public StudentSumContainerVo getLatestRatingsOfAcaTest() {
//        결과값 : List<2023 2-2 국수영한 등급>
        UserEntity userEnti = userRepository.findByUserId(facade.getLoginUserPk());
        List<StudentTestSumGraphVo> subList =
                acaResultRepository.getLatestRatingsOfAcaTest(userEnti);

        List<StudentSummarySubjectVo> tmp = new ArrayList<>();

        for (StudentTestSumGraphVo vo : subList) {
            StudentSummarySubjectVo tmpVo = new StudentSummarySubjectVo(vo.getNm(), vo.getRating());
            tmp.add(tmpVo);
        }

        String date = getMidFinalFormOfDate(acaResultRepository.getLatestTest(userEnti));

        return new StudentSumContainerVo(date, tmp);
    }


    public String getMidFinalFormOfDateByString(String date) {

        StringBuffer sb = new StringBuffer(date);
        String dateStrTmp = sb.insert(4, '-').toString();

        int len = dateStrTmp.length() - 1;
        if (dateStrTmp.endsWith("1")) {

            //중간
            dateStrTmp = dateStrTmp.substring(0, len);
            dateStrTmp += " 중간";
        } else if (dateStrTmp.endsWith("2")) {

            //기말
            dateStrTmp = dateStrTmp.substring(0, len);
            dateStrTmp += " 기말";
        }

        return dateStrTmp.substring(2);
    }

    public String getMidFinalFormOfDate(int[] date) {

        StringBuffer sb = new StringBuffer();
        for (int d : date) {
            sb.append(d);
        }
        String dateStrTmp = sb.insert(4, '-').toString();
        dateStrTmp.replaceAll(",", "");
        log.info("dateStrTmp : {}", dateStrTmp);

        int len = dateStrTmp.length() - 1;
        if (dateStrTmp.endsWith("1")) {

            //중간
            dateStrTmp = dateStrTmp.substring(0, len);
            dateStrTmp += " 중간";
        } else if (dateStrTmp.endsWith("2")) {

            //기말일때
            dateStrTmp = dateStrTmp.substring(0, len);
            dateStrTmp += " 기말";
        }

        return dateStrTmp.substring(2);
    }

    public NoticeTeacherListVo NoticeTeacher() {
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

    //3차 JPA 적용 부분//////////////////////////////////////////////3차 JPA 적용 부분

    public List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest() {
        UserEntity userEnti = userRepository.findByUserId(facade.getLoginUserPk());
//        mapper.getHighestRatingsOfAcaTest(userEnti);
        return acaResultRepository.getHighestRatingOfAcaTest(userEnti);
    }

    public StudentSumContainerVo getLatestRatingsOfMockTest() {
        UserEntity userEntity = userRepository.findByUserId(facade.getLoginUserPk());
        try {
            List<StudentTestSumGraphVo> sub = mockResultRepository.getLatestRatingsOfMockTest(userEntity);
            log.info("sub : {}", sub);
            List<StudentSummarySubjectVo> result = new ArrayList<>();

            for (StudentTestSumGraphVo vo : sub) {
                StudentSummarySubjectVo tmpVo = new StudentSummarySubjectVo(vo.getNm(), vo.getRating());
                result.add(tmpVo);
                log.info("tmpVo : {}", tmpVo);
            }
            String[] st = mockResultRepository.findLatestMock(userEntity);
            StringBuilder sb = new StringBuilder();
            for (String s : st) {
                sb.append(s);
            }
            return new StudentSumContainerVo(sb.insert(4, '-').toString(), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentTestSumGraphVo> getAcaTestGraph(Long userId) {
        UserEntity userEnti = userRepository.findByUserId(userId);

        try {
            List<StudentTestSumGraphVo> subList = acaResultRepository.getAcaTestGraph(userEnti);
            log.info("subList : {}", subList);
            List<StudentTestSumGraphVo> result = new ArrayList<>();

            //for문에서 날짜수정작업
            for (StudentTestSumGraphVo vo : subList) {
                StudentTestSumGraphVo subResult = new StudentTestSumGraphVo();
                subResult.setDate(getMidFinalFormOfDateByString(vo.getDate()));
                subResult.setNm(vo.getNm());
                subResult.setRating(vo.getRating());
                result.add(subResult);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentMockSumResultWithIdVo> selMockTestResultByDates(StudentSummarySubjectDto dto) {
        return mockResultRepository.searchMockResult(dto);
    }

    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId) {
        return mockResultRepository.getHighestRatingsOfMockTest(userId);
    }


    public List<StudentTestSumGraphVo> getMockTestGraph(Long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        try {
            List<StudentTestSumGraphVo> sub = mockResultRepository.getMockTestGraph(user);
            List<StudentTestSumGraphVo> result = new ArrayList<>();
            for (StudentTestSumGraphVo vo : sub) {
                log.info("vo : {}", vo);
                StringBuffer sb = new StringBuffer(vo.getDate());
                vo.setDate(sb.insert(4, "-").toString());
                result.add(vo);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void downloadMock(HttpServletResponse res, StudentSummarySubjectDto dto) throws IOException {
        dto.setUserId(facade.getLoginUserPk());
        List<StudentMockSumResultWithIdVo> resultList = mockResultRepository.searchMockResult(dto);
        if (resultList.isEmpty()) {
            throw new RuntimeException("성적 데이터가 없습니다.");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MockResult"); // 엑셀 sheet 이름
        sheet.setDefaultColumnWidth(10); // 디폴트 너비 설정

        /*
          header font style
         */
        XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
        headerXSSFFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));

        /*
          header cell style
         */
        XSSFCellStyle headerXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        headerXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        headerXssfCellStyle.setBorderRight(BorderStyle.THIN);
        headerXssfCellStyle.setBorderTop(BorderStyle.THIN);
        headerXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        // 배경 설정
        headerXssfCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 34, (byte) 37, (byte) 41}));
        headerXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerXssfCellStyle.setFont(headerXSSFFont);

        /*
          body cell style
         */
        XSSFCellStyle bodyXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        bodyXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderRight(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderTop(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        /*
          header data
         */
        int rowCount = 0; // 데이터가 저장될 행
        String[] headerNames = new String[]{"연도", "월", "과목 계열", "과목명", "표준점수", "등급", "백분위"};

        Row headerRow;
        Cell headerCell;

        headerRow = sheet.createRow(rowCount++);
        for (int i = 0; i < headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]); // 데이터 추가
            headerCell.setCellStyle(headerXssfCellStyle); // 스타일 추가
        }

        Row bodyRow;
        Cell bodyCell;

        for (StudentMockSumResultWithIdVo result : resultList) {
            bodyRow = sheet.createRow(rowCount++);

            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(result.getYear());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(result.getMon());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(result.getCateName());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(result.getNm());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(result.getStandardScore());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(5);
            bodyCell.setCellValue(result.getRating());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(6);
            bodyCell.setCellValue(result.getPercent());
            bodyCell.setCellStyle(bodyXssfCellStyle);
        }
        /*
          download
         */
        String fileName = "mock_result_download";

        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    public void downloadAca(HttpServletResponse res, StudentAcaResultsParam p) throws IOException {
        MyUserDetails userDetails = facade.getLoginUser();
        p.setUserId(userDetails.getUserId());
        p.setVanId(userDetails.getVanId());
        p.setGrade(userDetails.getGrade());
        p.setSchoolId(userDetails.getSchoolId());

        List<StudentAcaResultWithIdVo> resultList = acaResultRepository.searchAcaResult(p);

        if (resultList.isEmpty()) {
            throw new RuntimeException("성적 데이터가 없습니다.");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("AcaResult"); // 엑셀 sheet 이름
        sheet.setDefaultColumnWidth(10); // 디폴트 너비 설정

        /*
          header font style
         */
        XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
        headerXSSFFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));

        /*
          header cell style
         */
        XSSFCellStyle headerXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        headerXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        headerXssfCellStyle.setBorderRight(BorderStyle.THIN);
        headerXssfCellStyle.setBorderTop(BorderStyle.THIN);
        headerXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        // 배경 설정
        headerXssfCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 34, (byte) 37, (byte) 41}));
        headerXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerXssfCellStyle.setFont(headerXSSFFont);

        /*
          body cell style
         */
        XSSFCellStyle bodyXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        bodyXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderRight(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderTop(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        /*
          header data
         */
        int rowCount = 0; // 데이터가 저장될 행
        String[] headerNames = new String[]{"연도", "학기", "시험 구분", "과목 계열", "과목명", "점수", "등급", "반석차", "전교석차"};

        Row headerRow;
        Cell headerCell;

        headerRow = sheet.createRow(rowCount++);
        for (int i = 0; i < headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]); // 데이터 추가
            headerCell.setCellStyle(headerXssfCellStyle); // 스타일 추가
        }

        Row bodyRow;
        Cell bodyCell;

        for (StudentAcaResultWithIdVo result : resultList) {
            bodyRow = sheet.createRow(rowCount++);

            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(result.getYear());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(result.getSemester());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(result.getMidFinal() == 1 ? "중간" : "기말");
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(result.getCateName());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(result.getNm());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(5);
            bodyCell.setCellValue(result.getScore());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(6);
            bodyCell.setCellValue(result.getRating());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(7);
            bodyCell.setCellValue(result.getClassRank() + "/" + result.getClassCnt());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(8);
            bodyCell.setCellValue(result.getWholeRank() + "/" + result.getWholeCnt());
            bodyCell.setCellStyle(bodyXssfCellStyle);
        }
        /*
          download
         */
        String fileName = "aca_result_download";

        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();
    }


}