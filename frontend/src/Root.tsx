import {Route, Routes} from "react-router-dom";
import Auth from "./components/Auth";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import React from "react";
import LogoutButton from "./components/LogoutButton";
import TransactionPage from "./pages/TransactionPage";

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
                    <LogoutButton/>
                    <TransactionPage/>
                </Auth>
            }/>

            <Route path="/login" element={<LoginPage/>}/>
        </Routes>
    );
}