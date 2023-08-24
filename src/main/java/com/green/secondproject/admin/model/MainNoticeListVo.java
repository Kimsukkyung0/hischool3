package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class MainNoticeListVo {
    private List<MainNoticeVo> imptList;
    private List<MainNoticeVo> normalList;
}
