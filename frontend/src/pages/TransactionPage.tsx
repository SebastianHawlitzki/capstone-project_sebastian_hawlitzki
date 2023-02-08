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
import {Snackbar, Stack} from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import Container from "@mui/material/Container";
import {Transaction} from "../models/Transaction";
import {FormEvent, useState} from "react";
import axios from "axios";
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import AccountBalanceDisplay from "../components/AccountBalanceDisplay";


const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
    props,
    ref,
) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function TransactionPage() {

    const [transaction, setTransaction] = useState<Transaction>({
        senderAccountNumber: 0,
        receiverAccountNumber: 0,
        amount: 0,
        purpose: "",
        transactionDate: new Date().toLocaleDateString(),
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setTransaction({
            ...transaction,
            [e.target.name]: e.target.value,
        });
    };

    const createTransaction = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const response = await axios.post("/api/transactions", transaction, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        setTransaction({
            ...transaction,
            ...response.data,
        });
    };



    const [open, setOpen] = React.useState(false);

    const handleClick = () => {
        setOpen(true);
        setTimeout(() => {
            window.location.reload();
        }, 3000);
    };

    const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };





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
                            '& .MuiTextField-root': {m: 1, width: '35ch'},
                        }}
                        noValidate
                        autoComplete="off"
                        onSubmit={createTransaction}
                    >
                        <Box>
                            <TextField
                                id="outlined-number"
                                label="IBAN"
                                type="number"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                value={transaction.receiverAccountNumber}
                                onChange={handleChange}
                                name="receiverAccountNumber"
                            />
                            <TextField
                                id="outlined-number"
                                label="Betrag in €"
                                type="number"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                value={transaction.amount}
                                onChange={handleChange}
                                name="amount"
                            />
                            <TextField
                                id="outlined-input"
                                label="Verwendungszweck"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                value={transaction.purpose}
                                onChange={handleChange}
                                name="purpose"
                            />
                            <Stack direction="row" spacing={2} sx={{
                                marginTop: 2,
                                display: 'flex',
                                justifyContent:'center',
                                alignItems: 'center',
                                width: '100%'
                            }}>
                                <Button onClick={handleClick} type="submit" variant="contained" endIcon={<SendIcon/>} style={{width: '35ch'}}>
                                    Überweisen
                                </Button>
                                <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}
                                          anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                                          style={{ position: 'absolute', bottom: '80px', left: '20px', right: '20px' }}>
                                    <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                                        Überweisung bestätigt!
                                    </Alert>
                                </Snackbar>
                            </Stack>
                        </Box>
                    </Box>
                </Box>
            </Container>
            <Box sx={{
                display: 'flex',
                justifyContent:'center',
                alignItems: 'center',
            }}>
            <AccountBalanceDisplay/>
            </Box>
            <BottomAppBarTransactionPage/>
        </Box>
    );
}