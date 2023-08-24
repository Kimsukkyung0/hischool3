package com.green.secondproject.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class NoticeInsDto {
    private String title;
    private String content;
    private int imptyn;
}
