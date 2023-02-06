export type Transaction = {
    senderAccountNumber: number,
    receiverAccountNumber: number,
    amount: number,
    purpose: string,
    transactionDate: Date
}