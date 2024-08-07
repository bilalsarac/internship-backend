package com.mobile.service;

import com.mobile.entities.Attendance;
import com.mobile.entities.Client;
import com.mobile.repository.AttendanceRepository;
import com.mobile.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClientRepository clientRepository;

    public String markAttendance(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            LocalDate today = LocalDate.now();

            // Check if an attendance record for the client already exists for today
            Optional<Attendance> existingAttendance = attendanceRepository.findByClientAndAttendanceDate(client, today);

            if (existingAttendance.isPresent()) {
                return "Attendance already marked for today for client: " + clientId;
            }

            Attendance attendance = new Attendance();
            attendance.setClient(client);
            attendance.setAttendanceTime(LocalDateTime.now());
            attendance.setAttendanceDate(today);

            try {
                attendanceRepository.save(attendance);
                return "Attendance marked successfully for client: " + clientId;
            } catch (DataIntegrityViolationException e) {
                return "Error marking attendance: Duplicate entry.";
            }
        } else {
            return "Client not found for clientId: " + clientId;
        }
    }

    public List<Client> getAttendees() {
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream()
                .map(Attendance::getClient)
                .distinct()
                .collect(Collectors.toList());
    }
}
