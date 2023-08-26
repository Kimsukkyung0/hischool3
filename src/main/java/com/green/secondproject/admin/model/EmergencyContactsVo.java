package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EmergencyContactsVo {
    private ContactNumVo contactNum;
    private List<GradeManagerVo> gradeManagerList;
}
