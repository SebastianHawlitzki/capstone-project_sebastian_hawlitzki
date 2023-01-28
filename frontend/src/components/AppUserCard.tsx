import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';



const card = (
    <React.Fragment>
        <CardContent>
            <Typography variant="h5" component="div" textAlign="center">
                Aktuelle Konto-Übersicht
            </Typography>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
                Username
            </Typography>
            <Typography variant="body2">
                Hier werden die Daten stehen
                <br />
                {'some text'}
            </Typography>
        </CardContent>
    </React.Fragment>
);

export default function AppUserCard(){
    return (
        <Box sx={{ minWidth: 275, padding: 2, display: 'flex', alignItems:'center', justifyContent:'center'}}>
            <Card variant="outlined">{card}</Card>
        </Box>
    );
}