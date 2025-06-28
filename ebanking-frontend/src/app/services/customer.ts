import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerModel } from '../model/customer.model';

@Injectable({
  providedIn: 'root'
})
export class Customer {

   constructor(private http: HttpClient) { }

   public getCustomers():Observable<Array<CustomerModel>> {
      return this.http.get<Array<CustomerModel>>('http://localhost:8085/customers');
   }
}
