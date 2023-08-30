package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NoticeTeacherListVo {
    private List<NoticeTeacherVo> imptList;
    private List<NoticeTeacherVo> normalList;
}
