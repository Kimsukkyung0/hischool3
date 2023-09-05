package com.green.secondproject.acaResult;

import com.green.secondproject.acaResult.model.CalcClassRankParam;
import com.green.secondproject.acaResult.model.CalcWholeRankParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcaResultMapper {
    void calcClassRank(CalcClassRankParam p);
    int calcWholeRankAndRating(CalcWholeRankParam p);
}
