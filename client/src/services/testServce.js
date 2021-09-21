class testServce{

    //从后台的到表"book"的数据
    getAllBookData(){
        return axios.get(`http://localhost:3001/book/findAll`);
    }


}

export default new testServce();
