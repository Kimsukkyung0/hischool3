package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.jpa.QueryDslConfig;
import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QueryDslConfig.class,MyGradeGraphUtils.class, AcaResultEntity.class})
class AcaResultRepositoryImplTest {
    @Autowired
    AcaResultRepository aca;
    @Autowired
    UserRepository user;

//    @Autowired
//    MyGradeGraphUtils utils;


    @Test
    void findAllByUserEntityTest() {
        List<StudentTestSumGraphVo> subList = aca.getLatestRatingsOfAcaTest(user.findByUserId(5L));
        for(StudentTestSumGraphVo vo : subList){
            log.info("vo : {}", vo);
        }
    }

    @Test
    void findLatestTest(){
//        user.findById(5L).get();
        UserEntity userEntity = user.findByUserId(5L);
        int[] latestTest = aca.getLatestTest(userEntity);
        log.info("latestTest : {}",latestTest);
    }

    @Test
    void findUserById(){
        UserEntity userEntity = user.findByUserId(5L);
        log.info("userEntity : {}",userEntity);
    }

}