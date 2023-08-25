package com.green.secondproject;


import com.green.secondproject.common.utils.PwUtils;

public class Test {
    @org.junit.jupiter.api.Test
    void test() {
        System.out.println(PwUtils.getRandomPassword(8));
    }
}
