package com.example.pcrhospital.batch;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.pcrhospital.domain.Hospital;
import com.example.pcrhospital.domain.HospitalRepository;

import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * 하루에 한번씩 다운로드해서 db에 저장하는 batch
 * 병원이 추가된다면 추가된 병원만 db에 저장하도록 하는것이 복잡하기 때문에 기존 데이터를 삭제하고 전체를 다운로드해서 db에 저장한다.
 * </pre>
 */
@RequiredArgsConstructor
@Component
public class HospDownloadBatch {

    private final HospitalRepository hospRepository;

    @Value("${api.key}")
    String serviceKey;

    // 초 분 시 일 월 주
    @Scheduled(cron = "0 51 * * * *")
    public void startBatch() throws URISyntaxException {
        List<Hospital> hospitals = new ArrayList<>();

        RestTemplate rt = new RestTemplate();

        int totalCount = 2;
        String url = "https://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1/getRprtPtntDiagCnfcInfo1?" +
                "serviceKey="+ serviceKey +
                "&pageNo=1" +
                "&_type=json" +
                "&numOfRows=";

        ResponseDto totalCntDto = rt.getForObject(new URI(url+totalCount), ResponseDto.class);
        totalCount = totalCntDto.getResponse().getBody().getTotalCount();


        ResponseDto responseDto = rt.getForObject(new URI(url+totalCount), ResponseDto.class);

        List<Item> items = responseDto.getResponse().getBody().getItems().getItem();

        System.out.println("totalCount = " + items.size());

        items.stream().map(item -> {
            Hospital hospital = new Hospital();
            hospital.setYkiho(item.getYkiho());
            hospital.setAddr(item.getAddr());
            hospital.setCv19ExmTyCd(item.getCv19ExmTyCd());
            hospital.setDgmPrscPsblYn(item.getDgmPrscPsblYn());
            hospital.setYadmNm(item.getYadmNm());
            hospital.setDiagBknPsblTelno(item.getDiagBknPsblTelno());
            hospital.setInfcPtntDiagTyCd(item.getInfcPtntDiagTyCd());
            hospital.setOnstpMadmYn(item.getOnstpMadmYn());
            hospital.setRprtPtntDiagPsblYn(item.getRprtPtntDiagPsblYn());
            hospital.setSidoNm(item.getSidoNm());
            hospital.setSgguNm(item.getSgguNm());
            return hospital;
        }).forEach(hospitals::add);

//        hospitals = items.stream().map((e) -> {
//            return Hospital.builder()
//                    .addr(e.getAddr())
//                    .cv19ExmTyCd(e.getCv19ExmTyCd())
//                    .dgmPrscPsblYn(e.getDgmPrscPsblYn())
//                    .diagBknPsblTelno(e.getDiagBknPsblTelno())
//                    .infcPtntDiagTyCd(e.getInfcPtntDiagTyCd())
//                    .onstpMadmYn(e.getOnstpMadmYn())
//                    .infcPtntDiagTyCd(e.getInfcPtntDiagTyCd())
//                    .onstpMadmYn(e.getOnstpMadmYn())
//                    .rprtPtntDiagPsblYn(e.getRprtPtntDiagPsblYn())
//                    .sgguNm(e.getSgguNm())
//                    .sidoNm(e.getSidoNm())
//                    .yadmNm(e.getYadmNm())
//                    .ykiho(e.getYkiho())
//                    .build();
//        }).collect(Collectors.toList());
        //기존 데이터 삭제
//        hospRepository.deleteAll();
        //배치시간에 맞춰서 다운로드
        hospRepository.saveAll(hospitals);
    }
}
