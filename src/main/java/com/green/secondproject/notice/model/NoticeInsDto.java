package com.green.secondproject.notice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeInsDto {
    private String title;
    private String content;
    private int hits;
    private int imptyn;
    private LocalDateTime createdAt;
}
