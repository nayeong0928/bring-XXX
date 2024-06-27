package com.bring.back;

import com.bring.back.weather.WeatherDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-api-key.properties")
public class JsonParsingTest {

    @Value("${info.oepnapi.service.key}")
    private String serviceKey;

    @Test
    @DisplayName("weather-data.json 을 읽어와서 날씨 정보를 초기화한다")
    void parseWeather() throws IOException {

        File file=new File(System.getProperty("user.dir")
                +File.separator+"src"
                +File.separator+"main"
                +File.separator+"resources"
                +File.separator+"weather-data.json");
        ObjectMapper mapper=new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode itemNode = jsonNode.path("response").path("body").path("items").path("item");
        List<WeatherDto> list=mapper.readValue(itemNode.toString(), new TypeReference<List<WeatherDto>>() {});

        for (WeatherDto dto: list){
            System.out.println(dto.toString());
        }

        Assertions.assertThat(list).isNotNull();
    }

    @Test
    @DisplayName("공공 데이터 api에서 날씨 정보를 요청하여 날씨 예보 정보를 리턴한다")
    void responseApi() throws IOException {

        LocalDateTime now=LocalDateTime.now();
        String dateNow=now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        String time=now.format(DateTimeFormatter.ofPattern("HHmm"));
        String time=now.format(DateTimeFormatter.ofPattern("1400"));

        Assertions.assertThat(serviceKey).isNotNull();

        StringBuilder urlBuilder=new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(dateNow, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        Assertions.assertThat(sb).isNotNull();

        ObjectMapper mapper=new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = mapper.readTree(sb.toString());
        JsonNode itemNode = jsonNode.path("response").path("body").path("items").path("item");
        List<WeatherDto> list=mapper.readValue(itemNode.toString(), new TypeReference<List<WeatherDto>>() {});

        for (WeatherDto dto: list){
            System.out.println(dto.toString());
        }

        Assertions.assertThat(list).isNotNull();

    }
}
