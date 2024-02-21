package com.example.vaccNow.exception;

public class CustomException {

	public static class VaccineNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public VaccineNotFoundException(Long branchId) {
			super("Vaccines not found for branch with ID " + branchId);
		}
	}

	public static class AvailabilityNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public AvailabilityNotFoundException(Long branchId) {
			super("Availability not found for branch with ID " + branchId);
		}
	}

	public static class BranchNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public BranchNotFoundException(Long branchId) {
			super("Branch with ID " + branchId + " not found.");
		}
	}

	public static class TimeNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public TimeNotFoundException(Long branchId) {
			super("Available times not found for branch with ID " + branchId);
		}
	}

	public static class TimeslotNotAvailableException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public TimeslotNotAvailableException(String msg) {
			super("Available times Slot not available ");
		}
	}

	public static class ScheduleNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ScheduleNotFoundException(Long scheduleId) {
			super("Schedule with ID " + scheduleId + " not found.", null);
		}
	}

	public static class PaymentAlreadyMadeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public PaymentAlreadyMadeException(String message) {
			super("Payment has already been made for this schedule.");
		}
	}

	public static class InvalidPaymentMethodException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public InvalidPaymentMethodException(String message) {
			super("");
		}
	}

	public static class NoAppliedVaccinationsException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public NoAppliedVaccinationsException(String message) {
			super(message);
		}
	}

	public static class NoConfirmedVaccinationsException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public NoConfirmedVaccinationsException(String message) {
			super(message);
		}
	}
}
