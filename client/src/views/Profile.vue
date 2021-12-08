<template>
  <div class = "profile_container">
     

  <div class="sideBar">
    <SideBar/>
  </div>

   <div class="hpHeader">
       
       <Header/>
        
  </div>

   <div class="mainBody">

    <img alt="" class="cancelChangePassword" v-lazy="'/static/false.png'" style="width: 3vw; height: 5vh; float: right" v-if=!this.profileInfo @click="profileInfo = !profileInfo"/>

       <transition name="passwordWindow-fade">
      <ChangePassword v-if=!this.profileInfo :user="user"></ChangePassword>
       </transition>


       <!-- edit profilio window-->
        <transition name="slide-fade">
        <div id="profilioLeft" class="profilio_left" v-if = this.profileInfo>

            <strong style="fontSize: 3rem; textAlign: left; float: left; ">Personal Information</strong>
            <br/><br/><br/><br/>
            <span class="subTitle">User name:</span><br/>
            <input v-model="user.username" id="username" class="oneInput" type="text" style="width: 35vw; "  /><br/><br/><br/>


            <span class="subTitle">First name:</span>
            <span style=" fontSize: 1vw; marginLeft: 1vw">Last name:</span>

             <br/>
            <input v-model="user.firstname" id="firstname" class="oneInput" type="text" style="width: 20vw;" />
            <input v-model="user.lastname" id="lastname" type="text" style="width: 14vw; marginLeft: 4vw; right: 5.5vw" />
            <br/><br/>

            <span class="subTitle" >Email address:</span><br>
            <input v-model="user.email" id="email" class="oneInput" type="text" style="width: 35vw; "/><br/><br/><br/>

        
             <span class="subTitle" >Gender:</span><br/>
             
            <div class="gender">
             <input type="radio" class="oneInput" name= "gender" value="male" v-model="user.gender"/><span class="genderRadioSpan">Male</span>
             
            <input type="radio" class="oneInput" name="gender" value="female" v-model="user.gender"/><span class="genderRadioSpan">Female</span>
              </div>
              <br/><br/><br/>


              <span class="subTitle">Native Language:</span>
             <span style=" fontSize: 1vw; marginRight: 6vw" >Translate To:</span>

             <br/>
           <select v-model="user.native_language" class="oneInput" style="width: 16vw;">
                <option value="zh">Marderin</option>
                <option value="cht">Marderin(trandition)</option>
                 <option value="yue">Cantonese</option>
                 <option value="en">English</option>
                 <option value="kor">Korean</option>
                 <option value="jp">Japenese</option>
                 <option value="fra">Franch</option>
            </select>

              
            <select v-model="user.translate" class="oneInput" style="width: 16vw;">
                <option value="zh">Marderin</option>
                <option value="cht">Marderin(trandition)</option>
                 <option value="yue">Cantonese</option>
                 <option value="en">English</option>
                 <option value="kor">Korean</option>
                 <option value="jp">Japenese</option>
                 <option value="fra">Franch</option>
            </select>
            
            <br/><br/><br/>

            <span class="subTitle">Faviourite topics:</span><br/><br/>


        
            <a-tag v-for="(item,index) in user.Tag" :key = item.tag_id closable class="topicsTab" :value="item.tag_id" @close="()=>preventDefault(index)" color="blue">
               {{item.tag_name}}
            </a-tag>

         
            <br/><br/><br/><br/>


            <select id="allTagSelected" class="tag_select" style="width: 33vw;">
              <option v-for="(item, index) in this.allTags" :key="index" :value="item.tag_id">{{item.tag_name}}</option>
          
           </select>

           <button type="button" class="addTag_buttom" @click="addTag">Add</button>
            
            <br/><br/><br/>

        <button type="button" class="profileLeft_bottom" @click="updateInfo">Update</button>
        <button  type="button" class="changePasswordButton" @click="switchToChangePwd"> Change Password</button>
          
       </div>
      </transition>


 <!-- @change="handleChange" -->
       <!-- :headers="headers" -->
        <!-- action="https://www.mocky.io/v2/5cc8019d300000980a055e76" -->
       <transition name="slide-fade">
      <div id="profilioRight" class="profilio_right" v-if = this.profileInfo>
             <span class="avatarSpan">Avantar:</span><br/>
              <img alt="" v-lazy="this.photo" v-if="hasPhoto&photoCorrect"/>
             <img v-lazy="'/static/endUser.png'" alt="" v-if="!hasPhoto|| !photoCorrect"><br/>
             <a-upload
                  name="file"
                  :customRequest= dummyRequest
                  :multiple="false"
                  :file-list= this.selectedFileList
                  accept = "image/*"
                  :beforeUpload= handlePhotoUpload
                  @change="onChange"
              >
                <a-button :style="{}"> <a-icon type="upload" /> Click to Upload new Avantar</a-button>
          
          </a-upload>  <br/>
          <button type="button" class="profileRight_bottom" @click="onSuccess">Change Avantar</button>

              <br/>
              <br/>
              <br/>
              <br/>
       
        <span class="avatarSpan">Background:</span><br/>
            <img alt="" v-lazy="this.background" v-if="hasBackground"/>
             <img v-lazy="'/static/logo.png'" alt="" v-if="!hasBackground"><br/>
              <a-upload
                    name="file"
                    :multiple="false"
                    :customRequest= dummyRequest
                    :file-list= this.backgroundFileList
                     accept = "image/*"
                     :beforeUpload= handlePhotoUpload
                     @change="backgroundOnChange"
                  
                >
                  <a-button :style="{}"> <a-icon type="upload" /> Click to Upload new Background</a-button>
             </a-upload> <br/>
              
              <button type="button" class="profileRight_bottom" @click="backgroundOnSuccess" >Change Background</button>

        </div>
        </transition>
     

   </div>
</div>
</template>

<script>
  import SideBar from "../components/sideBar.vue"
  import Header from "../components/header.vue"
  import ChangePassword from "../components/changePassord.vue"
  import userService from '../services/userService';
  import tagService from '../services/tagService'

export default {
  name: "Profile.vue",
  data(){
    return{
    
      test:[], 
      profileInfo: true,
      userName: "Mark",
      avatarUrl:"",
      selectedFile:"",
      selectedFileList:[],
       backgroundFile:"",
      backgroundFileList:[],
       backgroundUrl:"", 
       background:"",
       user:[],
       photo:"",
       photoCorrect: false,
       hasPhoto: false,
       hasBackground:false,
       allLanguage:[],
       allTags:[],
       currentTag: false

    }
  },

  components:{
    SideBar,
    Header,
    ChangePassword,
    tagService
    
  },
  created(){
    const userid = this.$cookies.get('userid')
      if(userid){
        userService.getUserInfo(userid).then(user=>{
           if(user !=null){
              // var tags = ['sport', 'music', 'art', 'science']
              // user.data[0].Tag.push(...tags)
              this.user = user.data[0]
              console.log(this.user)
              this.photo = user.data[0].photo
              this.hasPhoto = true
              this.checkPhoto()
              this.checkBackground(user.data[0].background)
             }

        })

      }
   
  },

  async mounted(){
        const preferLanguage = await userService.getAllLanguage()
        const allTag = await tagService.getAllTags()
        this.allTags = allTag.data
        this.allLanguage = preferLanguage.data
  },

  methods:{
     preventDefault(e) {
        //  this.user.Tag.splice(tag, 1)
      this.user.Tag.splice(e, 1)
     },

    checkPhoto(){
   
      if(this.photo.includes('base64')){
          
          this.photoCorrect = true
       
      }


    },

    checkBackground(backgroundUrl){
   
      if(backgroundUrl&&backgroundUrl.includes("base64")){
          
          this.hasBackground = true
          this.background = backgroundUrl
      }


    },

     switchToChangePwd(){
       const profileLeft = document.getElementById('profilioRight')
       const profileRight = document.getElementById('profilioLeft')
            
            setTimeout(()=>{
                  profileLeft.style.opacity = 0
                  profileRight.style.opacity = 0
              setTimeout(()=>{
                  this.profileInfo = false
                }, 500)
            }, 200)
      
     },

     getBase64(img, callback) {
          const reader = new FileReader();
           reader.readAsDataURL(img);
           reader.onload = ()=> callback(reader.result)
     },

     onChange(info){
        const nextState = {};
        console.log(info.file)
        switch(info.file.status){
          case "uploading":
          nextState.selectedFileList = [info.file]
          break;

          case "done":
            
            nextState.selectedFile = info.file;
            nextState.selectedFileList = [info.file];
            this.getBase64(info.file.originFileObj, imageUrl =>{
                    this.avatarUrl = imageUrl
            }); 
            
           
            
            break;

          default:
             nextState.selectedFile = null;
             nextState.selectedFileList = [];
        }
           this.selectedFile = nextState.selectedFile
           this.selectedFileList = nextState.selectedFileList

     },

     backgroundOnChange(info){
        const nextState = {};
        switch(info.file.status){
          case "uploading":
          nextState.backgroundFileList = [info.file]
          break;

          case "done":
            nextState.backgroundFile = info.file;
            nextState.backgroundFileList = [info.file];
             this.getBase64(info.file.originFileObj, imageUrl =>
                this.backgroundUrl = imageUrl         
            );      
            break;

          default:
             nextState.backgroundFile = null;
             nextState.backgroundFileList = [];
        }
           this.backgroundFile = nextState.backgroundFile
           this.backgroundFileList = nextState.backgroundFileList

     },

     onSuccess(){
        const userid = this.$cookies.get('userid')
       userService.changeAvantar(this.avatarUrl, userid).then(res=>{
         console.log(res)
           if(res.data == true){
              alert("Avantar successfully changed")
              window.location.reload(false);

           }else{
             alert("Change fail, please try again")

           }
       })

    

    }, 
    backgroundOnSuccess(){
         const userid = this.$cookies.get('userid')
       userService.changeBackground(this.backgroundUrl, userid).then(res=>{
         
           if(res.data == true){
              alert("Background successfully changed")
              window.location.reload(false);

           }else{
             alert("Change fail, please try again")

           }
       })

     },

     dummyRequest ({ file, onSuccess }) {
       console.log(200)
        setTimeout(() => {
          onSuccess("ok");
        }, 0);
      },


      handlePhotoUpload(image){
       if(image.size/1024/1024>5){
         alert("image must smaller than 5MB");
         return false;
         }

      },

      async updateInfo(){
        let username = document.getElementById("username").value
        let firstname = document.getElementById("firstname").value
        let lastname = document.getElementById("lastname").value
        let email = document.getElementById("email").value
        if(username == "" || firstname == "" || lastname == "" || email == ""){
          alert("Your info cannot be empty")
        }else{
          console.log(this.user)
          const updateResult = await userService.updateUserInfo(this.user);
          if(updateResult.data == true){
             alert("Suceesfully Updated")
             window.location.reload(false);

          }else{
            alert("invaild input")

          }
        }
        
      },


       addTag(){
           this.currentTag = false
           const selectedTag = document.getElementById('allTagSelected').value
           
           const selectedTagData = this.allTags.find(item=>{
               return item.tag_id == selectedTag
               })

             this.user.Tag.forEach(item=>{
                 if(item.tag_id == selectedTag){
                    this.currentTag = true
                  }
             })
          
          if(this.user.Tag.length >= 3) {
            console.log(200)
               alert("User could only select 3 tags")

          }else if(this.currentTag == true) {
             console.log(201)
                 alert("This tag is already exist") 
          }else {
              console.log(202)
               this.user.Tag.push(selectedTagData)
               

          }

      }

      
  }
}
</script>

<style lang="less" scoped>
.profile_container{
    /* text-align: center; */
	/* background-color:rgba(169, 169, 169, 0.295); */

 background-image: url('/static/Background-Texture.png');
	width: 100%;
	height: 100%;
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%,-50%);
    display: grid;
    grid-template-columns: 15% 85%;
    grid-template-rows: 10% 90%;
    /* -webkit-box-shadow: #666 0px 0px 10px;
    -moz-box-shadow: #666 0px 0px 10px;
    box-shadow: #666 0px 0px 10px; */
}


.sideBar{
    grid-column: 1;
    grid-row: 2;
    // height: 100%;
    
}
.hpHeader{
   
    height: 100%;
    grid-row: 1;
}

.textBox{
    margin-top: 20px;
    grid-column: 2;
    height: 35px;
    border: 1px solid gray;
    border-radius: 5px;
}

p{
    margin-top: -10px;
    font-size: 20px;
    font-family: PingFangSC-Regular, sans-serif;
}

.mainBody{
  background: white;
  min-height: 75vh;
  height: 98%;
  grid-row: 2;
  grid-column: 2;
  margin-left: 5vw;
  width:92%;
  border-radius: 1rem;
  overflow-y: scroll;
  /* box-shadow: rgb(0, 0, 0/ 55%) 0 0 2.5vw; */
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
}

.profilio_left{

 float: left;
  margin-left: 5vw;
  margin-top: 2vh;
  opacity: 1;
  transition: 1s;
}

.profilio_right{

 float: right;
  margin-right: 5vw;
  margin-top: 3vh;
  opacity: 1;
  transition: 1s;
}

.gender{
  float:left;
  margin-right: 22vw;
  
}


.profilio_left span{ 
   position: relative;
   /* right: 12vw; */
   text-align: left;
}

.profilio_left input{ 
   position: relative;
   border: solid #e8e8e8;
}

.profilio_left select{ 
   position: relative;
   border: solid #e8e8e8
   /* right: 12vw; */
}


.genderRadioSpan{
  right: 2vw;
  margin-right: 3vw;
  font-size: 13px;
  float: left;
  text-align: left;

}


.topicsTab{
   position: relative;
     font-size: 1vw;
   margin-right: 2vw;
   float: left;
   /* margin-right: 1.5vw;
   right: 11vw; */
}

.subTitle{

   margin-right: 1vw;
   float: left;
   text-align: left;
   font-size: 1vw;

}

.oneInput{
   margin-right: 2.5vw;
   float: left;
   border: solid #e8e8e8;
}

.tag_select{
   float: left;
   border: solid #e8e8e8;

}

.profilio_right img{
  height: 25vh;
  width: 15vw;
  // margin-top: 4vh;
}

.avatarSpan{
    margin-right: 14vw;
    font-size: 20px;

}

.profilio_right /deep/ .ant-upload-list{
  height: 0.5vh;
}

/deep/ .ant-btn{
  //  float: left;
  //  position: relative;
  //  left: 2vw;
  //  margin-left: 1vw;
  border-radius: 12px;
  margin-top: 1vh;
}

.profileRight_bottom{
    // float: right;
    // position: relative;
    // right: 4vw;
    background-color: #1677FF;
    color: white;
    border-radius: 12px;
    // margin-right: 4vw;

}

// .profileRight_bottom:hover{
//   border: solid 0.1px #1677FF;
//   color: #1677FF;
//   transition: 0.5s;
// }

.profileLeft_bottom{
    background-color:#1677FF;
    color: white;
    float: left;
    width: 16vw;
    font-size: 20px;
    margin-top: 6vh;
    border-radius: 12px;
}

.addTag_buttom{

    border-radius: 5px;
    float: left;
    background-color: white;
}

.addTag_buttom:hover{
  color:#1677FF;
  border-color: #1677FF;
  transition: 0.5s;
 
}


.changePasswordButton{
    background-color: white;
    float: left;
    border: solid 0.5px rgba(128, 128, 128, 0.452);
    height: 5.5vh;
    width: 16vw;
    margin-left: 1vw;
    font-size: 20px;
    margin-top: 6vh;
    overflow-x:hidden;
    text-align: center;
    border-radius: 12px;
}

.changePasswordButton:hover{
    border: solid 0.5px #1677FF;
    color: #1677FF;
    transition: 0.5s;
}


.cancelChangePassword:hover{
   filter: brightness(5);
}




/*animation */
.slide-fade-enter-active{
    transition: all 1s ease;

}

.slide-fade-enter, .slide-fade-leave-to {
  opacity: 0;
}

.passwordWindow-fade-enter-active{
   transition: all .3s ease;

}

// .passwordWindow-fade-leave-active {
//   transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
// }

.passwordWindow-fade-enter, .passwordWindow-fade-leave-to{
  transform: translateX(10px);
  opacity: 0;
}

/deep/ .ant-menu-submenu:hover{
  border-bottom: 0 !important; 
  color: #1890ff !important;

}



</style>
