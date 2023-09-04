package com.example.pcrhospital.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    /**
     * 시도명, 시군구명으로 병원 찾기
     * @param sidoNm
     * @param sgguNm
     * @return
     */
    @Query(value = "SELECT * FROM hospital where sidoNm = :sidoNm and sgguNm = :sgguNm and dgmPrscPsblYn = 'Y'", nativeQuery = true)
    List<Hospital> mFindHospital(@Param("sidoNm") String sidoNm, @Param("sgguNm") String sgguNm);

    /**
     * 시도명 목록 불러오기
     * @param sidoNm
     * @return
     */
    @Query(value = "SELECT distinct sidoNm FROM hospital order by sidoNm", nativeQuery = true)
    List<String> mFindSidoNm();

    /**
     * 시군구명 목록 불러오기
     * @param sidoNm
     * @return
     */
    @Query(value = "SELECT distinct sgguNm FROM hospital where sidoNm = :sidoNm order by sgguNm", nativeQuery = true)
    List<String> mFindSgguNm(@Param("sidoNm") String sidoNm);
}
