package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Setter;

@Setter
public class TeacherStatUpdDto {

    private Long userId;
    private EnrollState enrollState;
    private String year;
    private int grade;
    private int classNum;

    public Long getUserId() {
        return userId;
    }
    public EnrollState getEnrollState() {
        return enrollState;
    }
    public int getClassNum() {
        return classNum;
    }
    public int getGrade() {
        return grade;
    }
    public String getYear(){return year;}

}
