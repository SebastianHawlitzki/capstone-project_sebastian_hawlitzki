import Box from "@mui/material/Box";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import LogoutButton from "../components/LogoutButton";
import * as React from "react";
import BottomAppBarTransactionPage from "../components/BottomAppBarTransactionPage";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {Stack} from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import Container from "@mui/material/Container";


export default function TransactionPage() {


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
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        SmartBanking
                    </Typography>
                    <LogoutButton/>
                </Toolbar>
            </AppBar>
            <Container component="main" maxWidth="xs">
                <Box
                    sx={{
                        marginTop: 6,
                        display: 'flex',
                        justifyContent:'center',
                        alignItems: 'center',
                        textAlign: 'center'
                    }}
                >
                    <Box
                        component="form"
                        sx={{
                            '& .MuiTextField-root': {m: 1, width: '25ch'},
                        }}
                        noValidate
                        autoComplete="off"
                    >
                        <Box>
                            <TextField
                                id="outlined-number"
                                label="IBAN"
                                type="number"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <TextField
                                id="outlined-number"
                                label="Betrag"
                                type="number"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <TextField
                                id="outlined-input"
                                label="Verwendungszweck"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <Stack direction="row" spacing={2} sx={{
                                marginTop: 2,
                                display: 'flex',
                                justifyContent:'center',
                                alignItems: 'center',
                            }}>
                                <Button variant="contained" endIcon={<SendIcon/>}>
                                    Überweisen
                                </Button>
                            </Stack>
                        </Box>
                    </Box>
                </Box>
            </Container>
            <BottomAppBarTransactionPage/>
        </Box>
    );
}