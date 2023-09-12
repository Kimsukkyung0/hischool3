package com.green.secondproject;

import com.green.secondproject.result.AcaResultMapper;
import com.green.secondproject.result.model.CalcClassRankParam;
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
        mapper.calcClassRank(CalcClassRankParam.builder()
                .vanId(89L)
                .year("2021")
                .semester(1)
                .midFinal(1)
                .build());
    }
}