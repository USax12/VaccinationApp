package com.example.vaccNow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vaccNow.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
