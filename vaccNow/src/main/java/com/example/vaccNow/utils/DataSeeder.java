package com.example.vaccNow.utils;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.vaccNow.model.Branch;
import com.example.vaccNow.model.Schedule;
import com.example.vaccNow.model.Vaccine;
import com.example.vaccNow.repository.BranchRepository;
import com.example.vaccNow.repository.ScheduleRepository;
import com.example.vaccNow.repository.VaccineRepository;

@Component
public class DataSeeder implements CommandLineRunner {

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private VaccineRepository vaccineRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	public void run(String... args) throws Exception {

		// Populate data for Branch
		Branch branch1 = new Branch(1L, "Branch Test1");
		Branch branch2 = new Branch(2L, "Branch Test2");
		branchRepository.saveAll(Arrays.asList(branch1, branch2));

		// Populate data for Vaccine

		Vaccine vaccine1 = new Vaccine(1L, 1L);
		Vaccine vaccine2 = new Vaccine(2L, 1L);
		Vaccine vaccine3 = new Vaccine(3L, 2L);
		vaccineRepository.saveAll(Arrays.asList(vaccine1, vaccine2, vaccine3));

		// Populate data for Schedule
		// Create Schedule entities with vaccination scheduled
		Schedule schedule1 = new Schedule(1L, LocalDateTime.now().plusDays(1), branch1, false, false, null);
		Schedule schedule2 = new Schedule(1L, LocalDateTime.now().plusDays(2), branch1, false, false, null);
		Schedule schedule3 = new Schedule(2L, LocalDateTime.now().plusDays(3), branch2, false, false, null);
		Schedule schedule4 = new Schedule(3L, LocalDateTime.now().plusDays(4), branch2, false, false, null);
		scheduleRepository.saveAll(Arrays.asList(schedule1, schedule2, schedule3, schedule4));

	}
}
