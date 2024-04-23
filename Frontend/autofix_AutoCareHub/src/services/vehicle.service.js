import httpClient from "../http-common";

const postNewVehicle = (data) => {
    return httpClient.post("/api/vehicle/",data)
};

export default { postNewVehicle };