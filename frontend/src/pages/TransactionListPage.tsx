import Box from "@mui/material/Box";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import LogoutButton from "../components/LogoutButton";
import * as React from "react";
import BottomAppBarTransactionListPage from "../components/BottomAppBarTransactionListPage";
import TransactionTable from "../components/TransactionTable";
import AccountBalanceDisplay from "../components/AccountBalanceDisplay";

export default function TransactionListPage () {


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
            <Box sx={{marginBottom:5}}>
           <TransactionTable/>
                <AccountBalanceDisplay/>
            </Box>
            <BottomAppBarTransactionListPage/>
        </Box>
    );
}