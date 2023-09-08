package com.green.secondproject.student.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentAcaResultWithIdVo {
    private Long resultId;
    private String year;
    private int semester;
    private int midFinal;
    private String cateName;
    private String nm;
    private int score;
    private int rating;
    private int classRank;
    private int wholeRank;
    private long vanCnt;
    private long wholeCnt;

    @QueryProjection
    public StudentAcaResultWithIdVo(Long resultId, String year, int semester, int midFinal, String cateName, String nm,
                                    int score, int rating, int classRank, int wholeRank, long vanCnt, long wholeCnt) {
        this.resultId = resultId;
        this.year = year;
        this.semester = semester;
        this.midFinal = midFinal;
        this.cateName = cateName;
        this.nm = nm;
        this.score = score;
        this.rating = rating;
        this.classRank = classRank;
        this.wholeRank = wholeRank;
        this.vanCnt = vanCnt;
        this.wholeCnt = wholeCnt;
    }
}
