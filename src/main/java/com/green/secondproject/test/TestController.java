package com.green.secondproject.test;

import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService service;

//    @PostMapping
//    public void downloadByList(HttpServletResponse res, @RequestBody List<MockResultDto> resultList) throws IOException {
//        service.downloadByList(res, resultList);
//    }

    @GetMapping("/mock-result")
    public void downloadMock(HttpServletResponse res, @ParameterObject StudentSummarySubjectDto dto) throws IOException {
        service.downloadMock(res, dto);
    }

    @GetMapping("/aca-result")
    public void downloadAca(HttpServletResponse res, @ParameterObject StudentAcaResultsParam p) throws IOException {
        service.downloadAca(res, p);
    }
}
