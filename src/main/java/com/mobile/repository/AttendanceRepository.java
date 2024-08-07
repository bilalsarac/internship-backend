package com.mobile.repository;


import com.mobile.entities.Attendance;
import com.mobile.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByClientAndAttendanceDate(Client client, LocalDate date);
}
