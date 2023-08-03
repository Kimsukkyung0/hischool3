package com.green.secondproject.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.schedule.model.ScheduleContainerVo;
import com.green.secondproject.schedule.model.ScheduleInfoVo;
import com.green.secondproject.schedule.model.ScheduleParam;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ScheduleService {
    private final String myApiKey;
    private final WebClient webClient;

    public ScheduleService(@Value("${my-api.key}") String myApiKey) {
        this.myApiKey = myApiKey;

        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) //5초동안 시도
                .doOnConnected(conn -> {
                    conn.addHandlerLast(new ReadTimeoutHandler(5000));
                    conn.addHandlerLast(new WriteTimeoutHandler(5000));
                });
        //저장공간 무한대 증식
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(-1))
                .build();


        this.webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl("https://open.neis.go.kr")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public ScheduleContainerVo getSchedule(ScheduleParam p) {
        String json = webClient.get().uri(uriBuilder -> uriBuilder.path("/hub/SchoolSchedule")
                        .queryParam("KEY", myApiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", 1)
                        .queryParam("pSize", 100)
                        .queryParam("ATPT_OFCDC_SC_CODE", "D10")
                        .queryParam("SD_SCHUL_CODE", p.getSdSchulCode())
                        .queryParam("AY", p.getAy())
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
