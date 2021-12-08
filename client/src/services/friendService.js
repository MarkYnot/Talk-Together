class friendService{


  addFriend(userId, toUserId){

    return axios.post(`http://localhost:3001/addFriend/${userId}/${toUserId}`).then(res=>{return res;})
  }


}

export default new friendService();