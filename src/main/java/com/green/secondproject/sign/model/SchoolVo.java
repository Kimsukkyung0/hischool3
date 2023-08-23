package com.green.secondproject.sign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class SchoolVo {
    private String nm;
    private String type;
    private String code;

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("SD_SCHUL_CODE")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("nm")
    public String getNm() {
        return nm;
    }

    @JsonProperty("SCHUL_NM")
    public void setNm(String nm) {
        this.nm = nm;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("HS_SC_NM")
    public void setType(String type) {
        this.type = type;
    }
}
