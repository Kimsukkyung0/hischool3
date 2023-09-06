package com.green.secondproject.volunteerwork.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class VolWorkVo {
    private Long volunteerId;
    private Long userId;
    private String grade;
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private String ctnt;
    private int hrs;
    private int totalHrs;
}
