import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import useAppUser from "../hooks/useAppUser";
import {Divider, Grid} from "@mui/material";


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


    const card = (
        <React.Fragment>
            <CardContent>
                <Typography variant="h5" component="div" textAlign="center" paddingBottom={1}
                            style={{fontSize: 22}}>
                    Aktuelle Kontoübersicht
                </Typography>
                <Divider/>
                <Typography sx={{mb: 1.5}} color="text.secondary" paddingTop={1}>
                    {appUser.username}
                </Typography>
                <Grid container justifyContent={"space-between"}>
                    <Grid item>
                        <Typography variant="body1" paddingTop={2} paddingBottom={2}
                                    style={{fontSize: 18, fontWeight: 400}}>
                            IBAN:
                        </Typography>
                    </Grid>
                    <Grid item>
                    <Typography variant="body1" paddingTop={2} paddingBottom={2}
                                style={{fontSize: 18, fontWeight: 400}}>
                        DE 99 9009 9009 0000 0{appUser.accountNumber}
                    </Typography>
                    </Grid>
                </Grid>
                    <Divider/>
                    <Grid container justifyContent={"space-between"}>
                        <Grid item>
                            <Typography variant="body1" paddingTop={2} paddingBottom={2}
                                        style={{fontSize: 18, fontWeight: 400}}>
                                Kontostand:
                            </Typography>
                        </Grid>
                        <Grid item>
                            <Typography variant="body1" paddingTop={2} paddingBottom={2}
                                        style={{fontSize: 18, fontWeight: 400}}>
                                {convert.format(appUser.accountBalance)}
                            </Typography>
                        </Grid>
                    </Grid>
                    <Divider/>
                    <Grid container justifyContent={"space-between"}>
                        <Grid item>
                            <Typography variant="body1" paddingTop={2}
                                        style={{fontSize: 18, fontWeight: 400}}>
                                Kontoart:
                            </Typography>
                        </Grid>
                        <Grid item>
                            <Typography variant="body1" paddingTop={2}
                                        style={{fontSize: 18, fontWeight: 400}}>
                                {appUser.accountType}
                            </Typography>
                        </Grid>
                    </Grid>

            </CardContent>
        </React.Fragment>
);


return (
    <Box sx={{
        minWidth: 275,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        marginLeft: 2,
        marginTop: 6,
        marginRight: 2,
    }}>
        <Card variant="outlined">{card}</Card>
    </Box>
);
}