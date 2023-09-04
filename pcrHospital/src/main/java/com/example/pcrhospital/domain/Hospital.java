package com.example.pcrhospital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //id
    private String addr; //주소
    private String cv19ExmTyCd; //진단검사 방법
    private String dgmPrscPsblYn; //진단검사 가능여부
    private String diagBknPsblTelno; //진단가능전화번호
    private String infcPtntDiagTyCd; //확진환자 진단방법
    private String onstpMadmYn; //입원가능여부
    private String rprtPtntDiagPsblYn; //확진환자 진단가능여부
    private String sgguNm; //시군구명
    private String sidoNm; //시도명
    private String yadmNm; //병원명
    private String ykiho; //암호화된 요양기호

}
