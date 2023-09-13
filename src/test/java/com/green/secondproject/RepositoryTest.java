package com.green.secondproject;

import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.jpa.QueryDslConfig;
import com.green.secondproject.common.config.security.PasswordEncoderConfiguration;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.StudentAcaResultWithIdVo;
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
@Import({PasswordEncoderConfiguration.class, QueryDslConfig.class, MyGradeGraphUtils.class})
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
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private MockResultRepository mockResultRepository;
    @Autowired
    private SchoolAdminRepository adminRepository;

    @Test
    void 사용자_비밀번호_변경() {
        UserEntity user = userRepository.findById(1L).get();
        user.setPw(passwordEncoder.encode("gkrtod123!"));
        userRepository.save(user);
    }
    @Test
    void 관리자_비밀번호_변경() {
        SchoolAdminEntity admin = adminRepository.findById(3L).get();
        admin.setPw(passwordEncoder.encode("gkrtod123!"));
        adminRepository.save(admin);
    }
    @Test
    void 랭크함수_테스트() {
//        List<AcaResultEntity> list = acaRepository.findAll();
//        List<Integer> rankList = new java.util.ArrayList<>(list.stream().map(AcaResultEntity::getWholeRank).toList());
//        Collections.sort(rankList);
//        System.out.println(rankList);
//        System.out.println(acaRepository.rank());
//        assertEquals(rankList, acaRepository.rank());
    }
    @Test
    void 내신_등급_계산() {
        final double one = 0.04;
        final double two = 0.11;
        final double thr = 0.23;
        final double fou = 0.4;
        final double fiv = 0.6;
        final double six = 0.77;
        final double sev = 0.89;
        final double eig = 0.96;

        List<VanEntity> vanList = vanRepository.findAllBySchoolEntityAndGradeAndYear(
                SchoolEntity.builder().schoolId(70L).build(), "1", "2023");
        List<UserEntity> stdList = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD);
        List<AcaResultEntity> resultList =
                acaRepository.findAllByUserEntityInAndSemesterAndSubjectEntityAndMidFinalAndYearOrderByScoreDesc(
                        stdList, 1, SubjectEntity.builder().subjectId(1L).build(), 1, "2023"
                );

        long grade1 = Math.round(resultList.size() * one);
        long grade2 = Math.round(resultList.size() * two);
        long grade3 = Math.round(resultList.size() * thr);
        long grade4 = Math.round(resultList.size() * fou);
        long grade5 = Math.round(resultList.size() * fiv);
        long grade6 = Math.round(resultList.size() * six);
        long grade7 = Math.round(resultList.size() * sev);
        long grade8 = Math.round(resultList.size() * eig);

        for (AcaResultEntity r : resultList) {
            if (r.getWholeRank() <= grade1) {
                r.setRating(1);
            } else if (r.getWholeRank() <= grade2) {
                r.setRating(2);
            } else if (r.getWholeRank() <= grade3) {
                r.setRating(3);
            } else if (r.getWholeRank() <= grade4) {
                r.setRating(4);
            } else if (r.getWholeRank() <= grade5) {
                r.setRating(5);
            } else if (r.getWholeRank() <= grade6) {
                r.setRating(6);
            } else if (r.getWholeRank() <= grade7) {
                r.setRating(7);
            } else if (r.getWholeRank() <= grade8) {
                r.setRating(8);
            } else {
                r.setRating(9);
            }
            acaRepository.save(r);
        }
//        for (int i = 0; i < resultList.size(); i++) {
//            if (i < grade1) {
//                resultList.get(i).setRating(1);
//            } else if (i < grade2) {
//                resultList.get(i).setRating(2);
//            } else if (i < grade3) {
//                resultList.get(i).setRating(3);
//            } else if (i < grade4) {
//                resultList.get(i).setRating(4);
//            } else if (i < grade5) {
//                resultList.get(i).setRating(5);
//            } else if (i < grade6) {
//                resultList.get(i).setRating(6);
//            } else if (i < grade7) {
//                resultList.get(i).setRating(7);
//            } else if (i < grade8) {
//                resultList.get(i).setRating(8);
//            } else {
//                resultList.get(i).setRating(9);
//            }
//            acaRepository.save(resultList.get(i));
//        } 동점자 무시
    }
    @Test
    void 내신_성적_조회() {
        StudentAcaResultsParam p = StudentAcaResultsParam.builder()
                .userId(1L)
                .build();
        List<StudentAcaResultWithIdVo> list = acaRepository.searchAcaResult(p);
        for (StudentAcaResultWithIdVo vo : list) {
            System.out.println(vo);
        }
    }
    @Test
    void 전교_석차_계산() {
//        List<VanEntity> vanList = vanRepository.findAllBySchoolEntityAndGradeAndYear(
//                SchoolEntity.builder().schoolId(70L).build(), "1", "2023");
//        List<UserEntity> stdList = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD);
//        List<AcaResultEntity> resultList =
//                acaRepository.findAllByUserEntityInAndSemesterAndSubjectEntityAndMidFinalAndYearOrderByScoreDesc(
//                        stdList, 1, SubjectEntity.builder().subjectId(1L).build(), 1, "2023"
//                );
//
//        for (AcaResultEntity res1 : resultList) {
//            int rank = 1;
//            for (AcaResultEntity res2 : resultList) {
//                if (res1.getScore() < res2.getScore()) {
//                    rank++;
//                }
//            }
//            res1.setWholeRank(rank);
//            acaRepository.save(res1);
//        }
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
        final long schoolId = 3; // 청구고(3), 계성고(70)
        final String grade = "3";
        final String year = "2023";
        long subjectId = 15L; // 화언(1), 공통수학1(126), 공통영어1(140), 한국사1(10), 물리학(29)
        final int semester = 2;
        final int midFinal = 1;

        List<VanEntity> vanList = vanRepository.findAllBySchoolEntityAndGradeAndYear(
                SchoolEntity.builder().schoolId(schoolId).build(), grade, "2023");
        List<UserEntity> stdList = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD);

        for (int i = 0; i < 1; i++) {
//            switch (i) {
//                case 1 -> subjectId = 132L;
//                case 2 -> subjectId = 19L;
//                case 3 -> subjectId = 85L;
//                case 4 -> subjectId = 144L;
//                case 5 -> subjectId = 3L;
//                case 6 -> subjectId = 35L;
//                case 7 -> subjectId = 153L;
//            }
            for (UserEntity entity : stdList) {
                if (entity.getVanEntity().getVanId() != 89) {
                    acaRepository.save(AcaResultEntity.builder()
                            .userEntity(UserEntity.builder().userId(entity.getUserId()).build())
                            .subjectEntity(SubjectEntity.builder().subjectId(subjectId).build())
                            .year(year)
                            .midFinal(midFinal)
                            .score((int) (Math.random() * 101))
                            .semester(semester)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
                }
            }
        }
    }
    @Test
        void 모의고사_성적_등록() {
            final long schoolId = 70L; // 청구고(3), 계성고(70)
            final String grade = "1";
            final String year = "2022";
            final String mon = "3";
            long subjectId = 4L; // 국어(4), 수학(5), 영어(7), 한국사(9)

            List<VanEntity> vanList = vanRepository.findAllBySchoolEntityAndGradeAndYear(
                    SchoolEntity.builder().schoolId(schoolId).build(), grade, "2023");
            List<UserEntity> stdList = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD);

            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 1 -> subjectId = 5L;
                    case 2 -> subjectId = 7L;
                    case 3 -> subjectId = 9L;
                }

                for (UserEntity entity : stdList) {
                    mockResultRepository.save(MockResultEntity.builder()
                            .userEntity(UserEntity.builder().userId(entity.getUserId()).build())
                            .subjectEntity(SubjectEntity.builder().subjectId(subjectId).build())
                            .year(year)
                            .mon(mon)
                            .standardScore((int)(Math.random() * 201))
                            .rating((int)(Math.random() * 9) + 1)
                            .percent((int)(Math.random() * 101))
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
                }
            }
    }
    @Test
    void 학생_등록() {
        final long schoolId = 3L;
        for (int classNum = 1; classNum <= 1; classNum++) {
            VanEntity vanEntity = VanEntity.builder()
                    .schoolEntity(schoolRepository.getReferenceById(schoolId))
                    .year("2023")
                    .grade("3")
                    .classNum(String.valueOf(classNum))
                    .build();

            VanEntity van = vanRepository.findBySchoolEntityAndYearAndGradeAndClassNum(
                    vanEntity.getSchoolEntity(), vanEntity.getYear(), vanEntity.getGrade(), vanEntity.getClassNum());

            if (van == null) {
                van = vanRepository.save(vanEntity);
            }

            for (int i = 1; i <= 20; i++) {
                userRepository.save(UserEntity.builder()
                        .vanEntity(van)
                        .email("std3" + classNum + i + "@naver.com")
                        .pw(passwordEncoder.encode("gkrtod123!"))
                        .nm("3학년" + classNum + "반학생" + i)
                        .pic("3학년" + classNum + "반학생" + i + ".jpg")
                        .birth(LocalDate.now())
                        .phone("010-1234-5678")
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
    @Test
    void 선생님_등록() {
        for (int classNum = 1; classNum <= 10; classNum++) {
            VanEntity vanEntity = VanEntity.builder()
                    .schoolEntity(SchoolEntity.builder().schoolId(70L).build())
                    .year("2023")
                    .grade("1")
                    .classNum(String.valueOf(classNum))
                    .build();

            VanEntity van = vanRepository.findBySchoolEntityAndYearAndGradeAndClassNum(
                    vanEntity.getSchoolEntity(), vanEntity.getYear(), vanEntity.getGrade(), vanEntity.getClassNum());

            if (van == null) {
                van = vanRepository.save(vanEntity);
            }

            userRepository.save(UserEntity.builder()
                    .vanEntity(van)
                    .email("1G" + classNum + "CTC@naver.com")
                    .pw(passwordEncoder.encode("gkrtod123!"))
                    .nm("1학년" + classNum + "반선생님")
                    .pic("1학년" + classNum + "반선생님.jpg")
                    .birth(LocalDate.now())
                    .phone("010-0000-0000")
                    .address("대구시 중구")
                    .detailAddr("중앙빌딩 5F")
                    .roleType(RoleType.TC)
                    .enrollState(EnrollState.ENROLL)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
    }
}
