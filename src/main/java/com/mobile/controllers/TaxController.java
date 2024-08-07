package com.mobile.controllers;

import com.mobile.dto.ImageUploadRequest;
import com.mobile.dto.TaxRequest;
import com.mobile.entities.Tax;
import com.mobile.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TaxController {

    private final TaxService taxService;

    @Autowired
    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    // Create a new Tax record
    @PostMapping("/taxes")
    public Tax createTax(@RequestBody TaxRequest taxRequest) {
        return taxService.createTax(taxRequest);
    }

    // Retrieve all Tax records
    @GetMapping("/taxes")
    public List<Tax> getAllTaxes() {
        return taxService.findAll();
    }

    // Retrieve a specific Tax record by ID
    @GetMapping("/taxes/{id}")
    public Optional<Tax> getTaxById(@PathVariable Long id) {
        return taxService.findById(id);
    }

    // Update a Tax record
    @PutMapping("/taxes/{id}")
    public Tax updateTax(@PathVariable Long id, @RequestBody Tax taxDetails) {
        return taxService.updateTax(id, taxDetails);
    }

    @PostMapping("/taxes/{id}/image")
    public ResponseEntity<String> addImageToTax(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            taxService.addImageToTax(id, imageBytes);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
    @GetMapping("/taxes/{id}/image")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable Long id) {
        try {
            byte[] imageBytes = taxService.getImageById(id);
            if (imageBytes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(bais));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

}


    // Delete a Tax record
    @DeleteMapping("/taxes/{id}")
    public void deleteTax(@PathVariable Long id) {
        taxService.deleteById(id);
    }
}