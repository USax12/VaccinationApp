package com.example.vaccNow.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.vaccNow.exception.CustomException.TimeNotFoundException;
import com.example.vaccNow.exception.CustomException.VaccineNotFoundException;
import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.repository.BranchRepository;
import com.example.vaccNow.repository.ScheduleRepository;
import com.example.vaccNow.repository.VaccineRepository;

@SpringBootTest
public class AvailabilityServiceImplTest {

	@Mock
	private BranchRepository branchRepository;

	@Mock
	private VaccineRepository vaccineRepository;

	@Mock
	private ScheduleRepository scheduleRepository;

	@InjectMocks
	private AvailabilityServiceImpl availabilityService;

	@Test
	public void testGetAllBranches() {
		// Mock data for the test
		List<Branch> mockBranches = new ArrayList<>();
		when(branchRepository.findAll()).thenReturn(mockBranches);

		List<Branch> result = availabilityService.getAllBranches();

		assertEquals(mockBranches, result);
	}

	@Test
	public void testGetVaccinesByBranch() {

		Long branchId = 1L;
		when(vaccineRepository.findByBranchId(branchId)).thenReturn(Collections.emptyList());

		try {
			availabilityService.getVaccinesByBranch(branchId);
			// If no exception is thrown, the test should fail
			fail("Expected VaccineNotFoundException, but no exception was thrown");
		} catch (VaccineNotFoundException e) {
			// Expected exception
			assertEquals("Vaccines not found for branch with ID 1", e.getMessage());
		}
	}

	@Test
	public void testGetAvailabilityByBranch() {
		// Mock data for the test
		Long branchId = 1L;
		Schedule mockAvailability = new Schedule(); // Provide a mock Schedule object
		Optional<Schedule> optionalAvailability = Optional.of(mockAvailability);
		when(scheduleRepository.findFirstByBranchIdOrderByAvailableTimeAsc(branchId)).thenReturn(optionalAvailability);

		Schedule result = availabilityService.getAvailabilityByBranch(branchId);

		assertEquals(mockAvailability, result);
	}

	@Test
	public void testGetAvailableTimesForBranch() {

		Long branchId = 1L;
		when(scheduleRepository.findByBranchIdOrderByAvailableTimeAsc(branchId)).thenReturn(Collections.emptyList());

		try {
			availabilityService.getAvailableTimesForBranch(branchId);
			// If no exception is thrown, the test should fail
			fail("Expected TimeNotFoundException, but no exception was thrown");
		} catch (TimeNotFoundException e) {
			// Expected exception
			assertEquals("Available times not found for branch with ID 1", e.getMessage());
		}
	}
}
