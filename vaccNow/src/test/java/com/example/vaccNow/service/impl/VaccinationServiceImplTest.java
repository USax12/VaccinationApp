package com.example.vaccNow.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.vaccNow.exception.CustomException.InvalidPaymentMethodException;
import com.example.vaccNow.exception.CustomException.PaymentAlreadyMadeException;
import com.example.vaccNow.exception.CustomException.TimeslotNotAvailableException;
import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.ScheduleResponse;
import com.example.vaccNow.repository.BranchRepository;
import com.example.vaccNow.repository.ScheduleRepository;

@ExtendWith(MockitoExtension.class)
class VaccinationServiceImplTest {

	@Mock
	private ScheduleRepository scheduleRepository;

	@Mock
	private BranchRepository branchRepository;

	@InjectMocks
	private VaccinationServiceImpl vaccinationService;

	@Test
	void testScheduleVaccination() {
		// Mock data for the test
		Long branchId = 1L;
		String selectedDate = "2022-12-01";
		String selectedTime = "10:00:00";
		String userEmail = "test@example.com";
		Branch branch = new Branch();
		branch.setId(branchId);
		when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
		when(scheduleRepository.findLatestBookingEndTime(branchId)).thenReturn(null);
		when(scheduleRepository.save(any())).thenReturn(new Schedule());

		// Call the method to be tested
		ScheduleResponse response = vaccinationService.scheduleVaccination(branchId, selectedDate, selectedTime,
				userEmail);

		// Assertions
		assertNotNull(response);
	}

	@Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	void testScheduleVaccinationTimeslotNotAvailable() {
		// Mock data for the test
		Long branchId = 1L;
		String selectedDate = "2022-12-01";
		String selectedTime = "10:00:00";
		String userEmail = "test@example.com";
		LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDate + "T" + selectedTime);
		Branch branch = new Branch();
		branch.setId(branchId);
		when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
		when(scheduleRepository.findLatestBookingEndTime(branchId)).thenReturn(selectedDateTime.minusMinutes(10));

		// Call the method to be tested
		try {
			vaccinationService.scheduleVaccination(branchId, selectedDate, selectedTime, userEmail);
			// If no exception is thrown, the test should fail
			fail("Expected TimeslotNotAvailableException, but no exception was thrown");
		} catch (TimeslotNotAvailableException e) {
			// Expected exception
			assertEquals("Available times Slot not available ", e.getMessage());
		}
	}

	@Test
	void testChoosePaymentMethod() {
		// Mock data for the test
		Long scheduleId = 1L;
		String paymentMethod = "cash";
		Schedule schedule = new Schedule();
		schedule.setId(scheduleId);
		schedule.setVaccinationScheduled(true);
		when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
		when(scheduleRepository.existsByIdAndPaymentMade(scheduleId, true)).thenReturn(false);

		// Call the method to be tested
		String result = vaccinationService.choosePaymentMethod(scheduleId, paymentMethod);

		// Assertions
		assertEquals("Payment successful", result);
		assertTrue(schedule.isPaymentMade());
		assertEquals("Cash", schedule.getPaymentMethod());
		verify(scheduleRepository, times(1)).save(schedule);
	}

	@Test
	void testChoosePaymentMethodPaymentAlreadyMade() {
		// Mock data for the test
		Long scheduleId = 1L;
		String paymentMethod = "credit";
		Schedule schedule = new Schedule();
		schedule.setId(scheduleId);
		schedule.setVaccinationScheduled(true);
		schedule.setPaymentMade(true); // Set payment as already made
		when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
		when(scheduleRepository.existsByIdAndPaymentMade(scheduleId, true)).thenReturn(true); // Change to true

		// Call the method to be tested
		try {
			vaccinationService.choosePaymentMethod(scheduleId, paymentMethod);
			// If no exception is thrown, the test should fail
			fail("Expected PaymentAlreadyMadeException, but no exception was thrown");
		} catch (PaymentAlreadyMadeException e) {
			// Expected exception
			assertEquals("Payment has already been made for this schedule.", e.getMessage());
		}
	}

	@Test
	void testChoosePaymentMethodVaccinationNotScheduled() {
		// Mock data for the test
		Long scheduleId = 1L;
		String paymentMethod = "fawry";
		Schedule schedule = new Schedule();
		schedule.setId(scheduleId);
		schedule.setVaccinationScheduled(false);
		when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

		// Call the method to be tested
		try {
			vaccinationService.choosePaymentMethod(scheduleId, paymentMethod);
			// If no exception is thrown, the test should fail
			fail("Expected TimeslotNotAvailableException, but no exception was thrown");
		} catch (TimeslotNotAvailableException e) {
			// Expected exception
			assertEquals("Available times Slot not available ", e.getMessage());
		}
	}

	@Test
	void testChoosePaymentMethodInvalidPaymentMethod() {
		// Mock data for the test
		Long scheduleId = 1L;
		String paymentMethod = "invalidPaymentMethod";
		Schedule schedule = new Schedule();
		schedule.setId(scheduleId);
		schedule.setVaccinationScheduled(true);
		when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

		// Call the method to be tested
		try {
			vaccinationService.choosePaymentMethod(scheduleId, paymentMethod);
			// If no exception is thrown, the test should fail
			fail("Expected InvalidPaymentMethodException, but no exception was thrown");
		} catch (InvalidPaymentMethodException e) {
			// Expected exception
			assertEquals("", e.getMessage());
		}
	}
}
