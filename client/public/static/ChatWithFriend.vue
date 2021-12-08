!<template>
  <div class="chatroom_container">
     <div class="sideBar">
    <SideBar/>
  </div>

   <div class="hpHeader">
       
       <Header>
               <div slot="user">
                <img alt="" v-lazy="'/static/avantar.png'" style="height: 4vh; borderRadius: 50%; marginRight: 1vw"/>
                <span style="fontSize: large">{{this.userName}}</span>
              </div>
       </Header>
  
  </div>

     <div class="mainBody">
        
        <div class="friendList">
                <span style="textAlign: centre; font-Size: large">Your Friends</span>
                <br/><br/>

                <span class="friendListTitle">Yourself (1)</span>
                 <!-- <span class="friendListTitle">(1)</span> -->
                <br/>
                <br/>
                
                <div class="friendListSelf">
                   <img alt="" v-lazy="'/static/avantar.png'"/>
                   <span style="color: #1677FF;">{{this.userName}}</span>
                </div>

                <br/>
                <br/>
                <br/>
                
                <span class="friendListTitle">Others</span><span class="friendListTitle">(1)</span>
  
                <br/>
                <br/>

                 <div class="friendListItem">
                    <img alt="" v-lazy="'/static/avantar.png'"/>
                    <span >ZiHan</span>
                </div>


                  <div class="friendListItem">
                    <img alt="" v-lazy="'/static/avantar.png'"/>
                    <span >Dairu</span>
                </div>


                  <div class="friendListItem">
                    <img alt="" v-lazy="'/static/avantar.png'"/>
                    <span >Pingzhou</span>
                </div>



        </div>

         <div class="chat">

                  <div class="chatHead">
                     <span class="chatHeadTitle">ZiHan</span>
                     <a-menu
                        theme="light"
                        mode="horizontal"
                        :style="{float: 'right'}"
                     
                      >
                            <a-sub-menu >
                                    <span style="fontSize: large;" slot="title" 
                                    >...</span>

                                    <a-menu-item key="setting:1">
                                        End Coversation
                                      </a-menu-item>

                                      <a-menu-item key="setting:2">
                                        Delete Friend
                                      </a-menu-item>
                            </a-sub-menu>
                        
                       </a-menu>
                  </div>
   
        
                    <div class="chatBody">
                        <div class="conversation">
                                <div class="conversation_left">

                                     <img alt="" v-lazy="'../static/google.png'"/>
                                      <span>hd冲冲冲</span>
                                </div>


                              <div class="conversation_right"></div>


                        </div>
                       
                       
                          <div class="inputWindow">
                              <textarea rows="10" maxlength="500" id="conversationText" class="textInput" placeHolder="Type Your message Here"/>
                                  <!-- :headers="headers"
                                    @change="handleChange" -->
                                <a-upload
                                    name="file"
                                    :multiple="true"
                                    action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                                  >
                                  <a-icon class="photoIcon" theme="filled" type="picture" />
                                  </a-upload>
                              
                                
                                <button class="sendButton">
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
  export default {
    data(){
      return{
         userName: "Mark"

      }
    },
    components:{
    SideBar,
    Header,
   }
  }
</script>

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


.friendListTitle{
  float: left;
  margin-left:0.4vw;
  font-size: medium;
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
 margin-left: 1.6vw;
 margin-top: 1.3vh;
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
   font-size: 25px;
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
 }

.textInput{
   border: 0;
   grid-column: 1;
   overflow-y: scroll;
   font-size: 19px;
   outline: none;
   resize: none;
}

.photoIcon{
  grid-column: 2;
  font-size: 30px;
  margin-top: 1.2vh;
}

.sendButton{
 grid-column: 3;
 border: solid 2px #4e4d4d69;
 background: white;
 font-size:medium;
}

.sendIcon{
  font-size: medium;
}

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
   font-size: large;
   padding: 5px 10px;

}







</style>