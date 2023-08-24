package com.green.secondproject;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.utils.PwUtils;
import com.green.secondproject.van.VanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    @org.junit.jupiter.api.Test
    void test() {
        System.out.println(PwUtils.getRandomPassword(8));
    }
}
