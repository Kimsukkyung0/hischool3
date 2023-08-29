package com.green.secondproject.subject;

import com.green.secondproject.teacher.subject.SubjectMapper;
import com.green.secondproject.teacher.subject.model.SubjectDetailDto;
import com.green.secondproject.teacher.subject.model.SubjectDetailVo;
import com.green.secondproject.teacher.subject.model.SubjectDetailVo2;
import com.green.secondproject.teacher.subject.model.SubjectVo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
public class SubjectMapperTest {

    @Autowired
    private SubjectMapper mapper;

@Test
    void subCate(){
    List<SubjectVo> list = mapper.subCate();
    SubjectVo vo = list.get(0);

    assertEquals(2,list.size());
    assertEquals(1,vo.getCategoryid());
    assertEquals("국어",vo.getNm());

}
@Test
    void subject(){
    List<SubjectDetailVo> list = mapper.subject(1L);
    SubjectDetailVo vo = list.get(0);

    assertEquals(2,list.size());
    assertEquals(1,vo.getSubjectid());
    assertEquals("화법과 언어",vo.getNm());
}
@Test
    void tcslist() {
    SubjectDetailDto dto = new SubjectDetailDto();
    dto.setUserid(1L);
    List<SubjectDetailVo2> list = mapper.tcslist(dto);
        SubjectDetailVo2 vo2 = list.get(0);
    assertEquals(1,list.size());
    assertEquals(1,vo2.getSubjectid());
    assertEquals(1,vo2.getUserid());
    assertEquals("국어",vo2.getNm());
}
}
