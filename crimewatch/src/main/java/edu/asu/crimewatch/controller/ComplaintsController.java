package edu.asu.crimewatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.crimewatch.service.ComplaintsService;

@RestController
public class ComplaintsController {


    @Autowired
    ComplaintsService complaintsService;

    @GetMapping("/getComplaint")
    ResponseEntity<?> getComplaints() {
        complaintsService.getComplaintsbyYear();
        return ResponseEntity.ok("Greetings from Spring Boot!");
    }

}

