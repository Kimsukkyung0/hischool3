package com.green.secondproject.admin.model;


import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
public class NoticeTeacherVo {
    private Long noticeId;
    private int imptYn;
    private String title;
    private LocalDate createdAt;
    private int hits;
}
