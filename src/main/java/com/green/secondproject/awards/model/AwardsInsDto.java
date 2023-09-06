package com.green.secondproject.awards.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AwardsInsDto {
    private Long userId;
    private String nm;
    private String prize;
    private LocalDate awardedAt;
    private String awardedBy;
    private String participation;

}
