package com.example.vaccNow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vaccine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "BRANCH_ID")
	private Long branchId;

	public Vaccine() {
		super();
	}

	public Vaccine(Long id, Long branchId) {
		super();
		this.id = id;
		this.branchId = branchId;
	}

}