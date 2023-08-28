package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EmergencyContactsVo {
    private String admNum;
    private String tcNum;
    private String prcpNum;
    private String mainNum;
    private String machineNum;
    private String faxNum;
}
