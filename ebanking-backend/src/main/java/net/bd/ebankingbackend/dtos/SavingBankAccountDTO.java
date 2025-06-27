package net.bd.ebankingbackend.dtos;


import lombok.Data;

import net.bd.ebankingbackend.enums.AccountStatus;

import java.util.Date;



@Data

public class SavingBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date CreatedAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;


}