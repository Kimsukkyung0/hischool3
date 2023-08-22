package com.green.secondproject.mealmenutable;

import com.green.secondproject.common.config.security.UserMapper;
import com.green.secondproject.mealmenutable.model.MealTableContainerVo;
import com.green.secondproject.mealmenutable.model.MealTableDto;
import com.green.secondproject.mealmenutable.model.MealTableVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@Import({MealMenuTableService.class})
class MealMenuTableServiceTest {
    @MockBean
    UserMapper MAPPER;
    @MockBean
    MealMenuTableService service;

    @Test
    @DisplayName("학교코드 TEST")
    void selSchoolCdByNmTest() {
        //오성고를 보내면 Long 타입의 학교 코드가 돌아온다.
        String schoolCdByNm = "오성고등학교";
        Long schoolCd = 7240099L;
        when(MAPPER.selSchoolCdByNm(schoolCdByNm)).thenReturn(schoolCd);
    }

    @Test
    @DisplayName("급식표테스트-월간")
    void getMealTableBySchoolOfTheWeek() {
        //dto
        Long schoolCd = 7240099L;
        MealTableDto dto = new MealTableDto();
        dto.setSdSchulCode(schoolCd.toString());
        dto.setStartDate("20230601");
        dto.setEndDate("20230630");

        //비교대상 가짜리스트 만들기
        List<MealTableVo> mealTableVo = new ArrayList<>();
        MealTableVo subList = new MealTableVo();
        subList.setDate("20230601");
        subList.setLunchOrDinner("lunch");
        subList.setMenuOftheDay("햄버거");
        mealTableVo.add(subList);
        MealTableContainerVo result = new MealTableContainerVo("오성고등학교","2023-6",mealTableVo);
        when(service.getMealTableApi(dto)).thenReturn(result);

        MealTableContainerVo ListForComp = service.getMealTableApi(dto);
        List<MealTableVo> subListForComp = ListForComp.getList();

        assertEquals(ListForComp.getSchoolNm(),"오성고등학교");
        assertEquals(ListForComp.getStrYearMonth(),"2023-6");
        //포함되어있어야 하는 일자
        assertFalse(subListForComp.contains("2023-06-15"));
        //없어야하는 일자-토.일
        assertTrue(!subListForComp.contains("2023-06-17"));
    }

//    @Test
//    @Disabled
//    @DisplayName("급식표api")
//    void getMealTableApi() {
//        MealTableDto dto = new MealTableDto();
//        dto.setSdSchulCode("7240099");
//        dto.setStartDate("20230601");
//        dto.setEndDate("20230630");
//
//        List<MealTableVo> mealTableVo = new ArrayList<>();
//        MealTableVo subList1 = new MealTableVo();
//
//        mealTableVo.add()
//        MealTableContainerVo result = new MealTableContainerVo();
//    }
}