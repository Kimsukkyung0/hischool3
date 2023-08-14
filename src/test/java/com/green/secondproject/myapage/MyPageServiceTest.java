package com.green.secondproject.myapage;


import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mypageTest")
@Primary
public class MyPageServiceTest {

    public MyPageServiceTest() {
        super();
    }

}
