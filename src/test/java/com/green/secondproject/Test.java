package com.green.secondproject;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.van.VanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    @Autowired
    VanRepository repository;
    @org.junit.jupiter.api.Test
    void test() {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setSchoolId(1L);
        VanEntity entity = repository.findBySchoolEntityAndYearAndGradeAndClassNum(schoolEntity,
                "2023", "1", "1");
        System.out.println(entity);
    }
}
