package com.green.secondproject;

import com.green.secondproject.result.AcaResultMapper;
import com.green.secondproject.result.model.CalcClassRankParam;
import com.green.secondproject.result.model.CalcWholeRankParam;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class MapperTest {
    @Autowired
    private AcaResultMapper mapper;

    @Test
    void 내신_성적_계산() {
        long vanId = 89L;
        long schoolId = 3L;
        String grade = "3";

        int semester = 1;
        int midFinal = 1;
        String year = "2021";

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 1, 3 -> midFinal = 2;
                case 2 -> { semester = 2; midFinal = 1; }
            }
            mapper.calcClassRank(CalcClassRankParam.builder()
                    .vanId(vanId)
                    .year(year)
                    .semester(semester)
                    .midFinal(midFinal)
                    .build());

            mapper.calcWholeRankAndRating(CalcWholeRankParam.builder()
                    .semester(semester)
                    .midFinal(midFinal)
                    .year(year)
                    .schoolId(schoolId)
                    .grade(grade)
                    .build());
        }
    }
}