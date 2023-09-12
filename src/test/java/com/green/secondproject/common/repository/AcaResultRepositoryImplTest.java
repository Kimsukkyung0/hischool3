package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.jpa.QueryDslConfig;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import com.green.secondproject.teacher.model.TeacherGraphVo;
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
@ActiveProfiles("test")
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

    @Test
    void countStudentsNumByVanAndCate() {
        double num = aca.countStudentsNumByVanAndCate(1L, RoleType.STD, 1, 3L,2023,2,2);
        log.info("num : {}",num);
    }

    @Test
    void teacherAcaGraph() {
        List<TeacherGraphVo> tmpVoList = aca.teacherAcaGraph(1L,1L,2022,1,1);
        for(TeacherGraphVo vo : tmpVoList){
            log.info("vo : {}",vo);
        }
    }

    @Test
    void getLatestTest() {
        int[] date = aca.getLatestTest(1L, 3L);
        for (int d : date){
            log.info("d : {}", d);
        }
    }


}