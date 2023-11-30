package edu.asu.crimewatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.crimewatch.service.HateCrimeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hatecrime")
public class HateCrimeController {

    @Autowired
    HateCrimeService hateCrimeService;

    @GetMapping("/getYears")
    ResponseEntity<?> getYearList() {
        List<Integer> yearList = hateCrimeService.fetchYearValues();
        return ResponseEntity.ok(yearList);
    }

    @GetMapping("/getBoroCountByYear")
    ResponseEntity<?> getBoroCountByYear(@RequestParam("year") int year) {
        Map<String, Integer> boroCount = hateCrimeService.getBoroCountByYear(year);
        return ResponseEntity.ok(boroCount);
    }

    @GetMapping("/getBiasCountByYear")
    ResponseEntity<?> getBiasCountByYear(@RequestParam("year") int year) {
        Map<String, Integer> boroCount = hateCrimeService.getBiasCountByYear(year);
        return ResponseEntity.ok(boroCount);
    }

}
