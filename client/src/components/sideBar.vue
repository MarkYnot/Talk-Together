<template>
     
        <a-layout-sider class="hpsidebar">
        <!-- :default-open-keys="['sub1']" -->
        <a-menu
          mode="inline"
          :default-selected-keys="['1']"
          :style="{ height: '100%', borderRight: 0, position: 'fixed', width: '17vw', zIndex: 99, borderRadius:'10px', 'box-shadow': '#666 0px 0px 10px '}"
        >
        
        <!-- <div class='sideBar-header'> -->
       
        <a-menu-item disabled :style="{ fontSize: 'larger', fontWeight: 'bold', height: '10vh', alignItem: 'centre'}">
            <router-link to="/">
            <img class="LogoIcon" v-lazy="'/static/logocut.png'" alt="LogoIcon">
            </router-link>
        </a-menu-item>
        <!-- </div> -->
      
        <a-menu-item class="menuItem" key="1">
           <router-link to="/"/>
             <a-icon type="wechat"/>
            <span>Ramdon Chat</span>
        </a-menu-item>

        
        <a-menu-item class="menuItem" key="2">
            <router-link to="/friendChat"/>
            <a-icon type="contacts" />
            <span>Chat with Friends</span>
        </a-menu-item>

        
        <a-menu-item class="menuItem" key="3">
            <router-link to="/chatroom"></router-link>
            <a-icon type="message" />
            <span>Chatroom</span>
        </a-menu-item>

        
      
        <a-menu-item class="menuItem" key="4">
           <router-link to="/profile"/>
            <a-icon type="control" />
            <span>Profile</span>
       </a-menu-item>
     
   </a-menu>
  </a-layout-sider>
        <!-- </a-layout> -->

</template>

<script>
import "../assets/css/sideBar.css";
export default {
  name: "sideBar.vue",
  data(){
    return{
    

    }
  },

  components:{

  },
  created(){
    // testServce.getAllBookData()
    
  },

  method(){


  }

}
</script>

<style lang="less" scoped>
.hpsidebar{
  width:0px !important;
  min-width: 0px !important;
}

.hpsidebar li{
  text-align: center;

}

.menuItem{
    padding:0 !important;
    height: 7% !important;
    width: 100% !important;
    margin-top: 1.78vh !important;

}

/deep/ .ant-menu-item:not(:last-child){
    
   margin-bottom: 8vh;

  
}

/deep/ .ant-menu{
  height: 87.5% !important;
  left: 3vw;
  top: 10.5vh;
  width: 14vw !important;
  border-radius: 2rem !important;
}


/deep/ .ant-menu i{
  float: left;
  margin-top: 1.5vh;
  margin-left:1vw;
  width: 0.5vw;
  height: 0.5vh;

}

/deep/ .ant-menu-item span {
   
    font-size: 1.3vw;
    float: left;
}


</style>
