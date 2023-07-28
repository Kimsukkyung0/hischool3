package com.green.secondproject.mealmenutable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.mealmenutable.model.MealTableContainerVo;
import com.green.secondproject.mealmenutable.model.MealTableParam;
import com.green.secondproject.mealmenutable.model.MealTableVo;
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

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=9a7b0b8f560b480abf0d0cb941af2bea&Type=json&pIndex=1&pSize=50&ATPT_OFCDC_SC_CODE=D10&SD_SCHUL_CODE=7240099&MLSV_FROM_YMD=20230506&MLSV_TO_YMD=20230512
//test용 주소
@Service
@Slf4j
public class MealMenuTableService {
    private final WebClient webClient;
    private final String myApiKey;

    public MealMenuTableService(@Value("${my-api.key}") String myApiKey) {
        this.myApiKey = myApiKey;
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)//5초간의 연결시도
                .doOnDisconnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000));
                });
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(-1))
                .build();

        this.webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies).baseUrl("https://open.neis.go.kr")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public MealTableContainerVo GetMealTableBySchoolOfTheWeek(MealTableParam p) {
        YearMonth thisMonth = YearMonth.now();
        log.info("thisMonth : {}",thisMonth);
        LocalDate thisMonthStart = thisMonth.atDay(1);//이번달의 시작
        log.info("thisMonthstart : {}",thisMonthStart);
        LocalDate thisMonthEnds = thisMonth.atEndOfMonth();//기준달 마지막
        log.info("thisMonthEnds : {}",thisMonthEnds);


        String strthisMonthStart = thisMonthStart.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String strthisMonthEnds = thisMonthEnds.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("strthisMonthstart : {}",strthisMonthStart);
        log.info("strthisMonthEnds : {}",strthisMonthEnds);

        String json = webClient.get().uri(uriBuilder -> uriBuilder.path("/hub/mealServiceDietInfo")
                        //URI는 URL보다 큰 개념. 생성자에서 주입해준 주소에 덧붙여 세부uri 생성.
                        .queryParam("KEY", myApiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex",1)
                        .queryParam("pSize",50)
                        .queryParam("ATPT_OFCDC_SC_CODE","D10")//시도교육청코드
                        .queryParam("SD_SCHUL_CODE",p.getSdSchulCode())
                        .queryParam("TI_FROM_YMD",strthisMonthStart)//조회시작일 : 기준월의 1일
                        .queryParam("TI_TO_YMD",strthisMonthEnds)//조회종료일 : 기준월의 마지막일
                        .build()
                ).retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("json : {}",json);

        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //tcp client
        List<MealTableVo> mealTableVo = null;
        MealTableContainerVo result = null;

        try{
            JsonNode jsonNode = om.readTree(json); //자바객체
            mealTableVo = om.convertValue(jsonNode.at("/mealServiceDietInfo/1/row"), new TypeReference<List<MealTableVo>>() {});
            for (MealTableVo f : mealTableVo) {
                f.setMenuOftheDay(f.getMenuOftheDay().replaceAll("<br/>",",").replaceAll("^[가-힣]*$_//.", ""));
                //	^[가-힣]*$ 정규식
//                f.setMenuOftheDay(f.getMenuOftheDay().replaceAll("<br/>",",").replaceAll("[0-9]", "").replaceAll("\\.",""));
            }
            Map<String,String> map = om.convertValue(jsonNode.at("/mealServiceDietInfo/1/row/0"), new TypeReference<Map<String, String>>() {});
//            String date = map.get("MLSV_YMD");
//            String lunchOrDinner = map.get("MMEAL_SC_NM");
//            String menuOftheDay = map.get("DDISH_NM");//메뉴이름
            String schoolNm = map.get("SCHUL_NM");
            log.info("mealTableVoList : {}", mealTableVo);
            result = new MealTableContainerVo(schoolNm,mealTableVo);
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