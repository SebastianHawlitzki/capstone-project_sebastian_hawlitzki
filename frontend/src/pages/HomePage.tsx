import LogoutButton from "../components/LogoutButton";
import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import BottomAppBar from "../components/BottomAppBar";
import AppUserCard from "../components/AppUserCard";

export default function HomePage() {




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
                    <Typography variant="h5" component="div" sx={{flexGrow: 1}}>
                        SmartBanking
                    </Typography>
                    <LogoutButton/>
                </Toolbar>
            </AppBar>
            <AppUserCard/>
            <BottomAppBar/>
        </Box>

    );
}