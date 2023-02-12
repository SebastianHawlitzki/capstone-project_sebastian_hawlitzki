import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import useAppUser from "../hooks/useAppUser";
import {css, Divider, Grid, Paper, styled} from "@mui/material";


export default function AppUserCard() {

    const {appUser} = useAppUser();
    if (!appUser) {
        return <div>...</div>;
    }

    const convert = Intl.NumberFormat('de-DE', {

        style: 'currency',

        currency: 'EUR',

        minimumFractionDigits: 2,

    });

    const StyledPaper = styled(Paper)(
        css`
    background-color: rgba(25, 34, 49, 0.8);
    
  `
    );

    const paper = (
        <StyledPaper sx={{
            padding: 3,
            paddingTop: 2,
            marginLeft: 2,
            marginRight: 2,}}>
            <Grid container justifyContent={"space-between"}>
                <Grid item>
                    <Typography variant="h6" paddingTop={2} paddingBottom={2}
                                style={{fontSize: 18}}>
                        IBAN:
                    </Typography>
                </Grid>
                <Grid item>
                    <Typography variant="h6" paddingTop={2} paddingBottom={2}
                                style={{fontSize: 18}}>
                        DE 99 9009 9009 0000 0{appUser.accountNumber}
                    </Typography>
                </Grid>
            </Grid>
            <Divider/>
            <Grid container justifyContent={"space-between"}>
                <Grid item>
                    <Typography variant="h6" paddingTop={2} paddingBottom={2}
                                style={{fontSize: 18}}>
                        Kontostand:
                    </Typography>
                </Grid>
                <Grid item>
                    <Typography variant="h6" paddingTop={2} paddingBottom={2}
                                style={{fontSize: 18}}>
                        {convert.format(appUser.accountBalance)}
                    </Typography>
                </Grid>
            </Grid>
            <Divider/>
            <Grid container justifyContent={"space-between"}>
                <Grid item>
                    <Typography variant="h6" paddingTop={2}
                                style={{fontSize: 18}}>
                        Kontoart:
                    </Typography>
                </Grid>
                <Grid item>
                    <Typography variant="h6" paddingTop={2}
                                style={{fontSize: 18}}>
                        {appUser.accountType}
                    </Typography>
                </Grid>
            </Grid>
        </StyledPaper>
    );


    return (
        <Box>
            <Paper sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                marginLeft: 2,
                marginTop: 6,
                marginRight: 2,
            }}>
                <Typography variant="h6" component="div" textAlign="center" padding={1}
                            style={{fontSize: 20}}>
                    Kontoübersicht
                </Typography>
            </Paper>
            <Box sx={{width: '100%'}}>{paper}</Box>
        </Box>
    );
}