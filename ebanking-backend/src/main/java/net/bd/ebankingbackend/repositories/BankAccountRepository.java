package net.bd.ebankingbackend.repositories;

import net.bd.ebankingbackend.entities.BankAccount;
import net.bd.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    List<BankAccount> findByCustomerId(Long customerId);


}
