package com.green.secondproject.sign.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassVo {
    private String classNm;

    @JsonProperty("classNm")
    public String getClassNm() {
        return classNm;
    }

    @JsonProperty("CLASS_NM")
    public void setClassNm(String classNm) {
        this.classNm = classNm;
    }
}
