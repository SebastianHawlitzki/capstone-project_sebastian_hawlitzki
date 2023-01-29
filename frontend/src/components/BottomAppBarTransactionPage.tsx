import {useNavigate} from "react-router-dom";
import {AppBar, Button, Toolbar} from "@mui/material";

export default function BottomAppBarTransactionPage() {

    const navigate = useNavigate();

    const toHomePage = () => {
        navigate('/');
    };

    const toTransactionListPage = () => {
        navigate('/transaction-list');
    };


    return (

        <AppBar position="fixed" color="primary" style={{ top: 'auto', bottom: 0 }}>
            <Toolbar style={{ display: "flex", justifyContent: "space-between" }}>
                <Button color="inherit" onClick={toHomePage}>Kontoübersicht</Button>
                <Button color="inherit" onClick={toTransactionListPage}>Saldenverlauf</Button>
            </Toolbar>
        </AppBar>

    );
}
