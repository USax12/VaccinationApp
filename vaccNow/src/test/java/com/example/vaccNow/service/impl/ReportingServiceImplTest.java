package com.example.vaccNow.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.vaccNow.exception.CustomException.NoAppliedVaccinationsException;
import com.example.vaccNow.exception.CustomException.NoConfirmedVaccinationsException;
import com.example.vaccNow.repository.ScheduleRepository;

@ExtendWith(MockitoExtension.class)
class ReportingServiceImplTest {

	@Mock
	private ScheduleRepository scheduleRepository;

	@InjectMocks
	private ReportingServiceImpl reportingService;

	@Test
	void testGetAppliedVaccinationsByBranch() {

		Long branchId = 1L;
		when(scheduleRepository.findAppliedVaccinationsByBranch(branchId)).thenReturn(Collections.emptyList());

		try {
			reportingService.getAppliedVaccinationsByBranch(branchId);
			// If no exception is thrown, the test should fail
			fail("Expected NoAppliedVaccinationsException, but no exception was thrown");
		} catch (NoAppliedVaccinationsException e) {

			assertEquals("No applied vaccinations found for branch with ID 1", e.getMessage());
		}
	}

	@Test
	void testGetAppliedVaccinationsByDay() {

		LocalDate date = LocalDate.now();
		LocalDateTime startDateTime = LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime());
		LocalDateTime endDateTime = LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime());
		when(scheduleRepository.findAppliedVaccinationsByDay(startDateTime, endDateTime))
				.thenReturn(Collections.emptyList());

		// Call the method to be tested
		try {
			reportingService.getAppliedVaccinationsByDay(date);
			// If no exception is thrown, the test should fail
			fail("Expected NoAppliedVaccinationsException, but no exception was thrown");
		} catch (NoAppliedVaccinationsException e) {
			// Expected exception
			assertEquals("No applied vaccinations found for the day: " + date, e.getMessage());
		}
	}

	@Test
	void testGetConfirmedVaccinationsOverTimePeriod() {

		LocalDateTime startDateTime = LocalDateTime.now().minusDays(7);
		LocalDateTime endDateTime = LocalDateTime.now();
		when(scheduleRepository.findConfirmedVaccinationsOverTimePeriod(startDateTime, endDateTime))
				.thenReturn(Collections.emptyList());

		// Call the method to be tested
		try {
			reportingService.getConfirmedVaccinationsOverTimePeriod(startDateTime, endDateTime);
			// If no exception is thrown, the test should fail
			fail("Expected NoConfirmedVaccinationsException, but no exception was thrown");
		} catch (NoConfirmedVaccinationsException e) {
			// Expected exception
			assertEquals("No confirmed vaccinations found over the specified time period.", e.getMessage());
		}
	}
}
