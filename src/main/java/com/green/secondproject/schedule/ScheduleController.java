package com.green.secondproject.schedule;

import com.green.secondproject.schedule.model.ScheduleContainerVo;
import com.green.secondproject.schedule.model.ScheduleParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag (name = "학사일정")
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService service;


    @GetMapping
    @Operation(summary = "학사일정")
    public ScheduleContainerVo getTimetable(ScheduleParam p){
        log.info("p : {}",p);
        return service.getSchedule(p);
    }

}
