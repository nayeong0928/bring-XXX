package com.example.back.service;

import com.example.back.dto.ScheduleDto;
import com.example.back.dto.ScheduleItemDto;
import com.example.back.dto.TimeBlock;
import com.example.back.entity.Address;
import com.example.back.entity.Location;
import com.example.back.entity.Member;
import com.example.back.entity.Schedule;
import com.example.back.repository.AddressRepository;
import com.example.back.repository.MemberRepository;
import com.example.back.repository.ScheduleRepository;
import com.example.back.weather.WeatherInfo;
import com.example.back.weather.WeatherObserver;
import com.example.back.weather.WeatherStation;
import com.example.back.weather.WeatherStationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final WeatherStationManager weatherStationManager;

    @Value("${serviceKey}")
    private String serviceKey;

    @Transactional
    public void addSchedule(Long memberId, Long addrId, int time){
        Member member = memberRepository.findOne(memberId);
        Address addr = addressRepository.findOne(addrId);
        Schedule schedule = Schedule.createSchedule(member, addr, time);
        scheduleRepository.save(schedule);
    }

    public Map<Integer, TimeBlock> schedules(Long memberId){
        Member member = memberRepository.findOne(memberId);
        HashMap<Integer, ScheduleDto> map=new HashMap<>();
        Map<Integer, TimeBlock> timeBlocks=new HashMap<>();

        for(Schedule schedule: member.getSchedules()){
            TimeBlock block=new TimeBlock();
            block.setMemberId(memberId);
            block.setTime(schedule.getTime());
            block.setScheduleId(schedule.getId());
            block.setAddressId(schedule.getAddress().getId());
            block.setAddressStr(schedule.getAddress().toString());
            timeBlocks.put(schedule.getTime(), block);
        }

        return timeBlocks;
    }

    /**
     * 모든 스케줄의 날씨 정보 업데이트 -> 추후 위치 위주 업데이트로 변경 필요
     * @throws IOException
     */
    public void notice() throws IOException {

        List<Schedule> schedules = scheduleRepository.findAll();
        List<Address> addresses = addressRepository.addressAll();

        for(Address address: addresses){
            weatherStationManager.addLocation(address.getCode());
        }

        for(Schedule schedule: schedules){
            Address addr = schedule.getAddress();
            weatherStationManager.addObserverToStation(addr.getCode(), schedule.getTime(), schedule.getId());
            weatherStationManager.parse(addr.getCode(), apiRequest(addr.getLocation().getNx(), addr.getLocation().getNy()));
        }
        weatherStationManager.print();
    }

    public List<ScheduleItemDto> itemsBySchedule(Long memberId){
        weatherStationManager.print();
        Member member = memberRepository.findOne(memberId);
        List<ScheduleItemDto> itemList=new ArrayList<>();

        for(Schedule schedule: member.getSchedules()){
            WeatherObserver observer = weatherStationManager.findObserver(schedule.getAddress().getCode(),
                    schedule.getTime(),
                    schedule.getId());
            itemList.add(new ScheduleItemDto(schedule.getTime(), observer.getItemList()));
        }

        return itemList.stream().sorted().collect(Collectors.toList());
    }

    /**
     * 해당 장소의 시간별 결과 open api 요청 & WeatherStation에 세팅
     * @return
     * @throws IOException
     */
    public String apiRequest(String nx, String ny) throws IOException{
        String today= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0800", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
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
        return sb.toString();
    }
}
