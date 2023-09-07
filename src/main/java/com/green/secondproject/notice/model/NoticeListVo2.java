package com.green.secondproject.notice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NoticeListVo2 {
    private List<NoticeTotalVo> list;
    private Long total;
    private int totalPage;
    private Long searchPage;
    private Long searchTotal;

}
