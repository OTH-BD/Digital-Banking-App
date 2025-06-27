package net.bd.ebankingbackend.service;


import net.bd.ebankingbackend.entities.BankAccount;
import net.bd.ebankingbackend.entities.CurrentAccount;
import net.bd.ebankingbackend.entities.SavingAccount;
import net.bd.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter() {
        BankAccount bankAccount =
                bankAccountRepository.findById("025b63f6-ce36-4672-ac26-ab882229bed5").orElse(null);

        if (bankAccount != null) {
            System.out.println("************************* ************************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if(bankAccount instanceof CurrentAccount){
                System.out.println(((CurrentAccount)bankAccount).getOverDraft());
            }else {
                System.out.println(((SavingAccount)bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op->{
                System.out.println(op.getType()+"\t"+op.getAmount()+"\t"+op.getOperationDate());


            });

        }
    }
}
