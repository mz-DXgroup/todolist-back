import axios from "axios";

function showMenuApi() {
  const BASE_URL =  'http://localhost:8080/api/members';
  return axios.get(BASE_URL);
}

export { showMenuApi };