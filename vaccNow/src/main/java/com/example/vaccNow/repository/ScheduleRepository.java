package com.example.vaccNow.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vaccNow.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	Optional<Schedule> findFirstByBranchIdOrderByAvailableTimeAsc(Long branchId);

	List<Schedule> findByBranchIdOrderByAvailableTimeAsc(Long branchId);

	List<Schedule> findByBranchIdAndAvailableTimeBetween(Long branchId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	boolean existsByBranchIdAndAvailableTime(Long branchId, LocalDateTime availableTime);

	@Query("SELECT MAX(s.availableTime) FROM Schedule s WHERE s.branch.id = :branchId")
	LocalDateTime findLatestBookingEndTime(@Param("branchId") Long branchId);

	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Schedule s WHERE s.id = :id AND s.paymentMade = :paymentMade")
	boolean existsByIdAndPaymentMade(@Param("id") Long id, @Param("paymentMade") boolean paymentMade);

	@Query("UPDATE Schedule s SET s.vaccinationScheduled = true WHERE s.id = :scheduleId")
	int markVaccinationScheduled(@Param("scheduleId") Long scheduleId);

	@Query("SELECT s FROM Schedule s WHERE s.branch.id = :branchId AND s.vaccinationScheduled = true")
	List<Schedule> findAppliedVaccinationsByBranch(@Param("branchId") Long branchId);

	@Query("SELECT s FROM Schedule s WHERE s.availableTime >= :startDate AND s.availableTime < :endDate AND s.vaccinationScheduled = true")
	List<Schedule> findAppliedVaccinationsByDay(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	@Query("SELECT s FROM Schedule s WHERE s.vaccinationScheduled = true AND s.availableTime BETWEEN :startDateTime AND :endDateTime")
	List<Schedule> findConfirmedVaccinationsOverTimePeriod(@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime);

}
