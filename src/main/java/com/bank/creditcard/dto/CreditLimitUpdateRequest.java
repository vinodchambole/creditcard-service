package com.bank.creditcard.dto;

import lombok.Data;

@Data
public class CreditLimitUpdateRequest {
    private String cardNumber;
    private double newCreditLimit;

    // Constructors, getters, and setters

}