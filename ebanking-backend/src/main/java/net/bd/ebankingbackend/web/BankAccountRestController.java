package net.bd.ebankingbackend.web;


import net.bd.ebankingbackend.dtos.AccountHistoryDTO;
import net.bd.ebankingbackend.dtos.BankAccountDTO;
import net.bd.ebankingbackend.exceptions.BanAccountNotFoundException;
import net.bd.ebankingbackend.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestController {
    private BankAccountService bankAccountService;

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

}
