package com.example.vaccNow.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vaccNow.exception.CustomException.NoAppliedVaccinationsException;
import com.example.vaccNow.exception.CustomException.NoConfirmedVaccinationsException;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.repository.ScheduleRepository;
import com.example.vaccNow.service.ReportingService;

@Service
public class ReportingServiceImpl implements ReportingService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	public List<Schedule> getAppliedVaccinationsByBranch(Long branchId) {
		// Fetch applied vaccinations based on branchId
		List<Schedule> appliedVaccinations = scheduleRepository.findAppliedVaccinationsByBranch(branchId);
		if (appliedVaccinations.isEmpty()) {

			throw new NoAppliedVaccinationsException("No applied vaccinations found for branch with ID " + branchId);
		}

		return appliedVaccinations;
	}

	@Override
	public List<Schedule> getAppliedVaccinationsByDay(LocalDate date) {
		// Fetch applied vaccinations for a specific day
		LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.MAX);
		List<Schedule> appliedVaccinations = scheduleRepository.findAppliedVaccinationsByDay(startDateTime,
				endDateTime);

		if (appliedVaccinations.isEmpty()) {

			throw new NoAppliedVaccinationsException("No applied vaccinations found for the day: " + date);
		}

		return appliedVaccinations;
	}

	@Override
	public List<Schedule> getConfirmedVaccinationsOverTimePeriod(LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		List<Schedule> confirmedVaccinations = scheduleRepository.findConfirmedVaccinationsOverTimePeriod(startDateTime,
				endDateTime);

		if (confirmedVaccinations.isEmpty()) {
			throw new NoConfirmedVaccinationsException(
					"No confirmed vaccinations found over the specified time period.");
		}

		return confirmedVaccinations;
	}
}
