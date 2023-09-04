package com.green.secondproject.notice;

import com.green.secondproject.admin.model.StudentClassVo;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.notice.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ToString
@Tag(name = "공지사항")
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService service;

    @GetMapping
    @Operation(summary = "공지사항 리스트"
    ,description = "")
    public List<NoticeVo> noticeList(){
        return service.noticeList();
    }

    @PostMapping
    @Operation(summary = "공지사항등록")
    public NoticeVo saveByNotice(@RequestBody NoticeInsDto dto){
        return service.saveByNotice(dto);
    }

    @GetMapping("/bynotice")
    @Operation(summary = "공지사항 하나 선택보기")
    public NoticeVo findBySchoolNotice(@RequestParam Long noticeId){
        return service.findBySchoolNotice(noticeId);
    }

    @PatchMapping
    @Transactional
    @Operation(summary = "글 수정")
    public NoticeVo2 no(@RequestBody NoticePatchDto dto){

        return service.upNotice(dto);
    }
    @DeleteMapping
    @Operation(summary = "글 삭제")
    public void del(@RequestParam Long noticeId){
        service.delNotice(noticeId);
    }

    @PatchMapping("/hits")
    @Operation(summary = "조회수 +1")
    public int upHits(Long noticeId) {
        return service.upHits(noticeId);
    }
//
//    @GetMapping("/search")
//    @Operation(summary = "검색기능")
//
//    public List<NoticeVo> searchNotice(@RequestParam(required = false) String search, int page) {
//        return service.searchNotice(search, page);
//    }

}
