//package com.green.secondproject;
//
//import com.green.secondproject.common.entity.AcaResultEntity;
//import com.green.secondproject.common.entity.SubjectEntity;
//import com.green.secondproject.common.entity.UserEntity;
//import com.green.secondproject.common.repository.AcaRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class AcaResultTest {
//    @Autowired
//    private AcaRepository rep;
//
//    @Test
//    void insResult() {
//        AcaResultEntity entity = AcaResultEntity.builder()
//                .userEntity(UserEntity.builder().userId(2L).build())
//                .subjectEntity(SubjectEntity.builder().subjectId(117L).build())
//                .year("2023")
//                .midFinal(1)
//                .rating(1)
//                .score(100)
//                .semester(1)
//                .build();
//        rep.save(entity);
//    }
//}
