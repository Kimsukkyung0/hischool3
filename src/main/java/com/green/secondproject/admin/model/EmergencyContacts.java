package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmergencyContacts {
    private String admNum;
    private String tcNum;
    private String prcpNum;
    private String mainNum;
    private String machineNum;
    private String faxNum;
}
