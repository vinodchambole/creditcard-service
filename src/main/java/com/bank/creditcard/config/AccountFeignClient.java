package com.bank.creditcard.config;

import com.bank.creditcard.repository.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "accountFeignClient", url = "http://localhost:8081", configuration = FeignClientConfig.class)
public interface AccountFeignClient {

    @GetMapping("/accounts/email")
    ResponseEntity<List<Account>> getAccountByEmail(@RequestParam String email);

    @PostMapping("/transactions/withdraw-balance")
    ResponseEntity<String> withdrawBalance(@RequestBody TransactionRequest transactionRequest);
}
