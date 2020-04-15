import Axios from "axios";

const registerUrl = "/api/register/new";

function register(username, email, password) {
    return Axios.post(registerUrl, {
        data: {
            username: username,
            email: email,
            password: password,
        },
    });
}

export default register;
