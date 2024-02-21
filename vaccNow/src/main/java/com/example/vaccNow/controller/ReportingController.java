package com.example.vaccNow.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.service.ReportingService;

@RestController
@RequestMapping("/api/reporting")
public class ReportingController {

	@Autowired
	private ReportingService reportingService;

	@GetMapping("/applied-vaccinations/{branchId}")
	public ResponseEntity<List<Schedule>> getAppliedVaccinationsByBranch(@PathVariable Long branchId) {
		List<Schedule> appliedVaccinations = reportingService.getAppliedVaccinationsByBranch(branchId);
		return new ResponseEntity<>(appliedVaccinations, HttpStatus.OK);
	}

	// Get a list of all applied vaccination per day/period
	@GetMapping("/applied-vaccinations")
	public ResponseEntity<List<Schedule>> getAppliedVaccinationsByDay(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		List<Schedule> appliedVaccinations = reportingService.getAppliedVaccinationsByDay(date);
		return new ResponseEntity<>(appliedVaccinations, HttpStatus.OK);
	}

	// Show all confirmed vaccinations over a time period
	@GetMapping("/confirmed-vaccinations")
	public ResponseEntity<List<Schedule>> getConfirmedVaccinationsOverTimePeriod(
			@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {

		List<Schedule> confirmedVaccinations = reportingService.getConfirmedVaccinationsOverTimePeriod(startDateTime,
				endDateTime);

		return new ResponseEntity<>(confirmedVaccinations, HttpStatus.OK);
	}
}
