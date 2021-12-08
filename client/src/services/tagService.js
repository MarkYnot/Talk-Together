class tagService{

    // get all tags from backend
    getAllTags(){
        return axios.get(`http://localhost:3001/getAllTag`);
    }
    

}

export default new tagService();