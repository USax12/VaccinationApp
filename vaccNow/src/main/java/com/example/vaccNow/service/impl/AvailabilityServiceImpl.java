package com.example.vaccNow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vaccNow.exception.CustomException.AvailabilityNotFoundException;
import com.example.vaccNow.exception.CustomException.TimeNotFoundException;
import com.example.vaccNow.exception.CustomException.VaccineNotFoundException;
import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.Vaccine;
import com.example.vaccNow.repository.BranchRepository;
import com.example.vaccNow.repository.ScheduleRepository;
import com.example.vaccNow.repository.VaccineRepository;
import com.example.vaccNow.service.AvailabilityService;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private VaccineRepository vaccineRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	// Get a list of all branches
	public List<Branch> getAllBranches() {
		return branchRepository.findAll();
	}

	public List<Vaccine> getVaccinesByBranch(Long branchId) {
		List<Vaccine> vaccines = vaccineRepository.findByBranchId(branchId);
		if (vaccines.isEmpty()) {
			throw new VaccineNotFoundException(branchId);
		}
		return vaccines;
	}

	public Schedule getAvailabilityByBranch(Long branchId) {
		Optional<Schedule> availability = scheduleRepository.findFirstByBranchIdOrderByAvailableTimeAsc(branchId);
		return availability.orElseThrow(() -> new AvailabilityNotFoundException(branchId));
	}

	public List<Schedule> getAvailableTimesForBranch(Long branchId) {
		List<Schedule> availableTimes = scheduleRepository.findByBranchIdOrderByAvailableTimeAsc(branchId);
		if (availableTimes.isEmpty()) {
			throw new TimeNotFoundException(branchId);
		}
		return availableTimes;
	}
}
