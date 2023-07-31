package com.green.secondproject.mealmenutable.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MealTableVo {
    private String date;//일자
    private String lunchOrDinner;//중식 or석식
    private String menuOftheDay;//메뉴이름리스트


    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("MLSV_YMD")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("lunchOrDinner")
    public String getLunchOrDinner() {
        return lunchOrDinner;
    }

    @JsonProperty("MMEAL_SC_NM")
    public void setLunchOrDinner(String lunchOrDinner) {
        this.lunchOrDinner = lunchOrDinner;
    }


    @JsonProperty("menuOftheDay")
    public String getMenuOftheDay() {
        return menuOftheDay;
    }

    @JsonProperty("DDISH_NM")
    public void setMenuOftheDay(String menuOftheDay) {
        this.menuOftheDay = menuOftheDay;
    }


//    private String MLSV_YMD;//이름 date
//    private String DDISH_NM;//메뉴이름 menuOftheDay
//    private String MMEAL_SC_NM;//중식 or석식 LunchOfDinner

}
