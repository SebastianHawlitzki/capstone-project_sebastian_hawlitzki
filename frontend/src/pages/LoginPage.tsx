import {FormEvent, useCallback, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function LoginPage () {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");

    const navigate = useNavigate();

    const login = useCallback(async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        setError("");

        try {
            await axios.post("/api/app-users/login", null, {
                headers: {
                    "Authorization": "Basic " + window.btoa(
                        username + ":" + password
                    )
                }
            });

            navigate("/");
        } catch (e) {
            setError("Invalid username or password");
        }
    }, [navigate, password, username]);

    return (
        <div>
            <h1>Login Page</h1>

            {error && <div>{error}</div>}

            <form onSubmit={login}>
                <div>
                    <input
                        placeholder="Username"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                </div>

                <div>
                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                </div>

                <div>
                    <button>Login</button>
                </div>
            </form>
        </div>
    );
}