package com.bank.creditcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {
    private String cardNumber;
    private double amount;
    private PaymentMethod paymentMethod;
}
