package com.green.secondproject.notice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NoticeListVo {
    private List<NoticeTotalVo> list;
    private Long total;
    private int totalPage;

}
