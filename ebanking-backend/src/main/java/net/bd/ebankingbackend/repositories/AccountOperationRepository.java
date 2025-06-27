package net.bd.ebankingbackend.repositories;

import net.bd.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
     List<AccountOperation> findByBankAccountId(String accountId);

     Page<AccountOperation> findByBankAccountId(String accountId, PageRequest pegeable);
}
