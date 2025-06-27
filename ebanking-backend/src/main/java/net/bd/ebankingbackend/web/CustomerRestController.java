package net.bd.ebankingbackend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bd.ebankingbackend.dtos.AccountOperationDTO;
import net.bd.ebankingbackend.dtos.CustomerDTO;
import net.bd.ebankingbackend.entities.Customer;
import net.bd.ebankingbackend.exceptions.CustomerNotFoundException;
import net.bd.ebankingbackend.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j // pour afficher les messages de logs
public class CustomerRestController {


    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }
    //  API pour consulter un Customer par ID
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomerById(@PathVariable(name = "id") Long customerId ) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(customerId);

    }
    // API pour ajouter un customer
    // @RequestBody pour indiquer a spring pour que les donner de customerDto on va la recupere apartir de core de la requette en format JSon
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerid}")
    public  CustomerDTO updateCustomer(@PathVariable Long customerid, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerid);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(id);

    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
       return  bankAccountService.accountHistory(accountId);
    }

}
