import React from 'react';
import './App.css';
import {BrowserRouter} from "react-router-dom";
import Root from "./Root";
import {createTheme, ThemeProvider} from "@mui/material/styles";


const theme = createTheme({
    shape: {
        borderRadius: 8,
    },
});


export default function App() {


    return (

        <BrowserRouter>
            <ThemeProvider theme={theme}>
                <Root />
            </ThemeProvider>
        </BrowserRouter>


    );
}

