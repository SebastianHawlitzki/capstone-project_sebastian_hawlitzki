import {Route, Routes} from "react-router-dom";
import Auth from "./components/Auth";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import React from "react";
import TransactionPage from "./pages/TransactionPage";
import TransactionListPage from "./pages/TransactionListPage";

export default function Root () {
    return (
        <Routes>
            <Route path="/" element={
                <Auth>
                    <HomePage/>
                </Auth>
            }/>

            <Route path="/transaction" element={
                <Auth>
                    <TransactionPage/>
                </Auth>
            }/>

            <Route path="/transaction-list" element={
                <Auth>
                    <TransactionListPage/>
                </Auth>
            }/>

            <Route path="/login" element={<LoginPage/>}/>
        </Routes>
    );
}