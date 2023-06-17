package com.bank.creditcard.service;

import com.bank.creditcard.config.AccountFeignClient;
import com.bank.creditcard.dto.CreditCardRequest;
import com.bank.creditcard.dto.CreditLimitUpdateRequest;
import com.bank.creditcard.dto.CreditLimitUpdateResponse;
import com.bank.creditcard.exception.ResourceNotFoundException;
import com.bank.creditcard.repository.Account;
import com.bank.creditcard.repository.CreditCard;
import com.bank.creditcard.repository.CreditCardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountFeignClient accountFeignClient;

    public CreditCard createCreditCard(CreditCardRequest request) {

        ResponseEntity<List<Account>> accountByEmail = accountFeignClient.getAccountByEmail(request.getCustomer());
        if (Objects.requireNonNull(accountByEmail.getBody()).isEmpty()) {
            throw new UsernameNotFoundException("User account does not exist. Please register.");
        }
        Account account = accountByEmail
                .getBody()
                .stream().filter(a -> Objects.equals(a.getId(), request.getAccountId()))
                .findFirst().orElseThrow((() -> new ResourceNotFoundException("Account with Id: " + request.getAccountId() + " does not exist.")));

        CreditCard creditCard = new CreditCard();
        BeanUtils.copyProperties(request, creditCard);
        creditCard.setStatus("ACTIVE");
        creditCard.setAvailableCredit(request.getCreditLimit());
        creditCardRepository.save(creditCard);

        return creditCard;
    }

    public CreditCard getCreditCardById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found with id: " + id));
        CreditCard creditCardResponse = new CreditCard();
        BeanUtils.copyProperties(creditCard, creditCardResponse);
        return creditCardResponse;
    }

    public CreditLimitUpdateResponse updateCreditLimit(CreditLimitUpdateRequest request) {
        CreditCard creditCardEntity = creditCardRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found with card number: " + request.getCardNumber()));

        creditCardEntity.setCreditLimit(request.getNewCreditLimit());
        creditCardEntity.setAvailableCredit(creditCardEntity.getCreditLimit() - creditCardEntity.getUsedCredit());
        creditCardRepository.save(creditCardEntity);

        CreditLimitUpdateResponse response = new CreditLimitUpdateResponse();
        response.setMessage("Credit limit updated successfully");
        response.setSuccess(true);

        return response;
    }
}
