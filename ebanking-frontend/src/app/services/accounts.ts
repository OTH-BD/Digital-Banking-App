import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountDetails } from '../model/account.model';
import { BankAccount } from '../model/bank-account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  port: string="http://localhost:8085"
  apiUrl: any;
  constructor(private http: HttpClient) { }


  public getAccount(accountId: string, page : number, size: number ):Observable<AccountDetails> {

    return this.http.get<AccountDetails>(`${this.port}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`);
  }

  public debit(accountId: string, amount: number, description: string, operationDate: Date) {
    let data ={accountId: accountId,amount:amount, description: description, operationDate: operationDate};
    return this.http.post(`${this.port}/accounts/debit`,data);
  }
  public credit(accountId: string, amount: number, description: string, operationDate: Date){
    let data ={accountId: accountId,amount:amount, description: description, operationDate: operationDate};
    return this.http.post(`${this.port}/accounts/credit`,data);
  }
  public transfer(accountSource: string, amount: number, description: string, operationDate: Date, accountDestination: string){
    let data ={accountSource,amount, description, operationDate, accountDestination};
    return this.http.post(`${this.port}/accounts/transfer`,data);
  }



}
