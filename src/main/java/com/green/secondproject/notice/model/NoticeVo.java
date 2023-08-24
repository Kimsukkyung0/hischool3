package com.green.secondproject.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class NoticeVo {
    private Long noticeId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private int hits;
}
