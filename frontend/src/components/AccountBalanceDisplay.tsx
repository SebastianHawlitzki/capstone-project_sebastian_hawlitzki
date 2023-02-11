import {Grid, Paper} from "@mui/material";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import * as React from "react";
import useAppUser from "../hooks/useAppUser";


export default function AccountBalanceDisplay() {


    const {appUser} = useAppUser();
    if (!appUser) {
        return <div>...</div>;
    }

    const convert = Intl.NumberFormat('de-DE', {

        style: 'currency',

        currency: 'EUR',

        minimumFractionDigits: 2,

    });


    return(
        <Box sx={{
            display: 'flex',
            justifyContent:'center',
            alignItems: 'center',
        }}>
            <Paper elevation={1} style={{ padding: 10, margin:20, marginTop:50, width: '35ch'}}>
                <Grid container justifyContent={"space-between"}>
                    <Grid item>
                        <Typography variant="h6" style={{fontSize: 18}}>Kontostand:</Typography>
                    </Grid>
                    <Grid item>
                        <Typography variant="h6" style={{fontSize: 18}}> {convert.format(appUser.accountBalance)}</Typography>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
}