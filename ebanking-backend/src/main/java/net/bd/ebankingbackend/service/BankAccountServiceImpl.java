package net.bd.ebankingbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bd.ebankingbackend.dtos.*;
import net.bd.ebankingbackend.entities.*;
import net.bd.ebankingbackend.enums.OperationType;
import net.bd.ebankingbackend.exceptions.BalanceNotSufficentException;
import net.bd.ebankingbackend.exceptions.BanAccountNotFoundException;
import net.bd.ebankingbackend.exceptions.CustomerNotFoundException;
import net.bd.ebankingbackend.mappers.BankAccountMapperImpl;
import net.bd.ebankingbackend.repositories.AccountOperationRepository;
import net.bd.ebankingbackend.repositories.BankAccountRepository;
import net.bd.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j // pour logger vos messages

public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;




    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer =dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer =customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    ///   create Current Bank account

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
       CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setBalance(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount =bankAccountRepository.save(currentAccount);

        return dtoMapper.fromCurrentAccount(savedBankAccount);
    }
    ///   create Saiving Bank account
    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setBalance(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount =bankAccountRepository.save(savingAccount);

        return dtoMapper.fromSavingAccount(savedBankAccount);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO>  customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
       /* // programmation imperative
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for(Customer customer : customers) {
            CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
            customerDTOs.add(customerDTO);
        }
        return customerDTOs;
        */
       return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BanAccountNotFoundException {
        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(()->new BanAccountNotFoundException(" BankAccount Not Found !"));
        if(bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingAccount(savingAccount);
        }else{
           CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentAccount(currentAccount);

        }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BanAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(()->new BanAccountNotFoundException(" BankAccount Not Found !"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("Balance not sufficient !");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        // cette operation fait depuis cette account
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        // mettre a jour le solde du compte
        bankAccount.setBalance(bankAccount.getBalance()-amount);

        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BanAccountNotFoundException {

        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(()->new BanAccountNotFoundException(" BankAccount Not Found !"));


        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        // cette operation fait depuis cette account
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        // mettre a jour le solde du compte
        bankAccount.setBalance(bankAccount.getBalance()+amount);

        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount, String description) throws BalanceNotSufficentException, BanAccountNotFoundException {

        debit(accountIdSource,amount,"Transfert to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfert from "+accountIdSource);

    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS =bankAccounts.stream().map(bankAccount ->{
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingAccount(savingAccount);
            }else{
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentAccount(currentAccount);
            }

                }).collect(Collectors.toList());
        return bankAccountDTOS;
    }


    @Override
        public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not found !"));
        return dtoMapper.fromCustomer(customer);
    }


    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer =dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer =customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations=accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BanAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) throw new BanAccountNotFoundException("Account Not Found !");
        Page<AccountOperation> accountOperations=accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId ,PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPages(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        List<Customer> customers = customerRepository.searchCustomer(keyword);
        List<CustomerDTO> customerDTOS =customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }


}
