package com.green.secondproject.volunteerwork.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VolWorkInsDto {
    private Long userId;
    private String grade;
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private String ctnt;
    private int hrs;

}
