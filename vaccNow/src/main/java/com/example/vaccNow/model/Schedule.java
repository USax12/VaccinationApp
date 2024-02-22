package com.example.vaccNow.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "AVAILABLE_TIME")
	private LocalDateTime availableTime;

	@ManyToOne
	private Branch branch;

	@Column(name = "VACCINATION_SCHEDULED")
	private boolean vaccinationScheduled;

	@Column(name = "PAYMENT_MADE")
	private boolean paymentMade;

	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;

	@Column(name = "user_email")
	private String userEmail;
	
	@Column(name = "confirmed")
	private boolean confirmed;

	public Schedule() {
		super();
	}

	public Schedule(Long id, LocalDateTime availableTime, Branch branch, boolean vaccinationScheduled,
			boolean paymentMade, String paymentMethod) {
		super();
		this.id = id;
		this.availableTime = availableTime;
		this.branch = branch;
		this.vaccinationScheduled = false; // Default to false
		this.paymentMade = paymentMade;
		this.paymentMethod = paymentMethod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(LocalDateTime availableTime) {
		this.availableTime = availableTime;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public boolean isVaccinationScheduled() {
		return vaccinationScheduled;
	}

	public void setVaccinationScheduled(boolean vaccinationScheduled) {
		this.vaccinationScheduled = vaccinationScheduled;
	}

	public boolean isPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

}