import Axios from "axios";
import registerUrl from "./url";

function register(username, email, password) {
    return Axios.post(registerUrl, {
        "data": {
            "username": username,
            "email": email,
            "password": password
        }
    });
}

export default register;

