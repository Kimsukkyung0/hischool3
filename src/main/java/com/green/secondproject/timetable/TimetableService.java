package com.green.secondproject.timetable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.timetable.model.TimeTableContainerVo;
import com.green.secondproject.timetable.model.TimeTableParam;
import com.green.secondproject.timetable.model.TimeTableVo;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
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
    private final WebClient webClient;
    private final String myApiKey;

    //생성자로 private 필드에 값넣
    public TimetableService(@Value("${my-api.key}") String myApiKey){
         this.myApiKey = myApiKey;
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)//5초간의 연결시도
                .doOnDisconnected(connection ->{//연결해제되었을때 시도
                    connection.addHandlerLast(new ReadTimeoutHandler(5000));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000));
                    //연결처리의 지속시간 +읽기쓰기 이 숫자가 extra 5초인진 모르겠음
                                    });

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(-1))
                .build();
        //교환전략 무슨말임이게 일단 파트별로 어떤역할하는지만 짚고넘어가자

        //클라이언트(데이터요청보내는 주체) 만들기
         this.webClient = WebClient.builder().exchangeStrategies(exchangeStrategies)
                 .baseUrl("https://open.neis.go.kr")
                 .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                 .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                 .build();
    }

    public TimeTableContainerVo getTimeTableByClassAndTheWeek(TimeTableParam timeTableParam){
//        LocalDate now = LocalDate.of(2023,4, 13);
        //tmpnow
        LocalDate now = LocalDate.now();

        LocalDate StartDayOfTheWeek = null;
//
        String thisWeekStart = now.with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String thisWeekEnds = now.with(DayOfWeek.FRIDAY).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("thisWeekStart : {}", thisWeekStart);
        log.info("thisWeekEnds : {}", thisWeekEnds);

        // 접속한 날 주의 월요일: 처음엔 단순히 분기문을 이용해서 +- 를 하면 되는건가 생각햇는데. 메서드가 있을까봐 찾는중
        //보통 localdate 쓰는게 아니라 calender 사용하는듯..
        //localdate 를 사용하려면 , localdate.with (dayOfweek, 요일지정)
        //접속한 날 주의 금요일
//        int WhatDaysToday = now.getDayOfWeek().getValue(); //접속한날짜의 요일
//
//        if(WhatDaysToday<= 1){
//            StartDayOfTheWeek = today.plusDays(-today.getDayOfWeek().getValue());}
//        LocalDate EndDayOfTheWeek = StartDayOfTheWeek.plusDays(5);

        String json = webClient.get().uri(uriBuilder -> uriBuilder.path("/hub/hisTimetable")
                //URI는 URL보다 큰 개념. 생성자에서 주입해준 주소에 덧붙여 세부uri 생성.
                .queryParam("KEY", myApiKey)
                .queryParam("Type", "json")
                .queryParam("pIndex",1)
                .queryParam("pSize",50)
                .queryParam("ATPT_OFCDC_SC_CODE","D10")//시도교육청코드
                .queryParam("SD_SCHUL_CODE",timeTableParam.getSdSchulCode())
                .queryParam("GRADE",timeTableParam.getGrade())
                .queryParam("classNm",timeTableParam.getClassNm())
                .queryParam("TI_FROM_YMD",thisWeekStart)//조회시작일 : 월
                .queryParam("TI_TO_YMD",thisWeekEnds)//조회종료일 : 금
                .build()
                ).retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("json : {}",json);
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //원치않는 값은 받지않겠슈..
        //https://open.neis.go.kr/hub/hisTimetable?KEY=d79ab6b1aab04b20b66cc314fb32124f&Type=json&pIndex=1&pSize=50&ATPT_OFCDC_SC_CODE=D10&SD_SCHUL_CODE=7240073&GRADE=1&CLASS_NM=01&TI_FROM_YMD=20230524&TI_TO_YMD=20230528
        //TEST용 주소(5월 한주,)

        List<TimeTableVo> timeTableVoList = null;
        TimeTableContainerVo result = null; //빈 배열 및 리턴타입 선언

        try{
            JsonNode jsonNode = om.readTree(json); //자바객체
            timeTableVoList = om.convertValue(jsonNode.at("/hisTimetable/1/row"), new TypeReference<List<TimeTableVo>>() {});
            Map<String,String> map = om.convertValue(jsonNode.at("/hisTimetable/1/row/0"), new TypeReference<HashMap<String, String>>() {});
            log.info("timeTableVoList : {}", timeTableVoList);
            String semester = map.get("SEM");
            String schoolNm = map.get("SCHUL_NM");
            result = new TimeTableContainerVo(schoolNm,timeTableParam.getGrade(),timeTableParam.getClassNm(),semester,timeTableVoList);
//            private String schoolNm;
//            private String grade; // 강동고등학교 1학년 1반 1학기 ()
//            private String classNm;
//            private String semester;
//            private List<TimeTableVo> list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

}
