!<template>

    <!-- change password window-->
   <div class="changePasswordWindow">
       <form>
             <h1>Change Password</h1>
            <span>Current password:</span>
            <input class="password" type="text" v-if="pwdType" v-model="currentPassword" placeholder="Enter your password"/>
            <input class="password" type="password" placeholder="Enter your password" v-model="currentPassword" v-else />
            
            <br>
            <br>
            
            <span style="marginLeft: 5vw">New Password:</span> 
            <input class="password" type="text" v-if="pwdType" v-model="newPassword" placeholder="Enter your password"/>
            <input class="password" type="password" placeholder="Enter your password" v-model="newPassword" v-else />
            

            <img v-lazy="seen ? seenImg : unseenImg" @click="pwdType = !pwdType" v-on:mouseover="seen = !seen" v-on:mouseout="()=>seen = !seen" class="icon-eye" />
            <br/>
            <br/>

            <button class="ChangeButton" style="marginRight: 4vw" type="button" @click="changePassword">Change</button> 
           <input class="ChangeButton" type="reset" value="Reset">
         </form>
        </div>

</template>

<script>
import userService from '../services/userService';
  export default {
    
     data(){

      return{
        seen:false,
        unseenImg: "/static/see0.png",
        seenImg: "/static/see1.png",   
        pwdType: false,
        newPassword:"",
        currentPassword:"",
      }
    },

    props:{
      user:[]

    },

    methods:{
     async changePassword(){
       if(this.currentPassword == "" || this.newPassword == ""){
          alert("Your new password or current password is empty")
       }else{
          const changeresult =  await userService.changePassword(this.user.user_id, this.$md5(this.currentPassword) , this.$md5(this.newPassword))

         if(changeresult.data == "Old password is not correct, please try again"){
            alert(changeresult.data)
         }else{
            alert(changeresult.data)
            window.location.reload(false)
         }
       }
        
    }

  }
    
}
</script>

<style lang="less" scoped>


.changePasswordWindow{
   margin-top: 20vh;
   font-size: 2rem;
}


.changePasswordWindow h1{
   font-size: 5rem;
}

.icon-eye{
    height: 10vh;
    width: 5vw;   
}

.password{
    // margin-right: 2vw;
     width: 21vw;
     text-align: center;
      border: solid #e8e8e8;
      border-radius: 12px;
   
}

.ChangeButton{
    background: black;
    color: white;
    width: 10vw;
    border-radius: 12px;
}

.ChangeButton:hover{
    border: solid 0.5px #1677FF;
    color: #1677FF;
    transition: 0.5s;
}



</style>