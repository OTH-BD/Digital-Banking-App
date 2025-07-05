import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AccountsService } from '../services/accounts';
import { AccountDetails } from '../model/account.model';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-accounts',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css'
})
export class Accounts implements OnInit {


   errorMessage!: string;
   accountFormGroup!: FormGroup;
   operationFormGroup!: FormGroup;
   currentPage: number = 0;
   pageSize: number = 5;
   accountObservable!: Observable<AccountDetails>;
  constructor(private fb : FormBuilder, private accountsService: AccountsService ,public authService: AuthService) { }



  ngOnInit(): void {
    this.accountFormGroup =  this.fb.group({
      accountId : this.fb.control(''),

    });

    this.operationFormGroup = this.fb.group({
     
      
      operationType: this.fb.control(null),
      amount: this.fb.control(null),
      operationDate: this.fb.control(null),
      description: this.fb.control(null),
      accountDestination : this.fb.control(null),
    });

  }
        handleSearchAccount() {
          let accountId = this.accountFormGroup.value.accountId;

          this.accountObservable = this.accountsService.getAccount(accountId, this.currentPage, this.pageSize).pipe(
            catchError(err => {
              this.errorMessage = err.message;
              return throwError(err) ;
            })
          );
        }


        goToPage(page: number) {
          this.currentPage = page;
          this.handleSearchAccount();
        }


        handleAccountOperation() {
          let accountId : string = this.accountFormGroup.value.accountId;
          let operationType : string = this.operationFormGroup.value.operationType;
          let amount : number = this.operationFormGroup.value.amount;
          let description  : string = this.operationFormGroup.value.description;
          let operationDate : Date = this.operationFormGroup.value.operationDate;
          let accountDestination : string = this.operationFormGroup.value.accountDestination;
          if(operationType == 'DEBIT') {
            this.accountsService.debit(accountId, amount, description, operationDate)
              .subscribe({
                next: (data) => {
                  alert(" Debit Operation  successful");
                  console.log('Debit operation successful', data);
                  this.operationFormGroup.reset(); // Reset the form after successful operation
                  this.handleSearchAccount(); // Refresh the account details after operation
                },
                error: (error) => {
                  console.error('Error occurred during debit operation', error);
                }
              });
          }else if(operationType == 'CREDIT') {
            this.accountsService.credit(accountId, amount, description, operationDate)
              .subscribe({
                next: (data) => {
                  alert(" Credit Operation  successful");
                  console.log('Credit operation successful', data);
                  this.operationFormGroup.reset(); // Reset the form after successful operation
                  this.handleSearchAccount(); // Refresh the account details after operation
                },
                
                error: (error) => {
                  console.log(error);
                }
              });

          }else if(operationType == 'TRANSFER') {
            let accountDestination = this.operationFormGroup.value.accountDestination;
            this.accountsService.transfer(accountId, amount, description, operationDate, accountDestination)
              .subscribe({
                next: (data) => {
                  alert(" Transfer Operation  successful");
                  console.log('Transfer operation successful', data);
                  this.operationFormGroup.reset(); // Reset the form after successful operation
                  this.handleSearchAccount(); // Refresh the account details after operation
                },
                error: (error) => {
                  console.log(error);
                }
              });
          }
        }

}
