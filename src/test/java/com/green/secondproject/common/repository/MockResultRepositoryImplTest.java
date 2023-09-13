package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.jpa.QueryDslConfig;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QueryDslConfig.class, MyGradeGraphUtils.class})
@Rollback(value = false)
@ActiveProfiles("test")
class MockResultRepositoryImplTest {
    @Autowired
    UserRepository user;
    @Autowired
    MockResultRepository mock;
    @Autowired
    MyGradeGraphUtils utils;
    @Autowired
    VanRepository van;

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

    @Test
    void getMockTestGraph() {
        UserEntity userEntity = user.findByUserId(1L);
        Optional<List<StudentTestSumGraphVo>> list = Optional.of(mock.getMockTestGraph(userEntity));
        if (list.isEmpty()) {
            log.info("list : " , list);
        }
        if(list.isPresent()) {
            for (StudentTestSumGraphVo vo : list.get()) {
                log.info("vo : {}", vo);
            }
        }
    }
//    고1 (3,6,9)
//4,5,7,9,195,194
//
//    고2 문과
//4,5,7,9
//
//        24,20,28,18,22,14,26,16
//
//    고2 이과
//    4,5,7,9
//
//            30,34,36,32
//
//    고3 문과
//4,5,7,9
//        14,16,18,20,22,24,26,28 중 2
//
//            189
//
//    고3 이과
//    4,5,7,9
//            190,191,192,193
    @Test
    void 모의고사_성적_등록_규진 () {
        final long schoolId = 3L; // 청구고(3), 계성고(70)
        final String grade = "3";
        final String year = "2022";
        final String mon = "9";
        long subjectId = 0L;


        //    고2 문과
//4,5,7,9   //
//        24,20,28,18,22,14,26,16
//    고2 이과
//    4,5,7,9  //            30,34,36,32
//
//        List<VanEntity> vanList = van.findAllBySchoolEntityAndGradeAndYear(
//                SchoolEntity.builder().schoolId(schoolId).build(), grade, "2023");

        VanEntity vanList = van.findBySchoolEntityAndYearAndGradeAndClassNum(
                SchoolEntity.builder().schoolId(schoolId).build(),"2023", grade,"10");
        List<UserEntity> stdList = user.findAllByVanEntityAndRoleType(vanList, RoleType.STD);

        for (int i = 1; i <= 6; i++) {
            switch (i) {
                case 1 -> subjectId = 4L;
                case 2 -> subjectId = 5L;
                case 3 -> subjectId = 7L;
                case 4 -> subjectId = 9L;
                case 5 -> subjectId = 36L;
                case 6 -> subjectId = 30L;
            }


            for (UserEntity entity : stdList) {
                int tmpStdScore = (int)(Math.random() * 160)+20;
                int rating = 0;
                int percent =  0;

                if(tmpStdScore >=150){
                    rating = 1;
                    percent = (int)(Math.random() * 101)+96;
                }
                else if(tmpStdScore >=130){
                    rating=2;
                    percent = (int)(Math.random() * 96)+88;
                }
                else if(tmpStdScore >=110){
                    rating=3;
                    percent = (int)(Math.random() * 88)+72;
                }
                else if(tmpStdScore >=95){
                    rating=4;
                    percent = (int)(Math.random() * 73)+57;
                }
                else if(tmpStdScore >=86){
                    rating=5;
                    percent = (int)(Math.random() * 63)+40;
                }
                else if(tmpStdScore >=60){
                    rating=6;
                    percent = (int)(Math.random() * 57)+50;
                }
                else if(tmpStdScore >=50){
                    rating=7;
                    percent = (int)(Math.random() * 40)+30;
                }
                else if(tmpStdScore >=40){
                    rating=8;
                    percent = (int)(Math.random() * 20)+10;
                }
                else {
                    rating=9;
                    percent = (int)(Math.random() * 4)+1;
                }


                mock.save(MockResultEntity.builder()
                        .userEntity(UserEntity.builder().userId(entity.getUserId()).build())
                        .subjectEntity(SubjectEntity.builder().subjectId(subjectId).build())
                        .year(year)
                        .mon(mon)
                        .standardScore(tmpStdScore)
                        .rating(rating)
                        .percent(percent)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
        }
    }
}