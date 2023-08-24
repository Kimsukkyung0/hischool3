package com.green.secondproject.timetable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.common.config.security.UserMapper;
import com.green.secondproject.common.utils.ApiUtils;
import com.green.secondproject.timetable.model.TimeTableContainerVo;
import com.green.secondproject.timetable.model.TimeTableGetDto;
import com.green.secondproject.timetable.model.TimeTableVo;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TimetableService {
//    private final WebClient webClient;
   @Value("${my-api.key}")
    private String myApiKey;

    @Autowired
    private UserMapper USERMAPPER;

    public TimeTableContainerVo getTimeTableByClassAndTheWeek(TimeTableGetDto dto){
        LocalDate now = LocalDate.now();

//        LocalDate now = LocalDate.of(2023,5,26);

//        int dayOfToday = now.getDayOfWeek().getValue(); //요일값 구하기
//        if( dayOfToday >=6 ){//접속한 날이 토요일이라면 ?//다음주 시간표 보여줄 수 있도록
//            now.plusDays((7-dayOfToday)+1);
//        }
        String thisWeekStart = now.with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String thisWeekEnds = now.with(DayOfWeek.FRIDAY).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("thisWeekStart : {}", thisWeekStart);
        log.info("thisWeekEnds : {}", thisWeekEnds);

        Long sdSchulCode = USERMAPPER.selSchoolCdByNm(dto.getSchoolNm());

        String json = ApiUtils.createWebClient().get().uri(uriBuilder -> uriBuilder.path("/hisTimetable")
                //URI는 URL보다 큰 개념. 생성자에서 주입해준 주소에 덧붙여 세부uri 생성.
                .queryParam("KEY", myApiKey)
                .queryParam("Type", "json")
                .queryParam("pIndex",1)
                .queryParam("pSize",50)
                .queryParam("ATPT_OFCDC_SC_CODE","D10")//시도교육청코드
                .queryParam("SD_SCHUL_CODE",sdSchulCode)
                .queryParam("GRADE",dto.getGrade())
                .queryParam("CLASS_NM",dto.getClassNum())
                .queryParam("TI_FROM_YMD",thisWeekStart)//조회시작일 : 월
                .queryParam("TI_TO_YMD",thisWeekEnds)//조회종료일 : 금
                .build()
                ).retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("json : {}",json);
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        List<TimeTableVo> timeTableVoList = null;
        TimeTableContainerVo result = null; //빈 배열 및 리턴타입 선언

        try{
            JsonNode jsonNode = om.readTree(json); //자바객체
            timeTableVoList = om.convertValue(jsonNode.at("/hisTimetable/1/row"), new TypeReference<List<TimeTableVo>>() {});

            Map<String,String> map = om.convertValue(jsonNode.at("/hisTimetable/1/row/0"), new TypeReference<HashMap<String, String>>() {});
            log.info("timeTableVoList : {}", timeTableVoList);
            String semester = map.get("SEM");
            String schoolNm = map.get("SCHUL_NM");
            for (int i = 0; i < timeTableVoList.size(); i++) {
                timeTableVoList.get(i).setDayMonToSun(timeTableVoList.get(i).getDate());
            }
            result = new TimeTableContainerVo(schoolNm,dto.getGrade(),dto.getClassNum(),semester, timeTableVoList);

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

}
