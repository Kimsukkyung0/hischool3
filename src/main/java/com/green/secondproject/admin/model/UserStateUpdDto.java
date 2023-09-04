package com.green.secondproject.admin.model;

import com.green.secondproject.common.entity.VanEntity;
import lombok.Data;
import lombok.Setter;

@Setter
public class UserStateUpdDto {
    private long userId;
    private long vanId;
    private String year;
    private int grade;
    private int classNum;


    public long getUserId() {
        return userId;
    }
    public long getVanId() {
        return vanId;
    }
    public String getYear() {
        return year;
    }
    public int getGrade() {
        return grade;
    }
    public int getClassNum() {
        return classNum;
    }
}
