package com.green.secondproject.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class NoticePatchDto {
    private Long noticeId;
    private String title;
    private String content;
}
