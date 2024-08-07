package com.mobile.controllers;

import com.mobile.entities.Client;
import com.mobile.service.AttendanceService;
import com.mobile.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ClientService clientService;


    @PostMapping("/attendance")
    public ResponseEntity<String> markAttendance(@RequestBody Map<String, String> qrData) {
        String clientEmail = qrData.get("qrData");
        Client client = clientService.getUserByEmail(clientEmail);

        if (client != null) {
            attendanceService.markAttendance(client.getUserId()); // assuming getUserId returns Long
            return ResponseEntity.ok("Attendance marked successfully for client with email: " + clientEmail);
        } else {
            return ResponseEntity.badRequest().body("Client not found for email: " + clientEmail);
        }
    }

    @GetMapping("/attendance")
    public ResponseEntity<List<Client>> getAttendees() {
        List<Client> attendees = attendanceService.getAttendees();
        return ResponseEntity.ok(attendees);
    }

}
