import React from 'react';
import './App.css';
import {BrowserRouter} from "react-router-dom";
import Root from "./Root";
import {createTheme, ThemeProvider} from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";


export default function App() {



    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
            primary: {
                main: '#5893df',
            },
            secondary: {
                main: '#2ec5d3',
            },
            background: {
                default: '#192231',
                paper: '#24344d',
            },
        },
    });
    return (

        <BrowserRouter>
            <ThemeProvider theme={darkTheme}>
                <CssBaseline/>
            <Root/>
            </ThemeProvider>
        </BrowserRouter>


    );
}

