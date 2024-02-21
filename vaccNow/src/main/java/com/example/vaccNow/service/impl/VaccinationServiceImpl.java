package com.example.vaccNow.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.vaccNow.exception.CustomException.BranchNotFoundException;
import com.example.vaccNow.exception.CustomException.InvalidPaymentMethodException;
import com.example.vaccNow.exception.CustomException.PaymentAlreadyMadeException;
import com.example.vaccNow.exception.CustomException.ScheduleNotFoundException;
import com.example.vaccNow.exception.CustomException.TimeslotNotAvailableException;
import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.ScheduleResponse;
import com.example.vaccNow.repository.BranchRepository;
import com.example.vaccNow.repository.ScheduleRepository;
import com.example.vaccNow.service.VaccinationService;

@Service
public class VaccinationServiceImpl implements VaccinationService {

	private final ScheduleRepository scheduleRepository;

	private final BranchRepository branchRepository;

	public VaccinationServiceImpl(ScheduleRepository scheduleRepository, BranchRepository branchRepository) {
		this.scheduleRepository = scheduleRepository;
		this.branchRepository = branchRepository;
	}

	@Override
	public ScheduleResponse scheduleVaccination(Long branchId, String selectedDate, String selectedTime) {
		LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDate + "T" + selectedTime);

		if (!isTimeslotAvailable(branchId, selectedDateTime)) {
			throw new TimeslotNotAvailableException("Selected timeslot is not available.");
		}

		Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new BranchNotFoundException(branchId));

		Schedule scheduledTimeslot = new Schedule();
		scheduledTimeslot.setAvailableTime(selectedDateTime);
		scheduledTimeslot.setBranch(branch);
		scheduledTimeslot.setVaccinationScheduled(true);
		Schedule savedSchedule = scheduleRepository.save(scheduledTimeslot);

		return new ScheduleResponse(savedSchedule.getId(), savedSchedule.getAvailableTime());
	}

	private boolean isTimeslotAvailable(Long branchId, LocalDateTime selectedDateTime) {

		// Find the latest existing booking end time in the database for the branch
		LocalDateTime latestBookingEndTime = scheduleRepository.findLatestBookingEndTime(branchId);

		// If there are no existing bookings or the input startDateTime is at least 15
		// minutes after the latest booking end time, it's available
		return latestBookingEndTime == null || selectedDateTime.isAfter(latestBookingEndTime.plusMinutes(15));

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

}
