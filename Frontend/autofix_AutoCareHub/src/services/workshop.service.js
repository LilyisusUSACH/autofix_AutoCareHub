import httpClient from "../http-common"

const getReparations = () => {
    return httpClient.get('/api/repair/');
}

const getActiveReparations = () => {
    return httpClient.get('/api/repair/actives');
}

const postNewReparation = (data) => {
    return httpClient.post('api/repair/',data)
}

export default {getReparations, getActiveReparations, postNewReparation};