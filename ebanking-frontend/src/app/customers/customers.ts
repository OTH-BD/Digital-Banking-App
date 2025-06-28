import { Customer } from './../services/customer';
import { CommonModule } from '@angular/common';

import { Component, OnInit } from '@angular/core';
import { CustomerModel } from './../model/customer.model';
import { catchError, Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-customers',
  imports: [CommonModule],
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class Customers implements OnInit {
  //customers : any;
  customers! : Observable<Array<CustomerModel>>;
  errorMessage!: string;
  constructor(private customerService: Customer) { }

  ngOnInit() {
    
    this.customers=this.customerService.getCustomers().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err); // Updated syntax
      })
    );
  }
    
    /*this.customerService.getCustomers().subscribe({
      next: (data) => {
        this.customers = data;
      },
      error: (err) => {
        this.errorMessage = err.message;
      }
    });*/
  }

