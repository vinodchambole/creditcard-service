package com.bank.creditcard.service;

import com.bank.creditcard.config.AccountFeignClient;
import com.bank.creditcard.config.TransactionRequest;
import com.bank.creditcard.dto.PaymentMethod;
import com.bank.creditcard.dto.PaymentRequest;
import com.bank.creditcard.dto.PaymentResponse;
import com.bank.creditcard.exception.ResourceNotFoundException;
import com.bank.creditcard.repository.CreditCard;
import com.bank.creditcard.repository.CreditCardRepository;
import com.bank.creditcard.repository.PaymentEntity;
import com.bank.creditcard.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountFeignClient accountFeignClient;

    public PaymentResponse clearDues(PaymentRequest paymentRequest) {
        CreditCard cCard = creditCardRepository.findByCardNumber(paymentRequest.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("credit card not found."));

        if (paymentRequest.getPaymentMethod().compareTo(PaymentMethod.BANK_ACCOUNT) == 0) {
            accountFeignClient.withdrawBalance(TransactionRequest.builder().accountId(cCard.getAccountId())
                    .amount(paymentRequest.getAmount()).build());
        }
        cCard.setAvailableCredit(cCard.getAvailableCredit() + paymentRequest.getAmount());
        cCard.setUsedCredit(cCard.getUsedCredit() - paymentRequest.getAmount());
        creditCardRepository.save(cCard);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentDate(LocalDateTime.now());
        paymentEntity.setCardNumber(paymentRequest.getCardNumber());
        paymentEntity.setAmount(paymentRequest.getAmount());
        paymentEntity.setPaymentMethod(PaymentMethod.BANK_ACCOUNT);
        paymentRepository.save(paymentEntity);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setMessage("Payment successful");
        paymentResponse.setSuccess(true);

        cCard.setStatus("PAID");
        creditCardRepository.save(cCard);

        return paymentResponse;
    }

    public PaymentResponse makeCreditCardPayment(PaymentRequest paymentRequest) {
        // Perform payment processing logic based on the credit card details
        // You can add additional logic here to handle payment validation, integration with payment gateway, etc.

        // Example logic: Retrieving the credit card entity and updating the available credit limit
        CreditCard creditCardEntity = creditCardRepository.findByCardNumber(paymentRequest.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found with card number: " + paymentRequest.getCardNumber()));


        double availableCredit = creditCardEntity.getAvailableCredit() - paymentRequest.getAmount();
        if (availableCredit < 0) {
            throw new IllegalArgumentException("Insufficient credit available for the payment.");
        }
        if (creditCardEntity.getCreditLimit() >= creditCardEntity.getUsedCredit()) {
            creditCardEntity.setAvailableCredit(availableCredit);
            creditCardEntity.setUsedCredit(creditCardEntity.getUsedCredit() + paymentRequest.getAmount());
            creditCardRepository.save(creditCardEntity);

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setMessage("Payment successful");
            paymentResponse.setSuccess(true);

            return paymentResponse;
        } else {
            throw new RuntimeException("insufficient limit");
        }
    }

}
