import { AppBar, Toolbar, Button } from '@mui/material';


export default function BottomAppBar() {

    return (

            <AppBar position="fixed" color="primary" style={{ top: 'auto', bottom: 0 }}>
                <Toolbar style={{ display: "flex", justifyContent: "space-between" }}>
                    <Button color="inherit">Überweisung</Button>
                    <Button color="inherit">Saldenverlauf</Button>
                </Toolbar>
            </AppBar>

    );
}
