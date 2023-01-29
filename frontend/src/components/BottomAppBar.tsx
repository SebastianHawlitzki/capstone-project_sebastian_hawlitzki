import { AppBar, Toolbar, Button } from '@mui/material';
import {useNavigate} from "react-router-dom";


export default function BottomAppBar() {

    const navigate = useNavigate();

    const toTransactionPage = () => {
        navigate('/transaction');
    };

    const toTransactionListPage = () => {
        navigate('/transaction-list');
    };


    return (

            <AppBar position="fixed" color="primary" style={{ top: 'auto', bottom: 0 }}>
                <Toolbar style={{ display: "flex", justifyContent: "space-between" }}>
                    <Button color="inherit" onClick={toTransactionPage}>Überweisung</Button>
                    <Button color="inherit" onClick={toTransactionListPage}>Saldenverlauf</Button>
                </Toolbar>
            </AppBar>

    );
}
