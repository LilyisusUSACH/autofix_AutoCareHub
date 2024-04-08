import axios from "axios";

const autofixServer = import.meta.env.VITE_AUTOFIX_BACKEND_SERVER;
const autofixPort = import.meta.env.VITE_AUTOFIX_BACKEND_PORT;

console.log(autofixServer)
console.log(autofixPort)

export default axios.create({
    baseUrl: `http://${autofixServer}:${autofixPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});
