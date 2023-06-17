package com.bank.creditcard.controller;

import com.bank.creditcard.dto.*;
import com.bank.creditcard.repository.CreditCard;
import com.bank.creditcard.service.CreditCardService;
import com.bank.creditcard.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private PaymentService paymentService;


    @PostMapping
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCardRequest creditCardRequest) {
        CreditCard creditCardResponse = creditCardService.createCreditCard(creditCardRequest);
        return new ResponseEntity<>(creditCardResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable("id") Long id) {
        CreditCard creditCardResponse = creditCardService.getCreditCardById(id);
        return new ResponseEntity<>(creditCardResponse, HttpStatus.OK);
    }

    @PostMapping("/make-payment")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.clearDues(paymentRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @PutMapping("/update-limit")
    public ResponseEntity<CreditLimitUpdateResponse> updateCreditLimit(
            @RequestBody CreditLimitUpdateRequest request) {
        CreditLimitUpdateResponse response = creditCardService.updateCreditLimit(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> makeCreditCardPayment(
            @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.makeCreditCardPayment(paymentRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}

