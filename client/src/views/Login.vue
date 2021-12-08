<template>

    <div class = "total_container">
        <div class="left">
            <img id="img" :src="img" alt=""/>
        </div>

        <div class="right">
            <h1>Welcome Back :)</h1>
            <p>LogIn To Chat Now</p>
            <div class="login">
                
                <input class="textBox" type="text" placeholder="Enter your username(email)" v-model="username">
                <input class="textBox" type="text" v-if="pwdType" v-model="password" placeholder="Enter your password"/>
                <input class="textBox" type="password" placeholder="Enter your password" v-model="password" v-else />

                <div class="route">
                    <router-link to="/forget" class="note" id="forget">Forget Password</router-link> 
                    <router-link to="/signin" class="note" id="sign">Sign Up</router-link>
                </div>
                
                <button id="Button" type="button" @click="userLogin">Log in</button>
                
                <div class="icon">
                    
                </div>
                <div class="eye">
                    <img :src="seen ? seenImg : unseenImg" @click="changeType()" v-on:mouseover="hoverEye" v-on:mouseout="outEye" class="icon-eye" />
                </div>
                 
            </div>
           
            
        </div>
        
    </div>
</template>

<script>
    import loginService from "../services/loginService"
    import img1 from "../assets/521633410487_.pic_hd.jpg"
    import img2 from "../assets/531633410598_.pic_hd.jpg"
    import img3 from "../assets/541633410671_.pic_hd.jpg"
    import see0 from "../assets/see0.png"
    import see1 from "../assets/see1.png"

    export default{
        name: "Login",
        beforeCreate(){
            const path = require("../assets/test.png")
            document.querySelector('body').setAttribute('style', "background-image: url(" + path + ");;background-repeat: no-repeat;background-size: 100%")
        },
        data(){
            return{
                username: "",
                password: "",
                data: [],
                seen: "",
                unseenImg: see0,  
                seenImg: see1,   
                pwdType: false,
                index: 0,
                imgList: [
                    {icon: img1},
                    {icon: img2},
                    {icon: img3}
                ],
                img: "",
                id: 0,
                

            }
        },
        created(){
            this.git()
        },
        methods: {
            changeType: function() {
            this.seen = !this.seen;   //change the view of eye symbol 
            this.pwdType = !this.pwdType;   
            },
            hoverEye: function(event) {
                this.seen = !this.seen;
            },
            outEye: function(event) {
                this.seen = !this.seen;
            },
            git(){
                this.img = this.imgList[this.id].icon

                setInterval(() => {
                    if (this.id === this.imgList.length - 1){
                        this.id = 0
                        this.img = this.imgList[this.id].icon
                    }else{
                        this.id += 1
                        this.img = this.imgList[this.id].icon
                    }
                }, 5000)
            },

      //每3秒重新运行函数qiehuan()
        //request the user data from backend and use mp5 to secure the sensitive user infomation
            async userLogin(){
                if(this.password == "" || this.username == ""){
                    alert("Password and username cannot be empty")
                }else{
                    let ps = this.$md5(this.password)
                    this.data = await loginService.getLoginDetail(this.username, ps)
                    this.data = this.data.data
                try{
                    if(this.data !== ""){
                        let firstname = this.data.firstname
                        let lastname = this.data.lastname
                        let username = this.data.email
                        let userid = this.data.user_id
                        this.$cookies.set("firstname", firstname, "360min"); //asve info into cookie with base64 encode to enhance the security. 
                        this.$cookies.set("lastname",lastname,"360min");
                        this.$cookies.set("state", true,"360min");
                        this.$cookies.set("username", username,"360min");
                        this.$cookies.set("userid", userid, "360min");
                        this.$router.push({path: "/"})
                    }else{
                        alert("invaild username or password, please try again")
                    }
                }catch(err){
                    console.log(err)
                    }
                }
                
            },


            
        },
    }
</script>

<style scoped>
.total_container{
    text-align: center;
	background-color:white;
	border-radius: 15px;
	width: 80%;
	height: 70%;
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%,-50%);
    display: grid;
    grid-template-columns: 60% 40%;
    grid-template-rows: 100%;
    -webkit-box-shadow: #666 0px 0px 10px;
    -moz-box-shadow: rgb(61, 61, 61) 0px 0px 10px;
    box-shadow: rgb(54, 54, 54) 0px 0px 10px;
}
.left{
    grid-column: 1;
    grid-row: 1;
    height: 100%;
}
.right{
    grid-column: 2;
    height: 100%;
    grid-row: 1;
}
#img{
    width:100%;
    height: 100%;
    border-radius: 15px 0px 0px 15px;
}
.login{
    display: grid;
    margin-top:50px;
    grid-template-columns: 20% 60% 20%;
    grid-template-rows: auto;
}
.textBox{
    margin-top: 20px;
    grid-column: 2;
    height: 35px;
    border: 1px solid gray;
    border-radius: 5px;
}

#Button{
    grid-column: 2;
    width: 35%;
    height: 35px;
    margin-left: 65%;
    margin-top: 10px;
    color: white;
    background-color: #483D8B;
    border-radius: 7px;
}
.route{
    grid-column: 2;
    margin-bottom: 10px;
}
.note{
    font-size: 8px;
    margin-top: 2px;
}
#forget{
    float: left;
}
#sign{
    float: right;
}
.icon{
    grid-row: 5;
    grid-column: 2;
    margin-top: 10px;
}
.iconImg{
    margin-left: 9px;
}
p{
    margin-top: -10px;
    font-size: 20px;
    font-family: PingFangSC-Regular, sans-serif;
}
.eye{
    grid-column: 3;
    grid-row: 2;
}

.icon-eye{
    height: 30px;
    width: 30px;
    position: relative;
    top: 25px;
    left: -15px;
}

</style>