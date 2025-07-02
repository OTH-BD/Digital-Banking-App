import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountDetails } from '../model/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  port: string="http://localhost:8085"
  constructor(private http: HttpClient) { }


  public getAccount(accountId: string, page : number, size: number ):Observable<AccountDetails> {

    return this.http.get<AccountDetails>(`${this.port}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`);
  }
}
