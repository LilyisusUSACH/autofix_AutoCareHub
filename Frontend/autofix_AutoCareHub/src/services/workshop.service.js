import httpClient from "../http-common"

const getReparations = () => {
    return httpClient.get('/api/repair/');
}

const getActiveReparations = () => {
    return httpClient.get('/api/repair/actives');
}

export default {getReparations, getActiveReparations};