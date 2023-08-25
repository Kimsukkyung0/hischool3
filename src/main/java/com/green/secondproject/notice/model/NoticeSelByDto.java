package com.green.secondproject.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSelByDto {
    private Long noticeId;
    private String title;
    private String content;
}
