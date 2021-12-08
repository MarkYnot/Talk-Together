<template>

    <div class = "total_container">
        <div class="left">
            <img id="img" src="../assets/image/nest_forgot_password_dribbble.gif" alt=""/>
        </div>

        <div class="right">
            <h1>Get Your Password Back</h1>
            <p id="line1">A new password will be sent</p>
            <p id="line2">by email</p>
            <div class="login">
                
                <input class="textBox" type="text" placeholder="Enter your username(email)" v-model="username">

                <div class="route">
                    <router-link to="/login" class="note" id="forget">Log in</router-link> 
                    <router-link to="/signin" class="note" id="sign">Sign up</router-link>
                </div>
                
                <button id="Button" type="button" @click="userLogin">Send Email</button>
                
                <div class="icon">
                    
                </div>
                 
            </div>
           
            
        </div>
        
    </div>
</template>

<script>
    import userService from "../services/userService"
    export default{
        name: "Login",
        beforeCreate(){
            const path = require("../assets/test.png")
            document.querySelector('body').setAttribute('style', "background-image: url(" + path + ");;background-repeat: no-repeat;background-size: 100%")
        },
        data(){
            return{
                username: "",
                data: [], 
                id: 0,
            }
        },
        methods: {
        //request the user data from backend and use mp5 to secure the sensitive user infomation
            async userLogin(){
                if(this.username == ""){
                    alert("Password and username cannot be empty")
                }else{
                    this.data = await userService.forgetPassword(this.username)
                    this.data = this.data.data
                    console.log(this.data)
                try{
                    if(this.data){
                        this.$router.push({path: "/login"})
                    }else{
                        alert("invaild username, please try again")
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
    grid-column: 2;
    height: 160%;
    border: 1px solid gray;
    border-radius: 5px;
}

#Button{
    grid-column: 2;
    transition: transform .3s ease-in-out, box-shadow .3s cubic-bezier(.47, 0, .745, .715), border .3s linear .1s;
    color: white;
    background-color: #483D8B;
    border-radius: 7px;
    height: 120%;
    margin-top: 3%;
    border: 0px solid white;
}
#Button:hover{
    filter:alpha(Opacity=80);
    -moz-opacity:0.8;
    opacity: 0.8;
    box-shadow: 0 10px 50px rgba(51, 51, 51, .25);
    -webkit-transform: translateY(-7px);
    -moz-transform: translateY(-7px);
    transform: translateY(-7px);
}

.route{
    grid-column: 2;
    margin-top: 5%;
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
#line2{
    margin-top: -16px;
}


</style>