package com.green.secondproject.admin.sign.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatusVo {
    private int tcNum;
    private int tcWaitingNum;
    private int stdNum;
}
