package com.green.secondproject.mealmenutable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.mealmenutable.model.MealTableContainerVo;
import com.green.secondproject.mealmenutable.model.MealTableDto;
import com.green.secondproject.mealmenutable.model.MealTableVo;
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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

//https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=9a7b0b8f560b480abf0d0cb941af2bea&Type=json&pIndex=1&pSize=50&ATPT_OFCDC_SC_CODE=D10&SD_SCHUL_CODE=7240099&MLSV_FROM_YMD=20230506&MLSV_TO_YMD=20230512
//https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=9a7b0b8f560b480abf0d0cb941af2bea&Type=json&pIndex=1&pSize=50&ATPT_OFCDC_SC_CODE=D10&SD_SCHUL_CODE=7240273&MLSV_FROM_YMD=20230601&MLSV_TO_YMD=20230630
//함지고
//test용 주소
@Service
@Slf4j
public class MealMenuTableService {
    private final WebClient webClient;
    private final String myApiKey;

    @Autowired
    private UserMapper USERMAPPER;


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

    public MealTableContainerVo getMealTableBySchoolOfTheMonth(MyUserDetails myuser) {
//        YearMonth thisMonth = YearMonth.now();
        YearMonth thisMonth = YearMonth.of(2023,6);
        LocalDate thisMonthStart = thisMonth.atDay(1);//이번달의 시작
        LocalDate thisMonthEnds = thisMonth.atEndOfMonth();//기준달 마지막


        Long sdSchulCode = USERMAPPER.selSchoolCdByNm(myuser.getSchoolNm());


        MealTableDto dto = new MealTableDto();
        dto.setSdSchulCode(String.valueOf(sdSchulCode));
        dto.setStartDate(thisMonthStart.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        dto.setEndDate(thisMonthEnds.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        MealTableContainerVo result = getMealTableApi(dto);
        return result;
    }

    public MealTableContainerVo getMealTableBySchoolOfTheWeek(MyUserDetails myuser) {
//        LocalDate now = LocalDate.now();//현재방학중이므로 데이터가 없어서 기준일을 7월 1일로 고정해둠
        LocalDate now = LocalDate.of(2023,7,1);
        MealTableDto dto = new MealTableDto();

        Long sdSchulCode = USERMAPPER.selSchoolCdByNm(myuser.getSchoolNm());
        dto.setSdSchulCode(String.valueOf(sdSchulCode));
        dto.setStartDate(now.with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        dto.setEndDate(now.with(DayOfWeek.FRIDAY).format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        log.info("dto : {}", dto);
        MealTableContainerVo result = getMealTableApi(dto);
        return result;
    }


    public MealTableContainerVo getMealTableApi(MealTableDto dto){

        String json = webClient.get().uri(uriBuilder -> uriBuilder.path("/hub/mealServiceDietInfo")
                        //URI는 URL보다 큰 개념. 생성자에서 주입해준 주소에 덧붙여 세부uri 생성.
                        .queryParam("KEY", myApiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex",1)
                        .queryParam("pSize",50)
                        .queryParam("ATPT_OFCDC_SC_CODE","D10")//시도교육청코드
                        .queryParam("SD_SCHUL_CODE",dto.getSdSchulCode())
                        .queryParam("MLSV_FROM_YMD",dto.getStartDate())//조회시작일 : 기준월의 1일
                        .queryParam("MLSV_TO_YMD",dto.getEndDate())//조회종료일 : 기준월의 마지막일
                        .build()
                ).retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("json : {}",json);

        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        List<MealTableVo> mealTableVo = null;
        MealTableContainerVo result = null;

        try{
            JsonNode jsonNode = om.readTree(json); //자바객체
            mealTableVo = om.convertValue(jsonNode.at("/mealServiceDietInfo/1/row"), new TypeReference<List<MealTableVo>>() {});
            for (MealTableVo f : mealTableVo) {
                StringBuffer sb = new StringBuffer();
                sb.append(f.getDate());
                sb.insert(4,'-');
                sb.insert(7,'-');
                f.setDate(sb.toString());
                log.info(sb.toString());
                f.setMenuOftheDay(f.getMenuOftheDay().replaceAll("<br/>",",").replaceAll("[\\(0-9.\\)]","").replaceAll(" ",""));
            }
            Map<String,String> map = om.convertValue(jsonNode.at("/mealServiceDietInfo/1/row/0"), new TypeReference<Map<String, String>>() {});
            String schoolNm = map.get("SCHUL_NM");
            String strYearMonth = dto.getStartDate().substring(0,6);
            log.info("yearmonth : {}", strYearMonth);
            log.info("mealTableVoList : {}", mealTableVo);
            result = new MealTableContainerVo(schoolNm,strYearMonth,mealTableVo);

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}