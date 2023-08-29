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
    @Operation(summary = "학생 진로지도 (학생보기)"
            ,description = "<br>" +
            "userId:\n" +"유저pk값<br>\n"+
            "grade:\n" +"학년<br>\n"+
            "interest:\n" +"특기 및 흥미<br>\n"+
            "stdHope:\n" +"학생 진로희망<br>\n"+
            "parentHope:\n" +"부모 진로희망<br>\n"+
            "hopeUniv:\n" +"희망하는 대학교<br>\n"+
            "hopeDept:\n" +"희망하는 학과<br>\n"+
            "specialNote:\n"+"행동 특성 및 종합 의견<br>\n")
    public List<CareerVo> SelStu(){
        return service.SelStu();
    }
    @GetMapping("/by")
    @Operation(summary = "학생별 선택"
            ,description = "<br>" +
            "userId:\n" +"유저pk값<br>\n"+
            "grade:\n" +"학년<br>\n"+
            "interest:\n" +"특기 및 흥미<br>\n"+
            "stdHope:\n" +"학생 진로희망<br>\n"+
            "parentHope:\n" +"부모 진로희망<br>\n"+
            "hopeUniv:\n" +"희망하는 대학교<br>\n"+
            "hopeDept:\n" +"희망하는 학과<br>\n"+
            "specialNote:\n"+"행동 특성 및 종합 의견<br>\n")
    public List<CareerVo> SelByStu(@RequestParam Long userId){
    CareerSelByDto dto = new CareerSelByDto();
    dto.setUserId(userId);
        return service.SelByStu(dto);
    }
    @PostMapping("/text")
    @Operation(summary = "진로지도 입력"
            ,description = "<br>" +
            "userId:\n" +"유저pk값<br>\n"+
            "grade:\n" +"학년<br>\n"+
            "interest:\n" +"특기 및 흥미<br>\n"+
            "stdHope:\n" +"학생 진로희망<br>\n"+
            "parentHope:\n" +"부모 진로희망<br>\n"+
            "hopeUniv:\n" +"희망하는 대학교<br>\n"+
            "hopeDept:\n" +"희망하는 학과<br>\n"+
            "specialNote:\n"+"행동 특성 및 종합 의견<br>\n")
    public CareerVo StuIns(@RequestBody CareerInsDto dto){
        return service.StuIns(dto);
    }
    @PatchMapping("/clear")
    @Operation(summary = "진로지도 수정"
            ,description = "<br>" +
            "userId:\n" +"유저pk값<br>\n"+
            "grade:\n" +"학년<br>\n"+
            "interest:\n" +"특기 및 흥미<br>\n"+
            "stdHope:\n" +"학생 진로희망<br>\n"+
            "parentHope:\n" +"부모 진로희망<br>\n"+
            "hopeUniv:\n" +"희망하는 대학교<br>\n"+
            "hopeDept:\n" +"희망하는 학과<br>\n"+
            "specialNote:\n"+"행동 특성 및 종합 의견<br>\n")
    public CareerVo2 CareerUp(@RequestBody CareerUpDto dto){
        return service.CareerUp(dto);
    }
    @GetMapping("/hope")
    @Operation(summary = "희망대학,희망학과 출력"
            ,description = "<br>" +
            "userId:\n" +"유저pk값<br>\n"+
            "grade:\n" +"학년<br>\n"+
            "interest:\n" +"특기 및 흥미<br>\n"+
            "stdHope:\n" +"학생 진로희망<br>\n"+
            "parentHope:\n" +"부모 진로희망<br>\n"+
            "hopeUniv:\n" +"희망하는 대학교<br>\n"+
            "hopeDept:\n" +"희망하는 학과<br>\n"+
            "specialNote:\n"+"행동 특성 및 종합 의견<br>\n")
    public List<CareerVo3> HopeSel(@RequestParam Long userId){

        CareerDto dto = new CareerDto();
        dto.setUserId(userId);
        return service.HopeSel(dto);
    }
}
