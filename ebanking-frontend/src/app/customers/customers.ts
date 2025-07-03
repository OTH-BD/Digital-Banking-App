import { Router } from '@angular/router';
import { Customer } from './../services/customer';
import { CommonModule } from '@angular/common';

import { Component, OnInit } from '@angular/core';
import { CustomerModel } from './../model/customer.model';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-customers',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class Customers implements OnInit {

  //customers : any;
  customers! : Observable<Array<CustomerModel>>;
  errorMessage!: string;

  searchFormGroup!: FormGroup;
  constructor(private customerService: Customer, private fb: FormBuilder , private router: Router) { }

  ngOnInit() {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    })
    /*this.customers=this.customerService.getCustomers().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err); // Updated syntax
      })
    );*/
    this.handelSearchCustomers()
  }
    
    /*this.customerService.getCustomers().subscribe({
      next: (data) => {
        this.customers = data;
      },
      error: (err) => {
        this.errorMessage = err.message;
      }
    });*/

    handelSearchCustomers(){
    let kw=this.searchFormGroup?.value.keyword;
    this.customers =this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err); 
      })

      );
    }
    handleDeleteCustomer(c : CustomerModel) {
      let confirmation = confirm("Are you sure you want to delete this customer?");
      if (!confirmation) return;
      // Call the service to delete the customer
      this.customerService.deleteCustomer(c.id).subscribe({
        next: (resp) => {
         this.customers=this.customers.pipe(
           map(data => {
            let index = data.indexOf(c);
            data.splice(index, 1);
            return data;
           })
          );
          alert("Customer deleted successfully");
        },
        error: (err) => {
          console.error("Error deleting customer", err);
          // Handle error appropriately, e.g., show a message to the user
        }

    }
      );
  }


  handleCustomerAccounts(customer: CustomerModel) {
    this.router.navigateByUrl(`/customer-accounts/${customer.id}`, { state: customer });
  }
}
