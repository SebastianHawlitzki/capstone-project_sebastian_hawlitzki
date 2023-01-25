import React, {useEffect} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";

export default function Auth (
    {
        children,
    } : {
        children: React.ReactNode,
    }
) {
    const [isAuthenticated, setIsAuthenticated] = React.useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        (async () => {
            try {
                await axios.get("/api/app-users/me");
                setIsAuthenticated(true);
            } catch (e) {
                setIsAuthenticated(false);
                navigate("/login");
            }
        })();
    }, [location.pathname, navigate]);

    return (
        <>
            {isAuthenticated && children}
        </>
    );
}