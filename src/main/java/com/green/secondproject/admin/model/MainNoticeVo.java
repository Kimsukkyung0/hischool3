package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MainNoticeVo {
    private Long noticeId;
    private int imptYn;
    private String title;
    private LocalDate createdAt;
    private int hits;
}
