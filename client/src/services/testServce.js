class testServce{

    //从后台的到表"book"的数据
    getAllTestData(){
        return axios.get(`http://localhost:3001/test`);
    }


}

export default new testServce();
