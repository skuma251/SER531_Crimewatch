package edu.asu.crimewatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.crimewatch.service.MiscellaneousService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/miscellaneous")
public class MiscellaneousController {

    @Autowired
    MiscellaneousService miscellaneousService;

    @GetMapping("/getComplaintShootingCountByYear")
    ResponseEntity<?> getCrimeTypeCount(@RequestParam("year") int year) {
        Map<String, List<Integer>> complaintShootingCount = miscellaneousService.fetchShootingHateCountByYear(year);
        return ResponseEntity.ok(complaintShootingCount);
    }
}