package com.green.secondproject.notice;

import com.green.secondproject.notice.model.NoticeSelDto;
import com.green.secondproject.notice.model.NoticeVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ToString
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService service;

    @GetMapping
    @Operation(summary = "공지사항 리스트")

    List<NoticeVo> noticeList(){
        return service.noticeList();
    }
}
