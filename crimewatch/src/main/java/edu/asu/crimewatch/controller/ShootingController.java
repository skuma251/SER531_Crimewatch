package edu.asu.crimewatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.crimewatch.service.ShootingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shooting")
public class ShootingController {

    @Autowired
    ShootingService shootingService;

    @GetMapping("/getYears")
    ResponseEntity<?> getYearList() {
        List<Integer> yearList = shootingService.fetchYearValues();
        return ResponseEntity.ok(yearList);
    }

    @GetMapping("/getVictimCountByBoro")
    ResponseEntity<?> getVictimCountByBoro(@RequestParam("year") int year, @RequestParam("boro") String boro) {
        Map<String, Integer> boroVictimCount = shootingService.getVictimCountByRace(year, boro);
        return ResponseEntity.ok(boroVictimCount);
    }

    @GetMapping("/getPerpCountByBoro")
    ResponseEntity<?> getPerpCountByBoro(@RequestParam("year") int year, @RequestParam("boro") String boro) {
        Map<String, Integer> boroPerpCount = shootingService.getPerpCountByRace(year, boro);
        return ResponseEntity.ok(boroPerpCount);
    }

    @GetMapping("/getBoroCountByYear")
    ResponseEntity<?> getBoroCountByYear(@RequestParam("year") int year) {
        Map<String, Integer> boroCount = shootingService.getCountByYear(year);
        return ResponseEntity.ok(boroCount);
    }

}
