package com.example.vaccNow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vaccNow.model.ScheduleRequest;
import com.example.vaccNow.model.ScheduleResponse;
import com.example.vaccNow.service.VaccinationService;

@RestController
@RequestMapping("/api/vaccination")
public class VaccinationController {

	private final VaccinationService vaccinationService;

	public VaccinationController(VaccinationService vaccinationService) {
		this.vaccinationService = vaccinationService;
	}

	@PostMapping("/schedule")
	public ResponseEntity<String> scheduleVaccination(@RequestBody ScheduleRequest scheduleRequest) {
		ScheduleResponse scheduledDateTime = vaccinationService.scheduleVaccination(scheduleRequest.getBranchId(),
				scheduleRequest.getSelectedDate(), scheduleRequest.getSelectedTime(), scheduleRequest.getEmailId());

		return ResponseEntity.ok("Vaccination scheduled successfully at: " + scheduledDateTime.getSelectedDateTime()
				+ " with ScheduleId " + scheduledDateTime.getScheduleId());
	}

	@PostMapping("/payment")
	public ResponseEntity<String> choosePaymentMethod(@RequestParam Long scheduleId,
			@RequestParam String paymentMethod) {
		String result = vaccinationService.choosePaymentMethod(scheduleId, paymentMethod);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/confirm-schedule")
	public ResponseEntity<String> confirmScheduledVaccinationByEmail(@RequestParam Long scheduleId,
			@RequestParam String userEmail) {
		String result = vaccinationService.confirmScheduledVaccinationByEmail(scheduleId, userEmail);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
