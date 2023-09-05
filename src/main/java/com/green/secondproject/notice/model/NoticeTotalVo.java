package com.green.secondproject.notice.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeTotalVo {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int hits;
    private int imptYn;
    private Long schoolId;
}
