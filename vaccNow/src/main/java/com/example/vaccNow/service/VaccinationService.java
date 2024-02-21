package com.example.vaccNow.service;

import com.example.vaccNow.model.ScheduleResponse;

public interface VaccinationService {

	ScheduleResponse scheduleVaccination(Long branchId, String selectedDate, String selectedTime);

	String choosePaymentMethod(Long scheduleId, String paymentMethod);

}
