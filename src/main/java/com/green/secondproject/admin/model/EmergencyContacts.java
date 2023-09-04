package com.green.secondproject.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmergencyContacts {
    @Schema(example = "053-123-4567", description = "행정실 번호")
    private String admNum;
    @Schema(example = "053-123-4567", description = "교무실 번호")
    private String tcNum;
    @Schema(example = "053-123-4567", description = "교장실 번호")
    private String prcpNum;
    @Schema(example = "053-123-4567", description = "관리실 번호")
    private String mainNum;
    @Schema(example = "053-123-4567", description = "기계실 번호")
    private String machineNum;
    @Schema(example = "053-123-4567", description = "팩스 번호")
    private String faxNum;
}
