package com.example.vaccNow.model;

public class ScheduleRequest {

	private Long branchId;
	private String selectedDate;
	private String selectedTime;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getSelectedTime() {
		return selectedTime;
	}

	public void setSelectedTime(String selectedTime) {
		this.selectedTime = selectedTime;
	}

}
