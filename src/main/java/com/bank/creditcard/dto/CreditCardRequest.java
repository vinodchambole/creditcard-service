package com.bank.creditcard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardRequest {
    private String cardNumber;
    private double creditLimit;
    private double interestRate;
    private String customer;
    private Long accountId;
}
