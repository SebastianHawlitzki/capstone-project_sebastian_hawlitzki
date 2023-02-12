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
import useAppUser from "../hooks/useAppUser";


const StyledTableCell = styled(TableCell)(({theme}) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: '#17669a',
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 15,
        fontWeight: 400
    },
}));

const StyledTableRow = styled(TableRow)({
    backgroundColor: 'rgba(25,34,49,0.9)',

    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
});



export default function TransactionTable() {


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


    const {appUser} = useAppUser();
    if (!appUser) {
        return <div>...</div>;
    }


    const plusOrMinusAmount = (transaction: Transaction) => {
        if (appUser.accountNumber === transaction.senderAccountNumber) {
            return "- ";
        } else {
            return "+ ";
        }
    };


    const tableTextHighlight = (transaction: Transaction) => {
        if (appUser.accountNumber === transaction.senderAccountNumber) {
            return {
                color: 'rgba(200, 0, 0, 1)'
            };
        } else {
            return {
                color: 'rgba(0, 160, 0, 1)'
            };
        }
    };

    return (
        <Box sx={{paddingTop: 4, paddingLeft: 3, paddingRight: 3}}>
            <TableContainer component={Paper} style={{height: '400px', overflowY: 'scroll'}}>
                <Table sx={{width: '190%', margin: 'auto'}} stickyHeader aria-label="sticky table">
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
                            <StyledTableRow key={transaction.transactionDate}>
                                <StyledTableCell component="th" scope="row">
                                    {new Date(transaction.transactionDate).toLocaleDateString("de-DE", {
                                        weekday: 'short',
                                        day: 'numeric',
                                        month: 'short',
                                        year: 'numeric'
                                    })}
                                </StyledTableCell>
                                <StyledTableCell align="right"
                                                 style={tableTextHighlight(transaction)}>{plusOrMinusAmount(transaction)}{convert.format(transaction.amount)}</StyledTableCell>
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

