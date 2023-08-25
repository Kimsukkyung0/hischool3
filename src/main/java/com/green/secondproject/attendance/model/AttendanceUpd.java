package com.green.secondproject.attendance.model;

import lombok.Data;

@Data
public class AttendanceUpd {
    private Long attendId;
    private int lessonNum;
    private int diseaseAbsence;
    private int unauthAbsence;
    private int etcAbsence;
    private int diseaseLate;
    private int unauthLate;
    private int etcLate;
    private int diseaseEarly;
    private int unauthEarly;
    private int etcEarly;
    private int diseaseOut;
    private int unauthOut;
    private int etcOut;
    private String specialNote;
}
