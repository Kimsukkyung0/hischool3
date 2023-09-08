package com.green.secondproject.teacher.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudentVo {
    private final Long userId;
    private final String name;
    private final LocalDate birth;

    @QueryProjection
    public StudentVo(Long userId, String name, LocalDate birth) {
        this.userId = userId;
        this.name = name;
        this.birth = birth;
    }
}
