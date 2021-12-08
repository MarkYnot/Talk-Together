<template>
  <div class="home">
    <!-- <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/> -->

    <div class="sideBar">
    <SideBar/>

    </div>
   
    <div class="hpHeader">   
    <Header v-on:currentUser='getCurrentUser'/>
    </div>

    <div class="body">
       
       <div class="chat-head">
    
          <button class="matchButton" id="startSearchButton" @click="matching" v-if="!chat"><span>Match</span></button>
          <!-- <button class="matchButton" id="nextButton" @click="next"><span>Next</span></button> -->
          <button class="matchButton" id="nextButton" @click="addFriend"><span>Add Friend</span></button>
          <button class="matchButton" id="stopButton" @click="stop" ><span>Stop</span></button>

       </div>

          <div class="chat-body" >
                <div class="ststemInfo" v-if="chat">System: You are already connected to others</div>
         

                <div class="random_conversation" :style="{backgroundImage:`url(${this.User.background})`}" v-if="chat">
          
                </div>
            
          
            <!-- <div class="addFriendList" v-if="chat">
                <span> Have a good chat?</span>
                <button class="addFriend_Button">Add Friend</button>
            </div> -->

             <div class="inputWindow" v-if="chat">
                              <textarea rows="10" maxlength="600" id="conversationText" class="textInput" placeHolder="Type Your message Here"/>
                                  <!-- :headers="headers"
                                    @change="handleChange" -->
                                <!-- <a-upload
                                    name="file"
                                    :multiple="true"
                                    action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                                    
                                  > -->
                                  <a-icon class="photoIcon"  type="line-height" @click="translate"/>
                                  <!-- </a-upload> -->
                              
                                
                                <button class="sendButton" @click="this.sendMessage">
                                <a-icon type="check-circle" class="sendIcon" theme="filled" two-tone-color="black" /> Send
                                </button>
                            
                          </div>
         
      
      </div>
        
    
    </div> 


  </div>

  
</template>

<script>
// @ is an alias to /src
import SideBar from '../components/sideBar.vue'
import Header from "../components/header.vue"
import photo from "../assets/521633410487_.pic_hd.jpg"
import FriendService from '../services/friendService'
import ApiService from "../services/apiService"

export default {
  name: 'Home',
  data(){
      return {
          chat: false,
          websock: null,
          randomList: null,
          User: null,
          userID: null,
          toUser: null,
          photo: photo,
          chatData:'',
         before:{originText:"", toLanguage:""},
         appid:"20211029000986238",
         salt:"1333333333444444444",
         originText:"",
         fromLanguage: "zh",
         toLanguage:"en",
         sign:"",
         userkey:"IAmiRVb9xPwJxeBEo0an",
         translateResult:"",
         friends:[],
         status:true
      }

  },
  components: {
    SideBar,
    Header,
    ApiService
  },
  beforeDestroy(){
    this.websock.close(); 
  },
  methods:{
     matching(){
        this.chat=true;
        this.initWebSocket();
     },
     getCurrentUser(user){
      this.User = user.data[0]
      console.log(this.User)
      this.toLanguage= user.data[0].translate
      this.fromLanguage = user.data[0].native_language
      this.userID = this.User.user_id
    },
    async addFriend(){

       
        if(this.chat == false) alert("Please start a chat first")
        else{
           this.User.Friends.forEach(item=>{
               if(item['user'].user_id !== this.User.user_id) this.friends.push(item['user'])
               else if(item['user1'].user_id !== this.User.user_id) this.friends.push(item['user1'])
            })
              this.friends.forEach(item=>{
                     if(item.user_id == this.toUser){
                         this.status =false
                          return alert('this user is already your friend');
                     } 
                     
              })


           if(this.status != false){ 
             let confirmation = confirm('You sure want to add this user as friend')
                if(confirmation){

                   
                    console.log(this.toUser, this.userID)
                    const result = await FriendService.addFriend(this.userID, this.toUser)
                      console.log(result.data)
                    if(result.data == "200"){
                            alert("Added friend already")
                    }
                    
                }}
         
           
        }
    },

     stop(){
       this.chat=false; 
       this.websock.close(); 
     }, 

     initWebSocket(){
      const wsurl = "ws://localhost:3001/randomChat"
      this.websock = new WebSocket(wsurl);
      this.websock.onmessage = this.websocketOnmessage;
      this.websock.onopen = this.websockoetOnopen;
      this.websock.onerror = this.websockoetOnerror;
      this.websock.onclose = this.websockoetOnclose;
    },
    websocketOnmessage(e){
      const redata = JSON.parse(e.data);
      console.log(1)
      console.log(redata)
           // see if this message is system-brocasted message
           if(redata.systemMsgFlag){
            //system message
            console.log(2)
              this.randomList = redata.message;
              if(this.randomList.length == 1 && this.randomList[0]==this.userID){
                alert("There are no people online right now")
                this.chat=false;
              }else if(this.randomList.length > 1){
                for(let i = 0; i < this.randomList.length; i++){
                  if(this.randomList[i] != this.userID){
                    this.toUser = this.randomList[i]
                  }
                }
              }
              
           }else{
             // not a system message
               const conversation = document.querySelector('.random_conversation')
               let i = Math.random() 
               let message = document.createElement('div')
    
               var str =` <div class=\"randomConversation_left\" id=\"${i}\"><span>${redata.message}</span></div>`

              message.innerHTML= str
              conversation.appendChild(message)
              const receiveMessage = document.getElementById(i) 
              let div = document.createElement("img")
              div.setAttribute('alt','')
              div.setAttribute('src', this.photo)
              receiveMessage.appendChild(div)
              this.$forceUpdate();

           }
    },
    websockoetOnerror(){
      this.initWebSocket();
    },
    websockoetOnopen(){
      console.log("Enter successfully")
      console.log(this.websock)
    },
    websockoetOnclose(e){
      console.log('disconnected',e);
    },
    websocketsend(Data){//数据发送
      this.websock.send(Data);
    },
    sendMessage(){
      const message = document.getElementById('conversationText').value;
      const conversation = document.querySelector('.random_conversation')
      
      if(message == "") return alert('Please enter message first')
    
      document.getElementById('conversationText').value = '';
      var json = {"message": message, "toName": this.toUser};

      //show the message in the conversation area.
      let sendMessage = document.createElement("div")
      let i = Math.random()
      var str = `<div class=\"randomConversation_right\" id=\"${i}\"><span>${message}</span></div>`

      sendMessage.innerHTML= str
      conversation.appendChild(sendMessage)
      const currentMessage = document.getElementById(i) 
      let div = document.createElement("img")
      div.setAttribute('alt','')
      div.setAttribute('src', this.User.photo)
      currentMessage.appendChild(div)
      this.$forceUpdate();
      
      this.websock.send(JSON.stringify(json))
      },
      endConversation(){
          this.talk = false;
          this.websock.close();
      },

          async translate(){
             
             let getAllReceiveMessage = document.getElementsByClassName('randomConversation_right')
              let getAllSentMessage = document.getElementsByClassName('randomConversation_left')
         
               var allReceiveMessage  = []
               var allSentMessage = []
                    if(getAllReceiveMessage){
                          
                        getAllReceiveMessage.forEach(item=>{
                              item.childNodes.forEach(childitem=>{
                                      if(childitem.nodeName =="SPAN"){
                                        allReceiveMessage.push(childitem.innerText)
                                    };
                                });
                            });

                        }
                        if(getAllSentMessage){
                          getAllSentMessage.forEach(sentitem=>{
                              sentitem.childNodes.forEach(child=>{
                                      if(child.nodeName =="SPAN"){
                                        allSentMessage.push(child.innerText)
                                    };
                                });
                            });
                        }


                 var sentLength = allSentMessage.length
                 const receiveLength = allReceiveMessage.length
               
                 if(sentLength != 0){
                    allReceiveMessage.push(...allSentMessage)
              
                 }
                    var textAllBefore = allReceiveMessage.join("@")
                    var textAll = textAllBefore.replace(/[：:\|\\\*^%$#\-]/g, "");
                    var sign = this.$md5(this.appid + textAll + this.salt + this.userkey)
                    var originText = encodeURIComponent(textAll)
                    const  result = await ApiService.baiduTranslate(originText,this.fromLanguage, this.toLanguage, this.appid, this.salt, sign)
               
                        var resultArr = []
                        resultArr = result.split("@")
                        console.log(resultArr)

                    if(sentLength != 0){
                        getAllReceiveMessage.forEach((item,index)=>{
                             if(index <= receiveLength) {
                            item.childNodes.forEach((childitem)=>{
                                    childitem.innerHTML = resultArr[index]
                                });

                             }
                          });

                        getAllSentMessage.forEach((item,index)=>{
                            item.childNodes.forEach((child)=>{
                                    child.innerHTML = resultArr[index + receiveLength]
                                });
                          });
                     
                     }else{
                        getAllReceiveMessage.forEach((item,index)=>{
                            
                            item.childNodes.forEach((childitem)=>{
                                    childitem.innerHTML = resultArr[index]
                                });
                          });

                     }
            
      }



  }
  }

  
</script>
<style>
.random_conversation{
  overflow-y: scroll;
  grid-column: 1;
  grid-row: 2;
   background-size: cover;
   background-position: center;
   background-repeat: no-repeat;
   background-size: 100% 100%;
}

.randomConversation_left{
   width:100%;
   text-align: left;
   margin-top: 2vh;
}

.randomConversation_left img{
  height: 4.2vh;
  width: 2.7vw;
  margin-left: 2vw;
  float: left;
  display: inline-block;
} 

.randomConversation_left span{
   display: inline-block;
   background: white;
   border: solid 2px #4e4d4d69;
   border-radius: 10px;
   color: black;
   margin-left:1vw;
   font-size: 1.1vw;
   padding: 5px 10px;
   max-width:50%;
   overflow: hidden;
   word-break:break-all;
    text-align: left;

}

.randomConversation_right{
   width:100%;
   text-align: right;
   margin-top: 2vh;
}

.randomConversation_right img{
  height: 4.2vh;
  width: 2.7vw;
  margin-right: 2vw;
  float: right;
  display: inline-block;
} 

.randomConversation_right span{
   display: inline-block;
   background: #1677FF;
  /* border: solid 2px #4e4d4d69; */
   border-radius: 10px;
   color: white;
   margin-right:1vw;
   font-size: 1.1vw;
   padding: 5px 10px;
    max-width:50%;
   overflow: hidden;
  /* word-break:break-all; */
   word-wrap: break-word;
    text-align: left;
}
</style>

<style lang="less" scoped>

.home{

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

.body{
min-height: 75vh;
  height: 98%;
  grid-row: 2;
  grid-column: 2;
  margin-left: 5vw;
  width:92%;
  // border-radius: 1rem;
  // border: solid 2px;
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 10% 90%;
}

.chat-head{
   grid-row: 1;
   grid-column: 1;
   width:100%;
   background-color: white;
   border-radius: 0.5rem;
   -webkit-box-shadow: #666 0px 0px 10px;
   -moz-box-shadow: #666 0px 0px 10px;
   box-shadow: #666 0px 0px 10px;

}

.chat-body{
  grid-row: 2;
  grid-column: 1;
  margin-top: 1vw;
  width:100%;
  background-color: white;
  border-radius: 0.5rem;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
  display: grid;
  grid-template-columns: 100%;
  // grid-template-rows: 10% 60% 10% 20%;
  grid-template-rows: 10% 70% 20%;
}

.matchButton{
   margin-top: 2vh;
   float: left;
   margin-right: 2vw;
   position: relative;
   left: 2vw;
   width: 10vw;
   height: 5.5vh;
   text-align: center;
   overflow: hidden;
   border-radius: 0.5rem;
   font-size: 1.9vw;
   border: 0px;
   transition: all 0.3s;
  }

  .matchButton span:hover{
    opacity: 0;
    font-size: 2vw;
  }


#startSearchButton{
  background-color: rgba(10, 163, 10, 0.712);
  color: white;
}

#startSearchButton:hover{
  background-image: url("../assets/match.gif");
   background-size:  100% 100%;
   transform: scale(1.1);
   height: 6vh;
   width:10vw;
  
}

#nextButton{
  background-color:rgb(0, 153, 255);
  color: white;
  font-size: 1.7vw;
}

#nextButton:hover{
    background-image: url("../assets/next.gif");
    background-size:  80% 100%;
    transform: scale(1.1);
    height: 6vh;
    width:10vw;
}



#stopButton{
  background-color: rgba(236, 44, 11, 0.76);
  color: white;

}

#stopButton:hover{
    background-image: url("../assets/bye.gif");
    background-size:  100% 100%;
    transform: scale(1.1);
    height: 6vh;
    width:10vw;

}

.ststemInfo{
  grid-column: 1;
  grid-row: 1;
  font-size: 1.2vw;
  margin-top: 2vh;
  border: solid 2px rgb(204, 203, 203);
  width: fit-content;
  height: fit-content;
  margin-left:35%;
  border-radius: 0.3rem;
  text-align: center;
  line-height: 3.6vh;
}




.addFriendList{
 
 border: solid 2px rgb(204, 203, 203);
 width: fit-content;
 height: 3.5vh;
 text-align: center;
 line-height: 3vh;
 font-size: 1vw;
 border-radius: 0.3rem;
 grid-column: 1;
 grid-row: 3;
 margin-left: 40%;
 margin-top: 2vh;

}

.addFriendList span{
  float:left;
  margin-right: 1vw;
  
}

.addFriend_Button{
  float: right;
  height: 3.5vh;
  width: 6vw;
  border-radius: 0.3rem;
  border: 0px;
  background-color: rgb(0, 153, 255);
  color: white;
  transition: 0.3s;
}

.inputWindow{
  grid-row: 3;
  grid-column: 1;
  width: 95%;
  height: 6.5vh;
  margin-top: 5vh; 
  margin-left: 1.5vw;
  border-radius: 0.5rem;
  border: solid 2px #4e4d4d69;
  display: grid;
  grid-template-columns: 80% 5% 15%;
  grid-template-rows: 100%;
  font-size: 0px;
 }

.textInput{
   border: 0;
   grid-column: 1;
   font-size: 1.5vw;
   outline: none;
   resize: none;
   width:100%;
   padding:0;
   margin: 0;
}

.photoIcon{
  grid-column: 2;
  font-size: 1.3vw;
  margin-top: 1.2vh;
  width:100%;
  // border: solid 2px #4e4d4d69;
}

.sendButton{
 grid-column: 3;
 border: 0px;
 font-size:1.1vw;
 border-radius: 0.5rem;
 float:right;
 width:100%;
}

.sendIcon{
  font-size: 1vw;
}





</style>
