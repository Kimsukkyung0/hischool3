package com.green.secondproject.header;

import com.green.secondproject.header.model.SelSchoolInfoDto;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeaderService {
    private final HeaderMapper mapper;

    @Autowired
    public HeaderService(HeaderMapper mapper) {
        this.mapper = mapper;
    }


    public List<SelSchoolInfoVo> selSchoolInfo(int studentId) {
        SelSchoolInfoDto dto = new SelSchoolInfoDto();
        dto.setStudentId(studentId);
        return mapper.selSchoolInfo(dto);
    }
}
