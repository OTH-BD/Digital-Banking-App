import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AccountsService } from '../services/accounts';
import { AccountDetails } from '../model/account.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-accounts',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css'
})
export class Accounts implements OnInit {


   accountFormGroup!: FormGroup;
   currentPage: number = 0;
   pageSize: number = 5;
   accountObservable!: Observable<AccountDetails>;
  constructor(private fb : FormBuilder, private accountsService: AccountsService) { }



  ngOnInit(): void {
    this.accountFormGroup =  this.fb.group({
      accountId : this.fb.control(''),

    })
   
  }
        handleSearchAccount() {
          let accountId = this.accountFormGroup.value.accountId;

          this.accountObservable = this.accountsService.getAccount(accountId, this.currentPage, this.pageSize);
        }


        goToPage(page: number) {
          this.currentPage = page;
          this.handleSearchAccount();
        }

}
