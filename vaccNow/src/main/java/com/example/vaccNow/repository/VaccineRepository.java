package com.example.vaccNow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vaccNow.model.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
	List<Vaccine> findByBranchId(Long branchId);
}
