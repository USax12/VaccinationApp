package com.example.vaccNow.service;

import java.util.List;

import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.Vaccine;

public interface AvailabilityService {

	List<Branch> getAllBranches();

	List<Vaccine> getVaccinesByBranch(Long branchId);

	Schedule getAvailabilityByBranch(Long branchId);

	List<Schedule> getAvailableTimesForBranch(Long branchId);

}
