import {FormEvent, useCallback, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";
import Box from "@mui/material/Box";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import * as React from "react";
import {FormControl, InputLabel, MenuItem, Select, SelectChangeEvent} from "@mui/material";



export default function SignUpPage () {
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
        accountType: ""
    });

    const [error, setError] = useState<string[]>([]);

    // React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    const handleChange = useCallback(
        (event: React.ChangeEvent<HTMLInputElement>) => {
            const {name, value} = event.target;
            setCredentials({...credentials, [name]: value});
        },
        [credentials, setCredentials]
    );

    const selectChange = useCallback(
        (event: SelectChangeEvent) => {
            const {name, value} = event.target;
            setCredentials({...credentials, [name]: value});
        },
        [credentials, setCredentials]
    );



    const navigate = useNavigate();

    const location = useLocation();

    const signUp = useCallback(
        async (e: FormEvent<HTMLFormElement>) => {
            e.preventDefault();

            setError([]);

            try {
                await axios.post("/api/app-users", credentials);
                navigate("/login" + location.search);
            } catch (e) {
                setError((error) => [
                    ...error,
                    "Invalid user data"
                ]);
            }
        },
        [credentials, navigate, location]
    );



    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{mr: 2}}>
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}  style={{fontSize: 24}}>
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
                    <Avatar sx={{ m: 1, bgcolor: 'primary' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign up
                    </Typography>
                    <Box component="form" onSubmit={signUp} noValidate sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="username"
                            label="Username"
                            name="username"
                            autoComplete="username"
                            autoFocus
                            value={credentials.username}
                            onChange={handleChange}
                        />
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">Kontoart</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={credentials.accountType}
                                name={"accountType"}
                                label="Kontoart"
                                onChange={selectChange}
                            >
                                <MenuItem value={"Privatkonto"}>Privatkonto</MenuItem>
                                <MenuItem value={"Geschäftskonto"}>Geschäftskonto</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            value={credentials.password}
                            onChange={handleChange}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2, bgcolor: 'primary' }}
                        >
                            Sign Up
                        </Button>
                    </Box>
                </Box>
            </Container>
        </Box>

    );
}

/*
Termin ausmachen:
                        <FormControl fullWidth>
                            <InputLabel id="accountType-select-label">Kontoart</InputLabel>
                            <Select
                                labelId="accountType-select-label"
                                id="accountType-select"
                                value={credentials.accountType}
                                label="AccountType"
                                onChange={handleSelectChange}
                            >
                                <MenuItem value="Girokonto">Girokonto</MenuItem>
                                <MenuItem value="Sparkonto">Sparkonto</MenuItem>
                                <MenuItem value="Geschäftskonto">Geschäftskonto</MenuItem>
                            </Select>
                        </FormControl>
 */