package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatusVo {
    private long tcNum;
    private long tcWaitingNum;
    private long stdNum;
}
