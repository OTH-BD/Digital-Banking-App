export interface AccountDetails {
    accountId:            string;
    balance:              number;
    currentPages:         number;
    totalPages:           number;
    pageSize:             number;
    accountOperationDTOS: AccountOperation[];
}

export interface AccountOperation {
    id:            number;
    operationDate: Date;
    amount:        number;
    type:          string;
    description:   string;
}
