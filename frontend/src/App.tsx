import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage";
import TransactionPage from "./pages/TransactionPage";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/transaction" element={<TransactionPage/>}/>
            </Routes>
        </BrowserRouter>
    );
}

