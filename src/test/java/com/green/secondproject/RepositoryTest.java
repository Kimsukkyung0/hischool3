//package com.green.secondproject;
//
//import com.green.secondproject.common.config.etc.EnrollState;
//import com.green.secondproject.common.config.security.PasswordEncoderConfiguration;
//import com.green.secondproject.common.config.security.model.RoleType;
//import com.green.secondproject.common.entity.*;
//import com.green.secondproject.common.repository.AcaRepository;
//import com.green.secondproject.common.repository.UserRepository;
//import com.green.secondproject.common.repository.VanRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
//@Import(PasswordEncoderConfiguration.class)
//public class RepositoryTest {
//    @Autowired
//    private AcaRepository acaRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VanRepository vanRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private final String classNum = "3";
//
//    @Test
//    @Rollback(value = false)
//    void calcRank() {
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    void insResult() {
//        long startIdx = (Long.parseLong(classNum) - 1) * 20 + 1;
//        long endIdx = startIdx + 19;
//
//        for (long i = startIdx; i <= endIdx; i++) {
//            acaRepository.save(AcaResultEntity.builder()
//                    .userEntity(UserEntity.builder().userId(i).build())
//                    .subjectEntity(SubjectEntity.builder().subjectId(1L).build())
//                    .year("2023")
//                    .midFinal(1)
//                    .rating((int)(Math.random() * 9) + 1)
//                    .score((int)(Math.random() * 101))
//                    .semester(1)
//                    .createdAt(LocalDateTime.now())
//                    .updatedAt(LocalDateTime.now())
//                    .build());
//        }
//    }
//
//    @Test
//    @Rollback(value = false)
//    void insStd() {
//        VanEntity vanEntity = VanEntity.builder()
//                .schoolEntity(SchoolEntity.builder().schoolId(70L).build())
//                .year("2023")
//                .grade("1")
//                .classNum(classNum)
//                .build();
//
//        VanEntity van = vanRepository.findBySchoolEntityAndYearAndGradeAndClassNum(
//                vanEntity.getSchoolEntity(), vanEntity.getYear(), vanEntity.getGrade(), vanEntity.getClassNum());
//
//        if (van == null) {
//            vanRepository.save(vanEntity);
//        }
//
//        for (int i = 1; i <= 20; i++) {
//            userRepository.save(UserEntity.builder()
//                    .vanEntity(vanEntity)
//                    .email("1G" + classNum + "Cstd" + i + "@naver.com")
//                    .pw(passwordEncoder.encode("1111"))
//                    .nm("1학년" + classNum + "반학생" + i)
//                    .pic("1학년" + classNum + "반학생" + i + ".jpg")
//                    .birth(LocalDate.now())
//                    .phone("010-0000-0000")
//                    .address("대구시 중구")
//                    .detailAddr("중앙빌딩 5F")
//                    .roleType(RoleType.STD)
//                    .enrollState(EnrollState.ENROLL)
//                    .createdAt(LocalDateTime.now())
//                    .updatedAt(LocalDateTime.now())
//                    .build());
//        }
//    }
//}
