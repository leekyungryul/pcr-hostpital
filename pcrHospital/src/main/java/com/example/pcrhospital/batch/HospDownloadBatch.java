package com.example.pcrhospital.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 하루에 한번씩 다운로드해서 db에 저장하는 batch
 * 병원이 추가된다면 추가된 병원만 db에 저장하도록 하는것이 복잡하기 때문에 기존 데이터를 삭제하고 전체를 다운로드해서 db에 저장한다.
 * </pre>
 */
@Component
public class HospDownloadBatch {

    // 초 분 시 일 월 주
    @Scheduled(cron = "0 * * * * *")
    public void startBatch() {
        System.out.println("download");
    }
}
