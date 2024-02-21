package com.example.vaccNow.model;

import java.time.LocalDateTime;

public class ScheduleResponse {
	private Long scheduleId;
	private LocalDateTime selectedDateTime;

	public ScheduleResponse(Long scheduleId, LocalDateTime selectedDateTime) {
		this.scheduleId = scheduleId;
		this.selectedDateTime = selectedDateTime;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public LocalDateTime getSelectedDateTime() {
		return selectedDateTime;
	}

	public void setSelectedDateTime(LocalDateTime selectedDateTime) {
		this.selectedDateTime = selectedDateTime;
	}
}