// bank-account.model.ts

export enum AccountStatus {
  CREATED = 'CREATED',
  ACTIVATED = 'ACTIVATED',
  SUSPENDED = 'SUSPENDED'
}

export interface Customer {
  id: string;
  name: string;
  email: string;
}

export interface AccountOperation {
  id: string;
  operationDate: Date;
  amount: number;
  type: string;
  description: string;
}

export interface BankAccount {
  id: string;
  balance: number;
  createdAt: Date;
  status: AccountStatus;
  customer: Customer;
  accountOperations: AccountOperation[];
}
