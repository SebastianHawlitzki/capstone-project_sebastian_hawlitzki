import React from 'react';
import {BrowserRouter} from "react-router-dom";
import Root from "./Root";
import {createTheme, ThemeProvider} from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";


export default function App() {



    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
            primary: {
                main: '#297abf',
            },
            secondary: {
                main: '#17669a',
            },
            background: {
                default: '#192231',
                paper: '#17669a',
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

