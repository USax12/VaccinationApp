package com.example.vaccNow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.vaccNow.model.Schedule;

public interface ReportingService {

	List<Schedule> getAppliedVaccinationsByBranch(Long branchId);

	List<Schedule> getAppliedVaccinationsByDay(LocalDate date);

	List<Schedule> getConfirmedVaccinationsOverTimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
