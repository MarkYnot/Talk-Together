!<template>
<div class="allHeader">

      <router-link to="/">
          <img class="headerTilte" v-lazy="'/static/logotitile.png'" alt="headerTitle">
      </router-link>



<!-- <slot name="user"></slot> -->
   
      <!-- <a-switch default-checked class="switch" style="float: right;" @change="onChange" />
      <span class="LightOn">LightOn</span> -->

        <a-menu
              theme="light"
              mode="horizontal"
              style="{lineHeight: 64px}"
              class="userInfoSlot"
            >
                <a-sub-menu >
                    <span slot="title" 
                    >
                    <img alt="" v-lazy="'/static/endUser.png'" style="height: 4vh; borderRadius: 50%; marginRight: 1vw" v-if="!hasPhoto|| !photoCorrect"/>
                      <img alt="" v-lazy="this.photo" style="height: 4vh;  width: 3vw; borderRadius: 50%; marginRight: 1vw" v-if="hasPhoto&photoCorrect"/>
                    <span style="fontSize: 1.3vw">{{username}}</span>
                    </span>

                    <a-menu-item key="setting:1">
                       <router-link to="/profile"/>
                        Setting
                      </a-menu-item>

                      <a-menu-item key="setting:2" @click="logout">
                        Login Out
                      </a-menu-item>
              
          
                </a-sub-menu>
            
            </a-menu>
      
      
</div>
  
</template>

<script>
  import userService from '../services/userService';
  export default {

    data(){
       return {
          username: "",
          photo: "",
          hasPhoto: false,
          photoCorrect: false
       }

    }, 
    components:{
     userService

    },

    created(){
      
       
    },

    
    mounted(){
      
           
        const userid = this.$cookies.get('userid')
        if(userid){
              userService.getUserInfo(userid).then(user=>{
                    console.log(user)
                 if(user.data[0].photo || user.data[0].username){
                    
                    this.username = user.data[0].username
                    this.photo = user.data[0].photo
                    this.hasPhoto = true
                    this.checkPhoto()
                    this.$emit('currentUser',user)
                 }
        });
        
        }else{
            alert('please login first')
            this.$router.push({path: "/login"})

      }


    },

    methods:{
        onChange(checked) {
      // console.log(`a-switch to ${checked}`);
    },

    checkPhoto(){
   
      if(this.photo.includes('base64')){
          
          this.photoCorrect = true
      }


    },

    async logout(){
       const userid = this.$cookies.get('userid')
      let confirmation = confirm('Are you sure log out?') 
             if(confirmation){
                this.$cookies.remove("firstname"); 
                this.$cookies.remove("lastname");
                this.$cookies.remove("state");
                this.$cookies.remove("username");
                await userService.logout(userid);

                this.$cookies.remove("userid");
                  sessionStorage.clear();
                this.$router.push({path:"/login"})
            }
       }

    }
  }
</script>

<style lang="less" scoped>

.allHeader{
  height: 53px;
  padding: 0;
  line-height: 4.5vh;
  width: 100vw;
  background: white;
  overflow: hidden;
  text-align: center;
  border-radius: 5px;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
  display: grid;
    grid-template-columns: 25% 36% 3% 9% 25%; 
    grid-template-rows: 100%;
}

/deep/ .ant-layout-header{
  height: 53px;
  padding: 0;
  line-height: 64px;
  width: 100vw;
  background: white;
  overflow: hidden;
  border-radius: 5px;
   -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;

  }


.headerTilte{

   float: left;
   margin-left: 4vw;
   height: 5vh;
   width: 12.5vw;
  //  margin-top:0.5vh;
   grid-row:1 ;
   grid-column: 1;
}

.switch{
  // position: relative;
  // left:22vw;
  margin-right: 1vw;
  margin-top: 1.5vh;
  grid-row:1;
  grid-column: 3;
  width:2vw;
}

.LightOn{
  // position: relative;
  // left: 28vw;
  margin-left: 0.5vw;
  margin-top: 0.1vh;
  font-size: 1.3vw;
  grid-row:1 ;
  grid-column:4;
  float:left;
  width: 4vw;
  z-index:99;
   
}

.userInfoSlot{
  position: relative;
  left: 8vw;
  top: 0.1vh;
  grid-column: 5;
  grid-row: 1;
}

/deep/ .ant-menu-submenu{
   border-bottom: 0 !important; 
  
}

</style>