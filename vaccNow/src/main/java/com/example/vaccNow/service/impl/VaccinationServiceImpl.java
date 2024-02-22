package com.example.vaccNow.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.vaccNow.exception.CustomException.BranchNotFoundException;
import com.example.vaccNow.exception.CustomException.InvalidEmailException;
import com.example.vaccNow.exception.CustomException.InvalidPaymentMethodException;
import com.example.vaccNow.exception.CustomException.PaymentAlreadyMadeException;
import com.example.vaccNow.exception.CustomException.PaymentNotMadeException;
import com.example.vaccNow.exception.CustomException.ScheduleAlreadyConfirmedException;
import com.example.vaccNow.exception.CustomException.ScheduleNotFoundException;
import com.example.vaccNow.exception.CustomException.TimeslotNotAvailableException;
import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.ScheduleResponse;
import com.example.vaccNow.repository.BranchRepository;
import com.example.vaccNow.repository.ScheduleRepository;
import com.example.vaccNow.service.VaccinationService;
import com.example.vaccNow.utils.EmailValidator;

@Service
public class VaccinationServiceImpl implements VaccinationService {

	private final ScheduleRepository scheduleRepository;

	private final BranchRepository branchRepository;

	public VaccinationServiceImpl(ScheduleRepository scheduleRepository, BranchRepository branchRepository) {
		this.scheduleRepository = scheduleRepository;
		this.branchRepository = branchRepository;
	}

	@Override
	public ScheduleResponse scheduleVaccination(Long branchId, String selectedDate, String selectedTime,
			String userEmail) {
		LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDate + "T" + selectedTime);

		// Validate the email address
		if (!isValidEmail(userEmail)) {
			throw new InvalidEmailException("Invalid email address: " + userEmail);
		}

		if (!isTimeslotAvailable(branchId, selectedDateTime)) {
			throw new TimeslotNotAvailableException("Selected timeslot is not available.");
		}

		Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new BranchNotFoundException(branchId));

		Schedule scheduledTimeslot = new Schedule();
		scheduledTimeslot.setAvailableTime(selectedDateTime);
		scheduledTimeslot.setBranch(branch);
		scheduledTimeslot.setVaccinationScheduled(true);
		scheduledTimeslot.setUserEmail(userEmail);
		Schedule savedSchedule = scheduleRepository.save(scheduledTimeslot);

		return new ScheduleResponse(savedSchedule.getId(), savedSchedule.getAvailableTime());
	}

	private boolean isTimeslotAvailable(Long branchId, LocalDateTime selectedDateTime) {

		// Find the latest existing booking end time in the database for the branch
		LocalDateTime latestBookingEndTime = scheduleRepository.findLatestBookingEndTime(branchId);

		return latestBookingEndTime == null || selectedDateTime.isAfter(latestBookingEndTime.plusMinutes(15));

	}

	private boolean isValidEmail(String email) {
		return EmailValidator.isValidEmail(email);
	}

	@Override
	public String choosePaymentMethod(Long scheduleId, String paymentMethod) {
		// Fetch the corresponding Schedule
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

		// Check if payment has already been made for this schedule
		if (scheduleRepository.existsByIdAndPaymentMade(scheduleId, true)) {
			throw new PaymentAlreadyMadeException("Payment has already been made for this schedule.");
		}

		// Check if vaccination has been scheduled for this slot
		if (!schedule.isVaccinationScheduled()) {
			throw new TimeslotNotAvailableException("Vaccination has not been scheduled for this timeslot.");
		}

		// Perform payment logic based on the chosen payment method
		switch (paymentMethod.toLowerCase()) {
		case "cash":
			updatePaymentStatus(schedule, "Cash");
			break;
		case "credit":
			updatePaymentStatus(schedule, "Credit");
			break;
		case "fawry":
			updatePaymentStatus(schedule, "Fawry");
			break;
		default:
			throw new InvalidPaymentMethodException(paymentMethod);
		}

		return "Payment successful";
	}

	private void updatePaymentStatus(Schedule schedule, String paymentMethod) {

		schedule.setPaymentMade(true);
		schedule.setPaymentMethod(paymentMethod);
		scheduleRepository.save(schedule);
	}

	@Override
	public String confirmScheduledVaccinationByEmail(Long scheduleId, String userEmail) {
		// Validate the user's email and scheduleId

		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

		if (!userEmail.equals(schedule.getUserEmail())) {
			throw new InvalidEmailException("Invalid email for confirmation");
		}

		if (!schedule.isPaymentMade()) {
			throw new PaymentNotMadeException("Payment has not been made for this schedule");
		}

		if (schedule.isConfirmed()) {
			throw new ScheduleAlreadyConfirmedException("Schedule is already confirmed");
		}

		schedule.setConfirmed(true);
		scheduleRepository.save(schedule);

		return "Vaccination schedule confirmed successfully";
	}

}
