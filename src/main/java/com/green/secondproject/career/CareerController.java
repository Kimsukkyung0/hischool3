package com.green.secondproject.career;

import com.green.secondproject.career.model.CareerVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/career")
@RequiredArgsConstructor
@Slf4j
public class CareerController {
    private final CareerService service;

    @GetMapping
    @Operation(description = "학생 진로지도 (학생보기)")
    public List<CareerVo> SelStu(){
        return service.SelStu();
    }
}
