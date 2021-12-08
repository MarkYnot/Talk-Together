class userServce{
    
    getAllLanguage(){
         return axios.get(`http://localhost:3001/getAllLanguage`)
    }

    forgetPassword(username){
      return axios.get(`http://localhost:3001/mail/forget/${username}`)
    }

    sendEmail(firstname, lastname, email){
      return axios({
        url: 'http://localhost:3001/mail/post',
        method: 'post',
        headers:{
            'Content-Type': 'application/json'
        },
        data:{
            firstname: firstname,
            lastname: lastname,
            email: email
        }
    }).then((res)=>{
        return res
    })
    }

    signInDetail(username, password, firstname, lastname, email, tags, native, translate){
      return axios({
          url: 'http://localhost:3001/UserSignin',
          method: 'post',
          headers:{
              'Content-Type': 'application/json'
          },
          data:{
              username: username,
              password: password,
              firstname: firstname,
              lastname: lastname,
              email: email,
              tag: tags,
              native: native,
              translate: translate
          }
      }).then((res)=>{
          return res
      })
  }

    changeAvantar(imgUrl, userid){
      return axios({
            url: 'http://localhost:3001/update_portrait',
            method: 'post',
            data:{
              imgUrl: String(imgUrl),
              user_id:userid
               
            }
         })

    }

    changeBackground(imgUrl, userid){
      return axios({
            url: 'http://localhost:3001/update_background',
            method: 'post',
            data:{
              imgUrl: String(imgUrl),
              user_id: userid 
               
            }
         })

    }

    updateUserInfo(user){
      console.log(JSON.stringify(user))
       return axios({
        url: 'http://localhost:3001/update_info',
        method: 'post',
        headers:{
          'Content-Type': 'application/json'
       },
        data:JSON.stringify(user)
       }).then(result=>{return result})

    }


    getUserInfo(userid){
      return axios.get(`http://localhost:3001/get_info/${userid}`)
    }


    changePassword(user_id, currentPassword, newPassword){
       return axios({
        url: 'http://localhost:3001/change_pwd',
        method: 'post',
        data:{
           user_id: user_id,
           currentPassword: currentPassword,
           newPassword: newPassword
          }
        })
  
    }

    deleteFriend(friendid){
      return axios.post(`http://localhost:3001/deleteFriend/${friendid}`).then(result=> {return result})
     }

     logout(userid){
      return axios.post(`http://localhost:3001/logout/${userid}`)

  }



}

export default new userServce();