package com.green.secondproject.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.common.utils.ApiUtils;
import com.green.secondproject.schedule.model.ScheduleContainerVo;
import com.green.secondproject.schedule.model.ScheduleInfoVo;
import com.green.secondproject.schedule.model.ScheduleParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final SchoolRepository schoolRepository;

    @Value("${my-api.key}")
    private String myApiKey;

    public ScheduleContainerVo getSchedule(MyUserDetails user,ScheduleParam p) {
        SchoolEntity schoolEntity = schoolRepository.findById(user.getSchoolId()).get();

        String json = ApiUtils.createWebClient().get().uri(uriBuilder -> uriBuilder.path("/SchoolSchedule")
                        .queryParam("KEY", myApiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", 1)
                        .queryParam("pSize", 100)
                        .queryParam("ATPT_OFCDC_SC_CODE", "D10")
                        .queryParam("SD_SCHUL_CODE", schoolEntity.getCode())
                        .queryParam("AA_FROM_YMD",p.getAaFromYmd())
                        .queryParam("AA_TO_YMD",p.getAaToYmd())
                        .build()
                ).retrieve().bodyToMono(String.class)
                .block();
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ScheduleInfoVo> infoList = null;
        ScheduleContainerVo result = null;
        try {
            JsonNode jsonNode = om.readTree(json);
            infoList = om.convertValue(jsonNode.at("/SchoolSchedule/1/row"), new TypeReference<List<ScheduleInfoVo>>() {});
            Map<String, String> map = om.convertValue(jsonNode.at("/SchoolSchedule/1/row/0")
                    ,new TypeReference<HashMap<String, String>>() {});
            log.info("List : {}",infoList);
            String schoolNm = map.get("SCHUL_NM");
            String year = map.get("AY");

            result = new ScheduleContainerVo(year,schoolNm,infoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
