package edu.asu.crimewatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.crimewatch.service.ComplaintsService;

import java.util.List;
import java.util.Map;

@RestController
public class ComplaintsController {


    @Autowired
    ComplaintsService complaintsService;

    @GetMapping("/getYears")
    ResponseEntity<?> getYearList() {
        List<Integer> yearList = complaintsService.fetchYearValues();
        return ResponseEntity.ok(yearList);
    }

    @GetMapping("/getCrimeTypeCountByYear")
    ResponseEntity<?> getCrimeTypeCount(@RequestParam("year") int year) {
        Map<String, Integer> crimeTypeCount = complaintsService.fetchCrimeTypeCountByYear(year);
        return ResponseEntity.ok(crimeTypeCount);
    }


}
