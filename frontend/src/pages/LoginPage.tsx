import {FormEvent, useCallback,useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";



export default function LoginPage () {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");

    const navigate = useNavigate();

    const toSignUpPage = () => {
        navigate('/signup');
    };






    const login = useCallback(async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        setError("");

        try {
            await axios.post("/api/app-users/login", null, {
                headers: {
                    "Authorization": "Basic " + window.btoa(
                        username + ":" + password
                    )
                }
            });

            navigate("/");
        } catch (e) {
            setError("Invalid username or password");
        }
    }, [navigate, password, username]);


function Copyright(props: any) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://capstone-sebastian-hawlitzki.fly.dev/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}



    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"

                        aria-label="menu"
                        sx={{mr: 2}}>
                    </IconButton>
                    <Typography variant="h5" component="div" sx={{flexGrow: 1}}>
                        SmartBanking
                    </Typography>
                </Toolbar>
            </AppBar>
            {error && <div>{error}</div>}
            <CssBaseline />

            <Container component="main" maxWidth="xs">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1 }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box component="form" onSubmit={login} noValidate sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="username"
                            label="Username"
                            name="username"
                            autoComplete="username"
                            autoFocus
                            onChange={e => setUsername(e.target.value)}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            onChange={e => setPassword(e.target.value)}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2}}
                        >
                            Sign In
                        </Button>
                        <Grid container justifyContent="center">
                                <Link onClick={toSignUpPage} variant="body2">
                                    {"Don't have an account? Sign Up"}
                                </Link>
                        </Grid>
                    </Box>
                </Box>
                <Copyright sx={{ mt: 8, mb: 4 }} />
            </Container>
        </Box>
    );
}