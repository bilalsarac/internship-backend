package com.mobile.controllers;


import com.mobile.entities.Directorship;
import com.mobile.service.DirectorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/directorships")
public class DirectorshipController {

    @Autowired
    private DirectorshipService directorshipService;

    @GetMapping
    public List<Directorship> getAllDirectorships() {
        return directorshipService.getAllDirectorships();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Directorship> getDirectorshipById(@PathVariable Long id) {
        Optional<Directorship> directorship = directorshipService.getDirectorshipById(id);
        if (directorship.isPresent()) {
            return ResponseEntity.ok(directorship.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Directorship createDirectorship(@RequestBody Directorship directorship) {
        return directorshipService.createDirectorship(directorship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Directorship> updateDirectorship(@PathVariable Long id, @RequestBody Directorship directorshipDetails) {
        Directorship updatedDirectorship = directorshipService.updateDirectorship(id, directorshipDetails);
        if (updatedDirectorship != null) {
            return ResponseEntity.ok(updatedDirectorship);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirectorship(@PathVariable Long id) {
        directorshipService.deleteDirectorship(id);
        return ResponseEntity.noContent().build();
    }
}