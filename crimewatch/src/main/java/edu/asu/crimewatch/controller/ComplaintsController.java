package edu.asu.crimewatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.crimewatch.service.ComplaintsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/complaints")
public class ComplaintsController {

    @Autowired
    ComplaintsService complaintsService;

    @GetMapping("/getYears")
    ResponseEntity<?> getYearList() {
        List<Integer> yearList = complaintsService.fetchYearValues();
        return ResponseEntity.ok(yearList);
    }

    @GetMapping("/getCrimeTypeCountByYear")
    ResponseEntity<?> getCrimeTypeCount(@RequestParam("year") int year, @RequestParam("sex") String sex) {
        Map<String, Integer> crimeTypeCount = complaintsService.fetchCrimeTypeCountByYear(year, sex);
        return ResponseEntity.ok(crimeTypeCount);
    }

    @GetMapping("/getBoroCrimeCountByYear")
    ResponseEntity<?> getBoroCrimeCountByYear(@RequestParam("year") int year) {
        Map<String, Integer> boroCrimeCount = complaintsService.fetchCrimeCountByBoro(year);
        return ResponseEntity.ok(boroCrimeCount);
    }

    @GetMapping("/getBoroMonthlyStats")
    ResponseEntity<?> getBoroMonthlyStats(@RequestParam("year") int year) {
        Map<String, List<Integer>> boroMonthlyStats = complaintsService.fetchBoroMonthlyStats(year);
        return ResponseEntity.ok(boroMonthlyStats);
    }

}
