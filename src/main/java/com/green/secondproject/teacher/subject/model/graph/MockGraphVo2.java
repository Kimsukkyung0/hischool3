package com.green.secondproject.teacher.subject.model.graph;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MockGraphVo2 {
    private String nm;  // 과목이름
    private Integer rating; // 등급
    private double ratio; // 퍼센트  1~9등급 인원 /현학생

}
