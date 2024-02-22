package com.example.vaccNow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.vaccNow.exception.CustomException.InvalidEmailException;
import com.example.vaccNow.exception.CustomException.PaymentNotMadeException;
import com.example.vaccNow.exception.CustomException.ScheduleAlreadyConfirmedException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.VaccineNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleVaccineNotFoundException(CustomException.VaccineNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.AvailabilityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAvailabilityNotFoundException(
			CustomException.AvailabilityNotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.BranchNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleBranchNotFoundException(CustomException.BranchNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.TimeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTimeNotFoundException(CustomException.TimeNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.TimeslotNotAvailableException.class)
	public ResponseEntity<ErrorResponse> handleTimeSlotNotFoundException(
			CustomException.TimeslotNotAvailableException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.ScheduleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleScheduleNotFoundException(CustomException.ScheduleNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.PaymentAlreadyMadeException.class)
	public ResponseEntity<ErrorResponse> handlePaymentAlreadyMadeException(
			CustomException.PaymentAlreadyMadeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.InvalidPaymentMethodException.class)
	public ResponseEntity<ErrorResponse> handlePaymentAlreadyMadeException(
			CustomException.InvalidPaymentMethodException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.NoAppliedVaccinationsException.class)
	public ResponseEntity<ErrorResponse> handleNoAppliedVaccinationsException(
			CustomException.NoAppliedVaccinationsException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.NoConfirmedVaccinationsException.class)
	public ResponseEntity<ErrorResponse> handleNoConfirmedVaccinationsException(
			CustomException.NoConfirmedVaccinationsException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEmailException(InvalidEmailException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ScheduleAlreadyConfirmedException.class)
	public ResponseEntity<ErrorResponse> handleScheduleAlreadyConfirmedException(ScheduleAlreadyConfirmedException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PaymentNotMadeException.class)
	public ResponseEntity<ErrorResponse> handlePaymentNotMadeException(PaymentNotMadeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
