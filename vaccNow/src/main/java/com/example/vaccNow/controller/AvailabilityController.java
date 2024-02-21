package com.example.vaccNow.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.Vaccine;
import com.example.vaccNow.service.AvailabilityService;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

	private static final Logger log = LoggerFactory.getLogger(AvailabilityController.class);

	@Autowired
	private AvailabilityService vaccinationService;

	@GetMapping("/branches")
	public ResponseEntity<List<Branch>> getAllBranches() {
		List<Branch> branches = vaccinationService.getAllBranches();
		return new ResponseEntity<>(branches, HttpStatus.OK);
	}

	@GetMapping("/vaccines/{branchId}")
	public ResponseEntity<List<Vaccine>> getVaccinesByBranch(@PathVariable Long branchId) {
		List<Vaccine> vaccines = vaccinationService.getVaccinesByBranch(branchId);
		return new ResponseEntity<>(vaccines, HttpStatus.OK);
	}

	@GetMapping("/availability/{branchId}")
	public ResponseEntity<Schedule> getAvailabilityByBranch(@PathVariable Long branchId) {
		Schedule availability = vaccinationService.getAvailabilityByBranch(branchId);
		log.info("Retrieved availability: {}", availability);
		return new ResponseEntity<>(availability, HttpStatus.OK);
	}

	@GetMapping("/available-times/{branchId}")
	public ResponseEntity<List<Schedule>> getAvailableTimesForBranch(@PathVariable Long branchId) {
		List<Schedule> availableTimes = vaccinationService.getAvailableTimesForBranch(branchId);
		return new ResponseEntity<>(availableTimes, HttpStatus.OK);
	}
}
