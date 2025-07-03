package net.bd.ebankingbackend.service;

import net.bd.ebankingbackend.dtos.*;
import net.bd.ebankingbackend.entities.BankAccount;
import net.bd.ebankingbackend.entities.CurrentAccount;
import net.bd.ebankingbackend.entities.Customer;
import net.bd.ebankingbackend.entities.SavingAccount;
import net.bd.ebankingbackend.exceptions.BalanceNotSufficentException;
import net.bd.ebankingbackend.exceptions.BanAccountNotFoundException;
import net.bd.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers();

    BankAccountDTO getBankAccount(String accountId) throws BanAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BanAccountNotFoundException, BalanceNotSufficentException;

    void credit(String accountId, double amount, String description) throws BalanceNotSufficentException, BanAccountNotFoundException;

    void transfer(String accountIdSource, String  accountIdDestination, double amount, String description) throws BalanceNotSufficentException, BanAccountNotFoundException;


    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BanAccountNotFoundException;

    List<CustomerDTO> searchCustomer(String keyword);


}
