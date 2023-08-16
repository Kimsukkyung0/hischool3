package com.green.secondproject.mypage;

import com.green.secondproject.mypage.model.SelUserMyPageDto;
import com.green.secondproject.mypage.model.SelUserMyPageVo;
import com.green.secondproject.teacher.subject.SubjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
@ActiveProfiles("test")
class MyPageMapperTest {

    @Autowired
    private MyPageMapper mapper;

//    @Test
//    void updUserInfo() {
//
//    }
//
//    @Test
//    void delUser() {
//    }

    @Test
    void selUserMyPage() {
        SelUserMyPageDto dto = new SelUserMyPageDto();
        dto.setUserId(1L);
        SelUserMyPageVo vo = mapper.selUserMyPage(dto);
        vo.getUserId();
        vo.getRole();
//        vo.getUnm();
//        vo.getEmail();
//        vo.getPic();
//        vo.getBirth();
//        vo.getPhone();
//        vo.getAddress();
//        vo.getDetailAddr();
//        vo.getClassId();
//        vo.getGrade();
//        vo.getVan();
//        vo.getSchoolId();
//        vo.getSchnm();

        assertEquals(1,vo.getUserId());
        assertEquals("ROLE_TC",vo.getRole());
//detail_addr 추가하셈
    }


}