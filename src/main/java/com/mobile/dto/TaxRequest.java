package com.mobile.dto;

import lombok.Data;

@Data
public class TaxRequest {
    private String taxName;
    private Double taxAmount;
    private String typeofTax;
    private Long directorshipId;
}

