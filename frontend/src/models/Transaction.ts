export type Transaction = {
    id: string,
    senderAccountNumber: number,
    receiverAccountNumber: number,
    amount: number,
    purpose: string,
    transactionDate: string
}