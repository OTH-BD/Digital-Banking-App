package net.bd.ebankingbackend;

import net.bd.ebankingbackend.dtos.BankAccountDTO;
import net.bd.ebankingbackend.dtos.CurrentBankAccountDTO;
import net.bd.ebankingbackend.dtos.CustomerDTO;
import net.bd.ebankingbackend.dtos.SavingBankAccountDTO;
import net.bd.ebankingbackend.entities.*;
import net.bd.ebankingbackend.enums.AccountStatus;
import net.bd.ebankingbackend.enums.OperationType;
import net.bd.ebankingbackend.exceptions.BalanceNotSufficentException;
import net.bd.ebankingbackend.exceptions.BanAccountNotFoundException;
import net.bd.ebankingbackend.exceptions.CustomerNotFoundException;
import net.bd.ebankingbackend.repositories.AccountOperationRepository;
import net.bd.ebankingbackend.repositories.BankAccountRepository;
import net.bd.ebankingbackend.repositories.CustomerRepository;
import net.bd.ebankingbackend.service.BankAccountService;
import net.bd.ebankingbackend.service.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(BankAccountService bankAccountService) {
        return args -> {

            Stream.of("Hassan", "Imane","Mohamed").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);

            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());

//                    bankAccountService.bankAccountList().forEach(account->{
//                        for(int i =0 ;i<10;i++){
//                            bankAccountService.credit(account.getId(),10000+Math.random()*120000 ,"Credit");
//                        }
//                    });
                } catch (CustomerNotFoundException e)  {
                    e.printStackTrace();
                }
            });

            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts) {
                for(int i =0 ;i<10;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO)bankAccount).getId();

                    }else{
                        accountId =((CurrentBankAccountDTO)bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000 ,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }

            }


        };
    }



    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan","Yassin","Aicha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.ACTIVATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                ///  l'ajout saving account

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.4);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc->{
                for(int i=0;i<10;i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }



            });
        };
    }

}
