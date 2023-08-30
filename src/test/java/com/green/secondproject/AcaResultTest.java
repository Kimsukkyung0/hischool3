package com.green.secondproject;

import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.AcaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AcaResultTest {
    @Autowired
    private AcaRepository rep;

    @Test
    @Rollback(value = false)
    void insResult() {
        AcaResultEntity entity = AcaResultEntity.builder()
                .userEntity(UserEntity.builder().userId(8L).build())
                .subjectEntity(SubjectEntity.builder().subjectId(117L).build())
                .year("2023")
                .midFinal(1)
                .rating(1)
                .score(100)
                .semester(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        rep.save(entity);
    }
}
