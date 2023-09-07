package com.green.secondproject.common.repository;

import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcaResultRepositoryImplTest {
    @Autowired
    AcaResultRepository aca;
    @Autowired
    UserRepository user;


    @Test
    void findLatestTest() {

        List<StudentTestSumGraphVo> subList = aca.findAllByUserEntity(user.findByUserId(3L));
        for(StudentTestSumGraphVo vo : subList){
            log.info("subList : {}", subList);
        }
    }
}