package com.green.secondproject.notice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class NoticeClassVo {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int imptYn;
    private int hits;
    private Long userId;
}
