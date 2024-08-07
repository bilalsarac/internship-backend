package com.mobile.entities;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="attendance", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "attendance_date"}))
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;


    @Column(nullable = false)
    private LocalDateTime attendanceTime;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;


}
