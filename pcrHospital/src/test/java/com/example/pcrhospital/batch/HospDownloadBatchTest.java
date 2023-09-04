package com.example.pcrhospital.batch;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class HospDownloadBatchTest {

    @Test
    public void test() throws URISyntaxException {
        // 1. 공공데이터 다운로드
        RestTemplate rt = new RestTemplate();
        String url = "https://apis.data.go.kr/B551182/rprtPtntDiagCnfcInfoService1/getRprtPtntDiagCnfcInfo1?serviceKey=Mi509VSdtp6o1yGDQSIeGFqrcsfCyU9Dig%2F0dIDK5IdUUoT7P7xVU48wmnUEF1LS5VQ1ueuZ2PLm6Pt%2BT8X7Bw==&pageNo=1&numOfRows=10&_type=json";
        ResponseDto dto = rt.getForObject(new URI(url), ResponseDto.class);
        System.out.println(dto);
        List<Item> hospitals = dto.getResponse().getBody().getItems().getItem();
        for (Item item : hospitals) {
            System.out.println(item.getYadmNm());
            System.out.println("PCR 여부 : " + item.getDgmPrscPsblYn());
        }
    }
}
