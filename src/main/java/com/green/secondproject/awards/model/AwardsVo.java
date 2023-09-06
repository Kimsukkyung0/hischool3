package com.green.secondproject.awards.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AwardsVo {
    private Long awardId;
    private Long userId;
    private String nm;
    private String prize;
    private LocalDate awardedAt;
    private String awardedBy;
    private String participation;
}
