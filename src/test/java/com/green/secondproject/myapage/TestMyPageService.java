package com.green.secondproject.myapage;


import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mypageTest")
@Primary
public class TestMyPageService  {

    public TestMyPageService() {
        super();
    }

}