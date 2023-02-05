import axios from 'axios';
import {AppUser} from "../models/AppUser";
import {Transaction} from "../models/Transaction";

export async function getLoggedInUser() {
    try {
        const response = await axios.get<AppUser>('api/app-users/me');
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function postTransaction(transaction: Transaction) {
    try {
        const response = await axios.post<Transaction>('api/transactions', transaction, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

