!<template>
  <div class="chatroom_container">
    <div class="sideBar">
      <SideBar/>
    </div>

    <div class="hpHeader">

      <Header v-on:currentUser='getCurrentUser'/>

    </div>

    <div class="mainBody">

      <div class="friendList">
        <span style="textAlign: centre; font-Size: 1.3vw;">Your GroupChat</span>
        <br/><br/>


        <div class="friendListItem">
            <div v-for="item in this.chatRoomList" class="groupName">
              <p @click="showChat(item)">{{item.room_name}}</p>
            </div>
        </div>
      </div>

      <!-- chat Section -->
      <div class="chat">

        <div class="chatHead" v-if="this.talk">
          <span class="chatHeadTitle">{{this.chatRoomName}}</span>
          <a-menu
              theme="light"
              mode="horizontal"
              :style="{float: 'right'}"

          >
            <a-sub-menu >
                                    <span style="fontSize: large;" slot="title"
                                    >...</span>

              <a-menu-item key="setting:1" @click="endConversation">
                End Coversation
              </a-menu-item>

            </a-sub-menu>

          </a-menu>
        </div>


        <div class="chatBody" v-show="this.talk">
          <div class="conversation" id="conversationid" >

          </div>

<!-- :style="{backgroundImage:`url(${this.User.background})`}" -->
          <div class="inputWindow">
            <textarea rows="10" maxlength="500" id="conversationText" class="textInput" placeHolder="Type Your message Here"/>
            <!-- :headers="headers"
              @change="handleChange" -->
            <!-- <a-upload
                name="file"
                :multiple="true"
                action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
            > -->
              <a-icon class="photoIcon" type="line-height" @click="translate"/>
            <!-- </a-upload> -->


            <button class="sendButton" @click="this.sendMessage">
              <a-icon type="check-circle" class="sendIcon" theme="filled" two-tone-color="black" /> Send
            </button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import SideBar from "../components/sideBar.vue"
import Header from "../components/header.vue"
import photo from "../assets/image/521633410487_.pic_hd.jpg"
import ApiService from "../services/apiService"
import userService from '../services/userService'
export default {
  data(){
    return{
      talk: false,
      websock: null,
      User: null,
      chatRoomName: null,
      chatRoomId: null,
      chatRoomList: null,
      userID: null,
      srcPho: photo,
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
      toUserInfo:[]
    }
  },
  mounted(){

  },
  beforeDestroy(){
    this.websock.close();
  },
  components:{
    SideBar,
    Header,
    ApiService,
    userService
  },
  methods:{
    showChat(item){
        if(this.websock != null){
          this.websock.close();
          console.log(1)
        }
        this.talk = true;
        this.chatRoomName = item.room_name;
        this.chatRoomId = item.room_id;
        this.initWebSocket();

        const conversation = document.getElementById('conversationid')
        conversation.innerHTML = "";
        
        let id = this.User.user_id + this.chatRoomName
        let chatData = sessionStorage.getItem(id)

        if(chatData != null){
          conversation.innerHTML = chatData
          const chatRoom = document.getElementsByClassName('conversation_left')
          const self = document.getElementsByClassName('conversation_right')

        chatRoom.forEach(item=>{
              let friendImg = document.createElement('img')
              friendImg.setAttribute('alt','')
              friendImg.setAttribute('src', this.srcPho)
              item.appendChild(friendImg)
        })

        self.forEach(selfitem=>{
              let selfImg = document.createElement('img')
              selfImg.setAttribute('alt','')
              selfImg.setAttribute('src', this.User.photo)
              selfitem.appendChild(selfImg)
        })                   
      }
    },

    getCurrentUser(user){
    
      this.User = user.data[0]
      this.userID = this.User.user_id
      this.toLanguage= user.data[0].translate
      this.fromLanguage = user.data[0].native_language
      this.chatRoomList = this.User.GroupRoom
    },
    initWebSocket(){
      const wsurl = "ws://localhost:3001/chat/" + this.chatRoomId + "/" + this.userID
      this.websock = new WebSocket(wsurl);
      this.websock.onmessage = this.websocketOnmessage;
      this.websock.onopen = this.websockoetOnopen;
      this.websock.onerror = this.websockoetOnerror;
      this.websock.onclose = this.websockoetOnclose;
    },

    //   async getUserInfo(userId){
    //      await userService.getUserInfo(userId).then(item=>{
    //        console.log(item.data)
    //        this.toUserInfo = item
           
    //     })
       
    // },

    websocketOnmessage(e){
      const redata = JSON.parse(e.data);
      const conversation = document.querySelector('.conversation')
      let i = Math.random() 
      let message = document.createElement('div')

      var str =` <div class=\"conversation_left\" id=\"${i}\"><span>${redata.msg}</span></div>`
      if(this.User.user_id != redata.username){
        // this.getUserInfo(redata.username)
        // console.log(this.toUserInfo)
      //    axios.get(`http://localhost:3001/get_info/${redata.username}`).then(result=>{
      //     this.toUserInfo = result.data;

      // })
        message.innerHTML= str
        conversation.appendChild(message)
        const receiveMessage = document.getElementById(i) 
        let div = document.createElement("img")
        div.setAttribute('alt','')
        div.setAttribute('src', this.srcPho)
        receiveMessage.appendChild(div)
        this.$forceUpdate();
        
        let id = this.User.user_id + this.chatRoomName;
        let chatData = sessionStorage.getItem(id);
        if(chatData != null){
            str = chatData + str;
        }
          sessionStorage.setItem(id, str)
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
      const conversation = document.querySelector('.conversation')
      
      if(message == "") return alert('Please enter message first')
    
      document.getElementById('conversationText').value = '';
      var json = {"msg": message};

      //show the message in the conversation area.
      let sendMessage = document.createElement("div")
      let i = Math.random()
      var str = `<div class=\"conversation_right\" id=\"${i}\"><span>${message}</span></div>`

      sendMessage.innerHTML= str
      conversation.appendChild(sendMessage)
      const currentMessage = document.getElementById(i) 
      let div = document.createElement("img")
      div.setAttribute('alt','')
      div.setAttribute('src', this.User.photo)
      currentMessage.appendChild(div)
      this.$forceUpdate();
      
      let id = this.User.user_id + this.chatRoomName;
      console.log(id)
      let chatData = sessionStorage.getItem(id)
        if(chatData != null){
          str = chatData + str;
      }
      sessionStorage.setItem(id, str)
      this.websock.send(JSON.stringify(json))
      },
      endConversation(){
          this.talk = false;
          this.websock.close();
      },

            async translate(){
             
             let getAllReceiveMessage = document.getElementsByClassName('conversation_right')
              let getAllSentMessage = document.getElementsByClassName('conversation_left')
         
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

<style lang="less">
.conversation_left{
  width:100%;
  text-align: left;
  margin-top: 2vh;
}

.conversation_left img{
  height: 4.2vh;
  width: 2.7vw;
  margin-left: 2vw;
  float: left;
  display: inline-block;
}

.conversation_left span{
  display: inline-block;
  background: white;
  border: solid 2px #4e4d4d69;
  border-radius: 10px;
  color: black;
  margin-left:1vw;
  font-size: 1.2vw;
  padding: 5px 10px;
  max-width:50%;
  overflow: hidden;
  word-break:break-all;
  text-align: left;

}

.conversation_right{
  width:100%;
  text-align: right;
  margin-top: 2vh;
}

.conversation_right img{
  height: 4.2vh;
  width: 2.7vw;
  margin-right: 2vw;
  float: right;
  display: inline-block;
}

.conversation_right span{
  display: inline-block;
  background: #1677FF;
  //  border: solid 2px #4e4d4d69;
  border-radius: 10px;
  color: white;
  margin-right:1vw;
  font-size: 1.2vw;
  padding: 5px 10px;
  max-width:50%;
  overflow: hidden;
  //  word-break:break-all;
  word-wrap: break-word;
  text-align: left;
}
</style>



<style lang="less" scoped>
.chatroom_container{
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

// .conversation span{
//    max-width:50%;
//    overflow: hidden;
//    word-break: break-word;

// }

.sideBar{
  grid-column: 1;
  grid-row: 2;
  // height: 100%;

}
.hpHeader{

  height: 100%;
  grid-row: 1;
}

.mainBody{
  // background: white;

  min-height: 75vh;
  height: 98%;
  grid-row: 2;
  grid-column: 2;
  margin-left: 5vw;
  width:92%;
  // border-radius: 1rem;
  display: grid;
  grid-template-columns: 20% 80%;
  grid-template-rows: 100%;
  /* box-shadow: rgb(0, 0, 0/ 55%) 0 0 2.5vw; */
  // -webkit-box-shadow: #666 0px 0px 10px;
  // -moz-box-shadow: #666 0px 0px 10px;
  // box-shadow: #666 0px 0px 10px;
}
.groupName{
  border: 1px solid grey;
  margin-bottom: 10px;
}

.friendList{
  background: white;
  height: 100%;
  grid-column: 1;
  width:100%;
  border-radius: 0.3rem;

  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
  overflow-y: scroll;
}

.chat{
  height:100%;
  grid-column: 2;
  width:100%;
  border-radius: 0.3rem;
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 6% 94%;
  margin-left: 0.2vw;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
  margin-left:1vw;
  border-radius: 0.3rem;
  background: white;
}

.chatHead{
  grid-row: 1;
  height: 100%;
  width: 100%;
  background: white;
}

.chatBody{
  grid-row: 2;
  height: 100%;
  width: 100%;
  background: white;
  margin-top: 0.2vh;
}

.friendList img{
  border-radius: 15px;
}


.friendListTitle{
  float: left;
  margin-left:0.4vw;
  font-size: 1vw;
}

.friendListSelf{
  float: left;
  margin-left: 1.6vw;

}

.friendListSelf img{
  height: 4.2vh;
  width: 2.7vw;
  margin-right: 2vw;
}

.friendListItem{
  float: left;
  margin-top: 1.3vh;
  width: 100%;
}

.friendListItem img{
  height: 4.2vh;
  width: 2.7vw;
  margin-right: 2vw;
}

.friendListItem span:hover{
  color: #1677FF;
}

.chatHeadTitle{
  margin-top: 1vh;
  position: fixed;
  font-size: 1.7vw;
}

.classSlot{

}

/deep/ .chatHead .ant-menu-horizontal{
  border-bottom: 0;
}

/deep/ .chatHead .ant-menu-submenu:hover{

  border-bottom: 0 !important;
}

/deep/ .chatHead .ant-menu-submenu{

  border-bottom: 0 !important;
}

/deep/ .chatHead .ant-menu-submenu-selected{

  color: black;
}

.conversation{
  //  border: solid 2px #4e4d4d69;
  width: 100%;
  height: 87%;
  overflow-y: scroll;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

.inputWindow{
  width: 95%;
  height: 7%;
  margin-top: 2vh;
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
  padding:0;
  margin: 0;
}

.photoIcon{
  grid-column: 2;
  font-size: 1.3vw;
  margin-top: 1.2vh;
  width:100%;
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



.status-circle{
  width: 15px;
  height: 15px;
  border-radius: 50%;
  background-color: grey;
  border: 2px solid white;
  position: relative;
  top:0.5vh;
  right: 0.4vw;
}

.self-status-circle{
  width: 15px;
  height: 15px;
  border-radius: 50%;
  // background-color: rgb(16, 216, 16);
  background-color: grey;
  border: 2px solid white;
  position: relative;
  top:0.5vh;
  right: 0.4vw;

}

</style>