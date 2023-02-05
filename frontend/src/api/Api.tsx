import axios from 'axios';
import {AppUser} from "../models/AppUser";

export async function getLoggedInUser() {
    try {
        const response = await axios.get<AppUser>('api/app-users/me');
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

