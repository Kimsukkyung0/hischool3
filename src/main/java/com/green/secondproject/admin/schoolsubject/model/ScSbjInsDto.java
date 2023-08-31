package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.teacher.subject.model.SubjectInsVo2;
import lombok.Data;

import java.util.List;

@Data
public class ScSbjInsDto {
    private List<Long> subjectList;
}
