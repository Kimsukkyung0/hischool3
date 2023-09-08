package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.jpa.QueryDslConfig;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QueryDslConfig.class, MyGradeGraphUtils.class})
class MockResultRepositoryImplTest {
    @Autowired
    UserRepository user;
    @Autowired
    MockResultRepository mock;
    @Autowired
    MyGradeGraphUtils utils;

    @Test
    void findLatestMock() {
        UserEntity userEntity = user.findByUserId(1L);
        String[] latest = mock.findLatestMock(userEntity);
        log.info("latest : {}", latest[0]);
        log.info("latest : {}", latest[1]);
    }

    @Test
    void getLatestRatingsOfMockTest(){
        UserEntity userEntity = user.findByUserId(1L);
//        String[] latest = mock.findLatestMock(userEntity);
        List<StudentTestSumGraphVo> list = mock.getLatestRatingsOfMockTest(userEntity);

    }

    @Test
    void getCateIdTest(){
        Long[] cateIds = utils.getCateIdForMockTest();
        for (Long c:cateIds) {
            log.info("cateIds : {}",c);
        }

    }
}