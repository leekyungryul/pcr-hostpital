package com.example.pcrhospital.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.pcrhospital.domain.Hospital;
import com.example.pcrhospital.domain.HospitalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HospitalController {

    private final HospitalRepository repository;

    @GetMapping("/")
    public String home(String sidoNm, String sgguNm, Model model) {
        model.addAttribute("sidoNms", repository.mFindSidoNm());
        model.addAttribute("sgguNms", repository.mFindSgguNm("강원"));

        if (sidoNm!=null && sgguNm!=null) {
            model.addAttribute("hospitals", repository.mFindHospital(sidoNm, sgguNm));
        }

        return "index"; //templates/index.mustache
    }

    // http://localhost:8080/api/hospital?sidoNm=강원&sgguNm=강릉시
    @GetMapping("/api/hospital")
    public @ResponseBody List<Hospital> hospitals(String sidoNm, String sgguNm) {
        return repository.mFindHospital(sidoNm, sgguNm);
    }

    @GetMapping("/api/sgguNm")
    public @ResponseBody List<String> sgguNm(String sidoNm) {
        return repository.mFindSgguNm(sidoNm);
    }

}
