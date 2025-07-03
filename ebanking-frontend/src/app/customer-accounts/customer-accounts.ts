import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../services/customer';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-accounts',
  imports: [CommonModule],
  templateUrl: './customer-accounts.html',
  styleUrl: './customer-accounts.css'
})
export class CustomerAccountsComponent implements OnInit {
  customerId!: string;
  customer!:Customer
  

  constructor(private route: ActivatedRoute ,private router : Router) { 
    this.customer = this.router.getCurrentNavigation()?.extras.state as Customer;
  }
  ngOnInit(): void {
   this.customerId = this.route.snapshot.params['id'];
   
  }
    // I
    // nitialization logic can go here
  }
