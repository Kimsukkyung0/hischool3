package com.green.secondproject.utils;

import com.green.secondproject.teacher.model.TeacherGraphVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class
MyGradeGraphUtils {
    private final Long[] cateIdForAca = {1L,3L,6L,7L};

    private final Long[] cateIdForMockTest = {2L,4L,5L,8L};

    public Long[] getCateIdForAca() {
        return cateIdForAca;
    }

    public Long[] getCateIdForMockTest() {
        return cateIdForMockTest;
    }

    public static final int RATING_NUM = 9;
    public static final String[] cateNm = {"국어","수학","영어","한국사"};



    public static List<List<TeacherGraphVo>> teacherGraphListAtb(){
        List<List<TeacherGraphVo>> result = new ArrayList<>();

        for (int i = 0; i < cateNm.length; i++) {
            List<TeacherGraphVo> subList = new ArrayList<>();
            for (int j = 1; j <= RATING_NUM; j++) {
               subList.add(TeacherGraphVo.builder()
                       .cateNm(cateNm[i])
                       .rating(j)
                       .percentage(0)
                       .build());
            }
            result.add(subList);
        }

        log.info("result : {}",result);
        return result;
    }
}
