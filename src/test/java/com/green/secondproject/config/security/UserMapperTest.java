package com.green.secondproject.config.security;

import com.green.secondproject.common.config.security.UserMapper;
import com.green.secondproject.common.config.security.model.UserEntity;
import com.green.secondproject.sign.model.ClassDto;
import com.green.secondproject.sign.model.UserVo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {
    @Autowired
    private UserMapper mapper;

    @Test
    void save() {
        UserEntity tc = UserEntity.builder()
                .email("test1@gmail.com")
                .pw("1111")
                .nm("테스트1")
                .pic("test1.jpg")
                .birth(LocalDate.now())
                .phone("010-0000-0000")
                .address("대구시 중구")
                .detailAddress("제일빌딩 5F")
                .role("ROLE_TC")
                .classId(1L)
                .aprPic("aprPic.jpg")
                .aprYn(1)
                .build();

        int r1 = mapper.save(tc);

        assertEquals(1, r1);
        assertEquals(26, tc.getUserId());

        UserEntity std = UserEntity.builder()
                .email("test2@gmail.com")
                .pw("1111")
                .nm("테스트2")
                .pic("test2.jpg")
                .birth(LocalDate.now())
                .phone("010-0000-0000")
                .address("대구시 중구")
                .detailAddress("제일빌딩 5F")
                .role("ROLE_STD")
                .classId(1L)
                .build();

        int r2 = mapper.save(std);

        assertEquals(1, r2);
        assertEquals(27, std.getUserId());

        UserVo tcUser = mapper.selUserById(tc.getUserId());
        UserVo stdUser = mapper.selUserById(std.getUserId());

        assertEquals(tc.getNm(), tcUser.getNm());
        assertEquals(std.getNm(), stdUser.getNm());
    }

    @Test
    void selUserByEmail() {
        UserVo user = mapper.selUserByEmail("tc@gmail.com");

        assertEquals("김선생", user.getNm());
        assertEquals("4ea6ede8-3f10-4429-9180-7a046b793ba7.png", user.getPic());
    }

    @Test
    void selUserById() {
        UserVo user = mapper.selUserById(2L);

        assertEquals("김학생", user.getNm());
        assertEquals("bff7ac5d-af7b-4042-b608-1dff52e33800.png", user.getPic());
    }

    @Test
    void selSchoolCdByNm() {
        Long code = mapper.selSchoolCdByNm("함지고등학교");

        assertEquals(7240273, code);
    }

    @Test
    void selSchoolIdByNm() {
        Long id = mapper.selSchoolIdByNm("오성고등학교");

        assertEquals(2, id);
    }

    @Test
    void selClassId() {
        ClassDto dto = ClassDto.builder()
                .schoolId(1L)
                .year("2023")
                .grade("1")
                .classNum("1")
                .build();

        Long id = mapper.selClassId(dto);

        assertEquals(1, id);
    }
}