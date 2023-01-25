import React from 'react';
import './App.css';
import {BrowserRouter} from "react-router-dom";
import Root from "./Root";


export default function App() {
    return (
        <BrowserRouter>
            <Root/>
        </BrowserRouter>
    );
}

