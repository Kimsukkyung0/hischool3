package com.green.secondproject.header;

import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeaderController {
    private final HeaderService service;


    @GetMapping
    public List<SelSchoolInfoVo> SelectSchoolInfo(@RequestParam int userId) {
        return service.selSchoolInfo(userId);
    }

    @GetMapping("/asdf")
    public List<SelSchoolLogoVo> SelectSchoolLogo(@RequestParam int userId) {
        return service.selSchoolLogo(userId);
    }
}
