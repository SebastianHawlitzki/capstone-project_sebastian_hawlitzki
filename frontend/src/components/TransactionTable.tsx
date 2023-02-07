import * as React from "react";
import {
    Paper,
    styled,
    Table,
    TableBody,
    TableCell,
    tableCellClasses,
    TableContainer,
    TableHead,
    TableRow,
} from "@mui/material";
import {useEffect, useState} from "react";
import {Transaction} from "../models/Transaction";
import axios from "axios";
import Box from "@mui/material/Box";




const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.grey.A700,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));


export default function TransactionTable() {
/*
    const [transaction, setTransaction] = useState<Transaction>({
        senderAccountNumber: 0,
        receiverAccountNumber: 0,
        amount: 0,
        purpose: "",
        transactionDate: new Date()
    });

 */

    const [transactions, setTransactions] = useState<Transaction[]>([]);
    useEffect(() => {
        (async () => {
            const response = await axios.get("/api/transactions");
            setTransactions(response.data);
        })();
    }, []);

    const convert = Intl.NumberFormat('de-DE', {

        style: 'currency',

        currency: 'EUR',

        minimumFractionDigits: 2,

    });

//{transaction.transactionDate.toLocaleDateString()}
    return (
        <Box sx={{padding:3 }}>
        <TableContainer component={Paper}>
            <Table sx={{ width:'80%',margin:'auto' }} aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell>Datum</StyledTableCell>
                        <StyledTableCell align="right">Betrag</StyledTableCell>
                        <StyledTableCell align="right">Verwendungszweck</StyledTableCell>
                        <StyledTableCell align="right">Empfänger-IBAN</StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {transactions.map((transaction) => (
                        <StyledTableRow>
                            <StyledTableCell component="th" scope="row">

                            </StyledTableCell>
                            <StyledTableCell align="right">{convert.format(transaction.amount)}</StyledTableCell>
                            <StyledTableCell align="right">{transaction.purpose}</StyledTableCell>
                            <StyledTableCell align="right">{transaction.receiverAccountNumber}</StyledTableCell>
                        </StyledTableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
            </Box>
    );
}

