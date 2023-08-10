package com.green.secondproject.subject;

import com.green.secondproject.teacher.subject.SubjectMapper;
import com.green.secondproject.teacher.subject.model.SubjectVo;
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

public class SubjectMapperTest {
    @Autowired
    private SubjectMapper mapper;
@Test
    void subCate(){
    List<SubjectVo> list = mapper.subCate();
    SubjectVo vo = list.get(0);

    assertEquals(4,list.size());
    assertEquals(1,vo.getCategoryid());
    assertEquals("국어",vo.getNm());

}

}
