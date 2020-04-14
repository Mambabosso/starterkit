import Axios from "axios";

const loginUrl = "/api/auth/login";


function login(username, password) {
  return Axios.post(loginUrl, {
    data: {
      username: username,
      password: password
    }
  });
}

export default login;
