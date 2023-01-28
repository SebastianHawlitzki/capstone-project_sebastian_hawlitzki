import {useNavigate} from "react-router-dom";
import {useCallback} from "react";
import axios from "axios";
import Button from "@mui/material/Button";

export default function LogoutButton () {
    const navigate = useNavigate();

    const logout = useCallback(async () => {
        await axios.get("/api/app-users/logout");
        navigate("/login");
        window.document.cookie = "";
    }, [navigate]);

    return (
    <Button variant="text"
            color="inherit"
            onClick={logout}>Logout</Button>
    )
}