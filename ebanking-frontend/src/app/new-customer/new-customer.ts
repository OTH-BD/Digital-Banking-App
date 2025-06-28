import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Customer } from '../services/customer';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-customer',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css'
})
export class NewCustomer implements OnInit {
  newCustomerFormGroup! : FormGroup;
  // Define properties and methods for the NewCustomer component here
  constructor(private fb: FormBuilder, private customerService: Customer,private router: Router) { }

  ngOnInit() {
    this.newCustomerFormGroup = this.fb.group({
      name: this.fb.control('',[Validators.required, Validators.minLength(3)]),
      email: this.fb.control('',[Validators.required, Validators.email]),
      // Add other form controls as needed
    });
  }

  // Add methods to handle form submission, validation, etc.  
    handeSaveCustomer(){
    let customerData = this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customerData).subscribe({
      next: (data) => {
        // Handle success, e.g., show a message or redirect
        alert("Customer saved successfully");
        
        console.log("Customer saved successfully", data);
        // Optionally reset the form or navigate to another page
        this.newCustomerFormGroup.reset();
        // Redirect to the customers list or another page
        // Uncomment the line below if you want to navigate to the customers list after saving
        // this.router.navigate(['/customers']); 
        
      },
      error: (err) => {
        console.error("Error saving customer", err);
        // Handle error appropriately, e.g., show a message to the user
      }
    });
  }
}
