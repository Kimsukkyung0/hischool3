package com.green.secondproject.career;

import com.green.secondproject.career.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "진로지도")
@RestController
@RequestMapping("/api/career")
@RequiredArgsConstructor
@Slf4j
public class CareerController {
    private final CareerService service;

    @GetMapping
    @Operation(summary = "학생 진로지도 (학생보기)")
    public List<CareerVo> SelStu(){
        return service.SelStu();
    }
    @GetMapping("/by")
    @Operation(summary = "학생별 선택")
    public List<CareerVo> SelByStu(@RequestParam Long userId,@RequestParam Long careerId){
    CareerSelByDto dto = new CareerSelByDto();
    dto.setCareerId(careerId);
    dto.setUserId(userId);
        return service.SelByStu(dto);
    }
    @PostMapping("/text")
    @Operation(summary = "진로지도 입력")
    public CareerVo StuIns(@RequestBody CareerInsDto dto){
        return service.StuIns(dto);
    }
    @PatchMapping("/clear")
    @Operation(summary = "진로지도 수정")
    public CareerVo2 CareerUp(@RequestBody CareerUpDto dto){
        return service.CareerUp(dto);
    }
}
