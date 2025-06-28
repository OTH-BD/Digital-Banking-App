import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerModel } from '../model/customer.model';

@Injectable({
  providedIn: 'root'
})
export class Customer {
   port: string="http://localhost:8085"
   constructor(private http: HttpClient) { }

   public getCustomers():Observable<Array<CustomerModel>> {
      return this.http.get<Array<CustomerModel>>(`${this.port}/customers`);
   }

    public searchCustomers(keyword: string):Observable<Array<CustomerModel>> {
      return this.http.get<Array<CustomerModel>>(`${this.port}/customers/search?keyword=${keyword}`);
   }

    public saveCustomer(customer: CustomerModel):Observable<CustomerModel> {
        return this.http.post<CustomerModel>(`${this.port}/customers`, customer);
    }
    public deleteCustomer(id: number){
        return this.http.delete(`${this.port}/customers/${id}`);
    }
    
}

