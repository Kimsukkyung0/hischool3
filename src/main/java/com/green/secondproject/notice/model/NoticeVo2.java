package com.green.secondproject.notice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeVo2 {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int hits;
    private int imptYn;
    private Long schoolId;
}
