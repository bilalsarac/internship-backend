package com.mobile.service;

import com.mobile.entities.Directorship;
import com.mobile.entities.Tax;
import com.mobile.repository.DirectorshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DirectorshipService {

    @Autowired
    private DirectorshipRepository directorshipRepository;

    public List<Directorship> getAllDirectorships() {
        return directorshipRepository.findAll();
    }

    public Optional<Directorship> getDirectorshipById(Long id) {
        return directorshipRepository.findById(id);
    }

    public Directorship createDirectorship(Directorship directorship) {
        return directorshipRepository.save(directorship);
    }

    public Directorship updateDirectorship(Long id, Directorship directorshipDetails) {
        Optional<Directorship> optionalDirectorship = directorshipRepository.findById(id);
        if (optionalDirectorship.isPresent()) {
            Directorship directorship = optionalDirectorship.get();
            directorship.setName(directorshipDetails.getName());

            if (directorshipDetails.getTaxes() != null) {
                // Get existing taxes and clear them
                List<Tax> existingTaxes = directorship.getTaxes();
                if (existingTaxes == null) {
                    existingTaxes = new ArrayList<>();
                }

                // Remove taxes that are no longer referenced
                existingTaxes.removeIf(tax -> !directorshipDetails.getTaxes().contains(tax));

                // Update existing taxes and add new ones
                for (Tax taxDetails : directorshipDetails.getTaxes()) {
                    if (taxDetails.getId() == null) {
                        // New Tax entity
                        taxDetails.setDirectorship(directorship);
                        existingTaxes.add(taxDetails);
                    } else {
                        // Existing Tax entity
                        Tax existingTax = existingTaxes.stream()
                                .filter(t -> t.getId().equals(taxDetails.getId()))
                                .findFirst()
                                .orElse(null);
                        if (existingTax != null) {
                            existingTax.setTaxName(taxDetails.getTaxName());
                            existingTax.setTaxAmount(taxDetails.getTaxAmount());
                            existingTax.setTypeofTax(taxDetails.getTypeofTax()); // Set the type field
                        } else {
                            // Tax not found, add it
                            taxDetails.setDirectorship(directorship);
                            existingTaxes.add(taxDetails);
                        }
                    }
                }

                // Update the taxes in the directorship
                directorship.setTaxes(existingTaxes);
            } else {
                // If no taxes provided, clear the existing ones
                directorship.getTaxes().clear();
            }

            return directorshipRepository.save(directorship);
        } else {
            return null;
        }
    }

    public void deleteDirectorship(Long id) {
        directorshipRepository.deleteById(id);
    }
}