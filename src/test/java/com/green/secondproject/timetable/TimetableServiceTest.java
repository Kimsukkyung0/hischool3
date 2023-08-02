package com.green.secondproject.timetable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.timetable.model.TimeTableContainerVo;
import com.green.secondproject.timetable.model.TimeTableParam;
import com.green.secondproject.timetable.model.TimeTableVo;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import({TimetableService.class})
class TimetableServiceTest {
    private MockMvc mvc;
    private String myApiKey;
    private WebClient webClient;

    @MockBean
    private TimetableService service;


    @Autowired
    public void TimetableService(@Value("${my-api.key}") String myApiKey){
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
        this.webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl("https://open.neis.go.kr")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Test
    @DisplayName("GetTimeTableServiceTEST : 5.22 오성고 1-1(월)")
    void getTimeTableByClassAndTheWeek() {

        TimeTableParam p = new TimeTableParam();
        p.setSdSchulCode("7240099");
        p.setGrade("1");
        p.setClassNm("1");

        //암튼 timetablecontainer vo 형식으로 리턴할 것이다.
        when(service.getTimeTableByClassAndTheWeek(p)).thenReturn(any(TimeTableContainerVo.class));

        //실제 넘어오는 값
        TimeTableContainerVo result = service.getTimeTableByClassAndTheWeek(p);

        List<TimeTableVo> mockSubVo = null;
        TimeTableVo timeTableListOfOhseongGo = new TimeTableVo();
        timeTableListOfOhseongGo.setDate("20230522");
        timeTableListOfOhseongGo.setDayMonToSun("20230522");//월
        timeTableListOfOhseongGo.setPeriod("1");
        timeTableListOfOhseongGo.setSubject("국어");
        System.out.println(timeTableListOfOhseongGo);
        mockSubVo.add(timeTableListOfOhseongGo);

        TimeTableContainerVo mockVo = new TimeTableContainerVo("오성고","1","1","1",mockSubVo);

        if(result !=null) {
            assertEquals(mockVo.getSchoolNm(), result.getSchoolNm());//학교이름
            assertEquals(mockVo.getGrade(), result.getGrade());//1학년
            assertEquals(mockVo.getClassNm(), result.getClassNm());//1반
            assertEquals(mockVo.getSemester(), result.getSemester());

            //서브 리스트 비교
            TimeTableVo simpleVo0 = mockVo.getList().get(0);

            assertEquals(simpleVo0.getPeriod(), timeTableListOfOhseongGo.getPeriod());
            assertEquals(simpleVo0.getSubject(), timeTableListOfOhseongGo.getSubject());
            assertEquals(simpleVo0.getDayMonToSun(), timeTableListOfOhseongGo.getDayMonToSun());
            assertEquals(simpleVo0.getDate(), timeTableListOfOhseongGo.getDate());
        }
    }


//    @Test
//    @DisplayName("GetTimeTableServiceTEST : 5.22 영진고 1-1(월)")
//    void getTimeTableByClassAndTheWeek2() throws Exception {
//
//        String criterion = "{\"KEY\":\"9a7b0b8f560b480abf0d0cb941af2bea\",\"Type\":\"json\",\"pIndex\": 1,\"pSize\":50" +
//                ",\"ATPT_OFCDC_SC_CODE\":\"D10\",\"SD_SCHUL_CODE\":\"7240099\",\"GRADE\":\"1\",\"classNm\":\"1\",\"TI_FROM_YMD\":\"20230522\",\"TI_TO_YMD\":\"20230526\"}";
//        String url = "https://open.neis.go.kr";
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("criterion", criterion);
////        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
////        JsonNode jsonNode =
////
////        Product product = restTemplate.getForObject(builder.build().toUri(), Product.class);
////
////        assertEquals(product.getId(), 1, 0);
//        ResultActions ra = mvc.perform(get("/api/timetable")
//                .contentType(MediaType.APPLICATION_JSON_UTF8));
//
//        ra.andExpect(status().isOk())//200을 기대하면..안되나
//                .andDo(print());
//    }
}