package com.green.secondproject.result;

import com.green.secondproject.result.model.CalcClassRankParam;
import com.green.secondproject.result.model.CalcWholeRankParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcaResultMapper {
    void calcClassRank(CalcClassRankParam p);
    int calcWholeRankAndRating(CalcWholeRankParam p);
}
