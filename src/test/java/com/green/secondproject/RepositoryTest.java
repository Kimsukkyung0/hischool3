package com.green.secondproject;

import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.jpa.QueryDslConfig;
import com.green.secondproject.common.config.security.PasswordEncoderConfiguration;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.AcaResultRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VanRepository;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({PasswordEncoderConfiguration.class, QueryDslConfig.class})
@Slf4j
@Rollback(value = false)
public class RepositoryTest {
    @Autowired
    private AcaResultRepository acaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VanRepository vanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void 내신_성적_조회() {
        StudentAcaResultsParam p = StudentAcaResultsParam.builder()
                .userId(1L)
                .build();
        List<AcaResultEntity> list = acaRepository.searchAcaResult(p);
        for (AcaResultEntity acaResultEntity : list) {
            System.out.println(acaResultEntity);
        }
    }

    @Test
    void 전교_석차_계산() {
        List<VanEntity> vanList = vanRepository.findAllBySchoolEntityAndGradeAndYear(
                SchoolEntity.builder().schoolId(70L).build(), "1", "2023");
        List<UserEntity> stdList = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD);
        List<AcaResultEntity> resultList =
                acaRepository.findAllByUserEntityInAndSemesterAndSubjectEntityAndMidFinalAndYearOrderByScoreDesc(
                        stdList, 1, SubjectEntity.builder().subjectId(1L).build(), 1, "2023"
                );

        for (AcaResultEntity res1 : resultList) {
            int rank = 1;
            for (AcaResultEntity res2 : resultList) {
                if (res1.getScore() < res2.getScore()) {
                    rank++;
                }
            }
            res1.setWholeRank(rank);
            acaRepository.save(res1);
        }
    }
    @Test
    void 반_석차_계산() {
        for (int classNum = 3; classNum <= 10; classNum++) {
            VanEntity vanEntity = vanRepository.findBySchoolEntityAndYearAndGradeAndClassNum(
                    SchoolEntity.builder().schoolId(70L).build(), "2023", "1", String.valueOf(classNum));
            List<UserEntity> stdList = userRepository.findAllByVanEntityAndRoleType(vanEntity, RoleType.STD);
            List<AcaResultEntity> resultList =
                    acaRepository.findAllByUserEntityInAndSemesterAndSubjectEntityAndMidFinalAndYearOrderByScoreDesc(
                            stdList, 1, SubjectEntity.builder().subjectId(1L).build(), 1, "2023"
                    );

            for (AcaResultEntity res1 : resultList) {
                int rank = 1;
                for (AcaResultEntity res2 : resultList) {
                    if (res1.getScore() < res2.getScore()) {
                        rank++;
                    }
                }
                res1.setClassRank(rank);
                acaRepository.save(res1);
            }
        }
    }

    @Test
    void 내신성적_등록() {
        for (long classNum = 1; classNum <= 10; classNum++) {
            long startIdx = (classNum - 1) * 20 + 2;
            long endIdx = startIdx + 19;

            for (long i = startIdx; i <= endIdx; i++) {
                acaRepository.save(AcaResultEntity.builder()
                        .userEntity(UserEntity.builder().userId(i).build())
                        .subjectEntity(SubjectEntity.builder().subjectId(1L).build())
                        .year("2023")
                        .midFinal(1)
                        .rating((int)(Math.random() * 9) + 1)
                        .score((int)(Math.random() * 101))
                        .semester(1)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
        }
    }

    @Test
    void 학생_등록() {
        for (int classNum = 3; classNum <= 10; classNum++) {
            VanEntity vanEntity = VanEntity.builder()
                    .schoolEntity(SchoolEntity.builder().schoolId(70L).build())
                    .year("2023")
                    .grade("1")
                    .classNum(String.valueOf(classNum))
                    .build();

            VanEntity van = vanRepository.findBySchoolEntityAndYearAndGradeAndClassNum(
                    vanEntity.getSchoolEntity(), vanEntity.getYear(), vanEntity.getGrade(), vanEntity.getClassNum());

            if (van == null) {
                vanRepository.save(vanEntity);
            }

            for (int i = 1; i <= 20; i++) {
                userRepository.save(UserEntity.builder()
                        .vanEntity(vanEntity)
                        .email("1G" + classNum + "Cstd" + i + "@naver.com")
                        .pw(passwordEncoder.encode("1111"))
                        .nm("1학년" + classNum + "반학생" + i)
                        .pic("1학년" + classNum + "반학생" + i + ".jpg")
                        .birth(LocalDate.now())
                        .phone("010-0000-0000")
                        .address("대구시 중구")
                        .detailAddr("중앙빌딩 5F")
                        .roleType(RoleType.STD)
                        .enrollState(EnrollState.ENROLL)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
        }
    }
}
