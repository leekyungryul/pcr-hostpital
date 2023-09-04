package com.example.pcrhospital.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.example.pcrhospital.domain.Hospital;

public class HospDownloadBatchTest {

    String serviceKey = "";

    @Test
    public void test() throws URISyntaxException {
        // 1. 공공데이터 다운로드
        RestTemplate rt = new RestTemplate();
        String url = "https://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1/getRprtPtntDiagCnfcInfo1?serviceKey="+serviceKey+"&pageNo=1&numOfRows=10&_type=json";
        ResponseDto dto = rt.getForObject(new URI(url), ResponseDto.class);
        System.out.println(dto);
        List<Item> hospitals = dto.getResponse().getBody().getItems().getItem();
        for (Item item : hospitals) {
            System.out.println(item.getYadmNm());
            System.out.println("PCR 여부 : " + item.getDgmPrscPsblYn());
        }
    }

    /**
     * <pre>
     * 조회된 공공데이터 컬렉션에 담기
     * </pre>
     * @throws URISyntaxException
     */
    @Test
    public void download() throws URISyntaxException {

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

        assertEquals(totalCount, items.size());

    }
}
