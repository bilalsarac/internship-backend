package com.mobile.service;

import com.mobile.dto.TaxRequest;
import com.mobile.entities.Directorship;
import com.mobile.entities.Tax;
import com.mobile.repository.DirectorshipRepository;
import com.mobile.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@Service
public class TaxService {

    private final TaxRepository taxRepository;
    private final DirectorshipRepository directorshipRepository;

    @Autowired
    public TaxService(TaxRepository taxRepository, DirectorshipRepository directorshipRepository) {
        this.taxRepository = taxRepository;
        this.directorshipRepository = directorshipRepository;
    }

    public List<Tax> findAll() {
        return taxRepository.findAll();
    }

    public Tax saveTax(Tax tax) {
        return taxRepository.save(tax);
    }

    public void deleteById(Long id) {
        Optional<Tax> taxOptional = taxRepository.findById(id);
        taxOptional.ifPresent(taxRepository::delete);
    }

    public Optional<Tax> findById(Long id) {
        return taxRepository.findById(id);
    }

    public Tax updateTax(Long id, Tax updatedTax) {
        Optional<Tax> existingTax = taxRepository.findById(id);
        if (existingTax.isPresent()) {
            Tax tax = existingTax.get();
            tax.setTaxName(updatedTax.getTaxName());
            tax.setTaxAmount(updatedTax.getTaxAmount());
            tax.setTypeofTax(updatedTax.getTypeofTax()); // set the type field

            return taxRepository.save(tax);
        }
        return null;
    }

    public Tax createTax(TaxRequest taxRequest) {
        Directorship directorship = directorshipRepository.findById(taxRequest.getDirectorshipId())
                .orElseThrow(() -> new RuntimeException("Directorship not found"));

        Tax tax = new Tax();
        tax.setTaxName(taxRequest.getTaxName());
        tax.setTaxAmount(taxRequest.getTaxAmount());
        tax.setTypeofTax(taxRequest.getTypeofTax());
        tax.setDirectorship(directorship);

        return taxRepository.save(tax);
    }

    public void addImageToTax(Long id, byte[] imageBytes) throws IOException {
        Optional<Tax> existingTax = taxRepository.findById(id);
        if (existingTax.isPresent()) {
            Tax tax = existingTax.get();
            tax.setImage(imageBytes);
            taxRepository.save(tax);
        } else {
            throw new RuntimeException("Tax not found");
        }
    }

    @Transactional(readOnly = true)
    public byte[] getImageById(Long id) {
        Optional<Tax> taxOptional = taxRepository.findById(id);
        if (taxOptional.isPresent()) {
            return taxOptional.get().getImage();
        }
        return null;
    }
}