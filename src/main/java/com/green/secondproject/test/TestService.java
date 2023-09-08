package com.green.secondproject.test;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.repository.AcaResultRepository;
import com.green.secondproject.common.repository.MockResultRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.student.model.StudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentMockSumResultWithIdVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final MockResultRepository rep;
    private final AcaResultRepository acaResultRepository;
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;

//    public void downloadByList(HttpServletResponse res, List<MockResultDto> resultList) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("MockResult"); // 엑셀 sheet 이름
//        sheet.setDefaultColumnWidth(10); // 디폴트 너비 설정
//
//        /*
//          header font style
//         */
//        XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
//        headerXSSFFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));
//
//        /*
//          header cell style
//         */
//        XSSFCellStyle headerXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();
//
//        // 테두리 설정
//        headerXssfCellStyle.setBorderLeft(BorderStyle.THIN);
//        headerXssfCellStyle.setBorderRight(BorderStyle.THIN);
//        headerXssfCellStyle.setBorderTop(BorderStyle.THIN);
//        headerXssfCellStyle.setBorderBottom(BorderStyle.THIN);
//
//        // 배경 설정
//        headerXssfCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 34, (byte) 37, (byte) 41}));
//        headerXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        headerXssfCellStyle.setFont(headerXSSFFont);
//
//        /*
//          body cell style
//         */
//        XSSFCellStyle bodyXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();
//
//        // 테두리 설정
//        bodyXssfCellStyle.setBorderLeft(BorderStyle.THIN);
//        bodyXssfCellStyle.setBorderRight(BorderStyle.THIN);
//        bodyXssfCellStyle.setBorderTop(BorderStyle.THIN);
//        bodyXssfCellStyle.setBorderBottom(BorderStyle.THIN);
//
//        /*
//          header data
//         */
//        int rowCount = 0; // 데이터가 저장될 행
//        String[] headerNames = new String[]{"연도", "월", "과목 계열", "과목명", "표준점수", "등급", "백분위"};
//
//        Row headerRow;
//        Cell headerCell;
//
//        headerRow = sheet.createRow(rowCount++);
//        for(int i = 0; i < headerNames.length; i++) {
//            headerCell = headerRow.createCell(i);
//            headerCell.setCellValue(headerNames[i]); // 데이터 추가
//            headerCell.setCellStyle(headerXssfCellStyle); // 스타일 추가
//        }
//
//        /*
//          body data
//         */
////        String[][] bodyDatass = new String[][]{
////                {"첫번째 행 첫번째 데이터", "첫번째 행 두번째 데이터", "첫번째 행 세번째 데이터"},
////                {"두번째 행 첫번째 데이터", "두번째 행 두번째 데이터", "두번째 행 세번째 데이터"},
////                {"세번째 행 첫번째 데이터", "세번째 행 두번째 데이터", "세번째 행 세번째 데이터"},
////                {"네번째 행 첫번째 데이터", "네번째 행 두번째 데이터", "네번째 행 세번째 데이터"}
////        };
////
////        Row bodyRow;
////        Cell bodyCell = null;
////
////        for(String[] bodyDatas : bodyDatass) {
////            bodyRow = sheet.createRow(rowCount++);
////
////            for(int i = 0; i < bodyDatas.length; i++) {
////                bodyCell = bodyRow.createCell(i);
////                bodyCell.setCellValue(bodyDatas[i]); // 데이터 추가
////                bodyCell.setCellStyle(bodyXssfCellStyle); // 스타일 추가
////            }
////        }
//        Row bodyRow;
//        Cell bodyCell;
//
//        for (MockResultDto result : resultList) {
//            bodyRow = sheet.createRow(rowCount++);
//
//            bodyCell = bodyRow.createCell(0);
//            bodyCell.setCellValue(result.getYear());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//
//            bodyCell = bodyRow.createCell(1);
//            bodyCell.setCellValue(result.getMon());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//
//            bodyCell = bodyRow.createCell(2);
//            bodyCell.setCellValue(result.getCateName());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//
//            bodyCell = bodyRow.createCell(3);
//            bodyCell.setCellValue(result.getNm());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//
//            bodyCell = bodyRow.createCell(4);
//            bodyCell.setCellValue(result.getStandardScore());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//
//            bodyCell = bodyRow.createCell(5);
//            bodyCell.setCellValue(result.getRating());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//
//            bodyCell = bodyRow.createCell(6);
//            bodyCell.setCellValue(result.getPercent());
//            bodyCell.setCellStyle(bodyXssfCellStyle);
//        }
//        /*
//          download
//         */
//        String fileName = "mock_result_download";
//
//        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
//        ServletOutputStream servletOutputStream = res.getOutputStream();
//
//        workbook.write(servletOutputStream);
//        workbook.close();
//        servletOutputStream.flush();
//        servletOutputStream.close();
//    }
    public void downloadMock(HttpServletResponse res, StudentSummarySubjectDto dto) throws IOException {
        dto.setUserId(facade.getLoginUserPk());
        List<StudentMockSumResultWithIdVo> resultList = rep.searchMockResult(dto);
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
        for(int i = 0; i < headerNames.length; i++) {
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
        for(int i = 0; i < headerNames.length; i++) {
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
            bodyCell.setCellValue(result.getClassRank() + "/" + result.getVanCnt());
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
