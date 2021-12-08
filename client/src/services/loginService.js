class testServce{

    //get data from backend
    getLoginDetail(username, password){
        return axios.get(`http://localhost:3001/login/${username}/${password}`);
    }


}

export default new testServce();