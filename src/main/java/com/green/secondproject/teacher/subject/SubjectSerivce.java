package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.SubjectDetailVo;
import com.green.secondproject.teacher.subject.model.SubjectEntity;
import com.green.secondproject.teacher.subject.model.SubjectVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectSerivce {
    private final SubjectMapper mapper;


    public List<SubjectVo> sbjCate(){
        return mapper.sbjCate();
    }

    public List<SubjectDetailVo> subDetail() {
        return mapper.subDetail();
    }

}
