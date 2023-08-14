package com.green.secondproject.schedule;


import com.green.secondproject.teacher.subject.SubjectMapper;
import com.green.secondproject.teacher.subject.SubjectService;
import com.green.secondproject.teacher.subject.model.SubjectVo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({ScheduleService.class})
public class ScheduleServiceTest {

}
