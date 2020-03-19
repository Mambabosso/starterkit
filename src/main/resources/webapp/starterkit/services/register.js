import Axios from "axios";
import registerUrl from "./url";

function register(username, email, password) {
    const data = {
        username: username,
        email: email,
        password: password
    };
    return Axios.post(registerUrl, data);
}

export default register;

