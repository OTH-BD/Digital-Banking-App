package net.bd.ebankingbackend.web;


import net.bd.ebankingbackend.dtos.*;
import net.bd.ebankingbackend.entities.BankAccount;
import net.bd.ebankingbackend.entities.Customer;
import net.bd.ebankingbackend.exceptions.BalanceNotSufficentException;
import net.bd.ebankingbackend.exceptions.BanAccountNotFoundException;
import net.bd.ebankingbackend.repositories.CustomerRepository;
import net.bd.ebankingbackend.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestController {
    private  BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;

    }


    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccountById(@PathVariable String accountId) throws BanAccountNotFoundException {
        return  bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getAllBankAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId , @RequestParam(name = "page",defaultValue = "0") int page,
                                                     @RequestParam(name = "size",defaultValue = "5") int size ) throws BanAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BanAccountNotFoundException, BalanceNotSufficentException {
       this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
       return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BanAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfert")
    public void transfert(@RequestBody TransfertRequestDTO transfertRequestDTO) throws BalanceNotSufficentException, BanAccountNotFoundException {
        this.bankAccountService.transfer(transfertRequestDTO.getAccountSource(), transfertRequestDTO.getAccountDestination(),transfertRequestDTO.getAmount(),transfertRequestDTO.getDescription());

    }




}
