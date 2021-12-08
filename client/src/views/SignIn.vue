<template>
    <div class="total_container">
        <div class="left">
            <img id="img" :src="img" alt=""/>
        </div>
        <div class="right">
            <h2>Sign Up</h2>
            <p id="join">Join Us Now</p>
            <div class="signup">
                <p class="title">Firstname:</p>
                <input  type = "text" class="input" placeholder="Please enter your firstname" v-model="firstname">
                <p class="title">Lastname:</p>
                <input  type = "text" class="input" placeholder="Please enter your lastname" v-model="lastname">
                <p class="title">username:</p>
                <input  type = "text" class="input" placeholder="Please enter your nickname" v-model="username">
                <p class="title">email:</p>
                <input type = "text" class="input" placeholder="Please enter your email address" v-model="email"/>
                <p class="title">Password:</p>
                <input class="input" type="text" v-if="pwdType" v-model="password" placeholder="Enter your password"/>
                <input class="input" type="password" placeholder="Enter your password" v-model="password" v-else />
                <p class="title">Confirm Your Password:</p>
                <input class="input" type="text" v-if="pwdType" v-model="cofirmPw" placeholder="Enter your password again"/>
                <input class="input" type="password" placeholder="Enter your password again" v-model="cofirmPw" v-else />
                <img :src="seen ? seenImg : unseenImg" @click="changeType()" v-on:mouseover="hoverEye" v-on:mouseout="outEye" class="icon-eye" />
                <p class="title">Native Language</p>
                <select v-model="native" class="native">
                    <option value="zh">Marderin</option>
                    <option value="cht">Marderin(trandition)</option>
                    <option value="yue">Cantonese</option>
                    <option value="en">English</option>
                    <option value="kor">Korean</option>
                    <option value="jp">Japenese</option>
                    <option value="fra">Franch</option>
                </select>
                <p class="title">Translate To</p>
                <select v-model="translate" class="translate">
                    <option value="zh">Marderin</option>
                    <option value="cht">Marderin(trandition)</option>
                    <option value="yue">Cantonese</option>
                    <option value="en">English</option>
                    <option value="kor">Korean</option>
                    <option value="jp">Japenese</option>
                    <option value="fra">Franch</option>
                </select>
                
            </div>
            <div class="tag">
                <button @click="showModal=true" class="btn" id="selBtn">Select Your Tag</button>
                <div class="mask" v-if="showModal" @click="showModal=false"></div>
                <div class="pop" v-if="showModal">
                    <div id="mask">
                        <div class="alertMask" v-if="showAlert" @click="showAlert=false"></div>
                        <div class="alert" v-if="showAlert">
                            <p>You have selected this tag before.</p>
                            <button @click="showAlert=false" class="btnAlert">Cancel</button>
                        </div>
                    </div>
                    <p id="tagShow">Select your preferred tag</p>
                    <div class="tagList">
                        <p v-for="item in tagList" class="tags" @click="clickTag(item)">{{item.tag_name}}</p>
                    </div>
                    <p id="yourTag">Your Tags</p>
                    <div id="selectList">
                        <div v-for="item, index in ramTag" class="selectContainer" :id=item>
                            <p class="selTags">{{item.tag_name}}</p>
                            <img class="delete" src="../assets/delete-1432400-1211078.png" @click="deleteTag(index,item)"/>
                        </div>
                    </div>
                    <div id="comfirmBtn">
                        <button @click="showModal=false" class="btn">Canel</button>
                        <button @click="comfirm()" class="btn">Comfirm</button>
                    </div>
                </div>
            </div>
            <div class="signupContainer">
                <button class="btn" id="signBtn" type="button" @click="userSignin">Sign in</button>
                <router-link to="/login">Already have account?</router-link>  
            </div>
        </div>     
    </div>
        
</template>

<script>
    import userDataService from "../services/userService"
    import tagService from "../services/tagService"
    import see0 from "../assets/see0.png"
    import see1 from "../assets/see1.png"
    import img1 from "../assets/sign.png"
    import img2 from "../assets/sign2.jpeg"
    import img3 from "../assets/sign3.jpeg"

    export default{
        name: "Signin",
        beforeCreate(){
            const path = require("../assets/test.png")
            document.querySelector('body').setAttribute('style', "background-image: url(" + path + ");background-repeat: no-repeat;background-size: 100%")
        },
        created(){
            this.git()
            this.getAllTag()
        
        },
        data(){
            return{
                username: "",
                password: "",
                cofirmPw:"",
                firstname: "",
                lastname: "",
                email: "",
                data: [],
                seen: "",
                unseenImg: see0, 
                seenImg: see1,   
                pwdType: false,
                imgList: [
                    {icon: img1},
                    {icon: img2},
                    {icon: img3}
                ],
                img: "",
                id: 0,
                showModal: false,
                showAlert: false,
                tagList: "",
                selectedTag: [],
                ramTag: [],
                translate: null,
                native: null
                
            }
        },
        methods: {
          // change the view of eye symbol 
            changeType: function() {
            this.seen = !this.seen;   
            this.pwdType = !this.pwdType;   
            },
            hoverEye: function(event) {
                this.seen = !this.seen;
            },
            outEye: function(event) {
                this.seen = !this.seen;
            },
            clickTag(item){
                if(this.ramTag.indexOf(item) > -1){
                    this.showAlert = true;
                }else{
                    this.ramTag.push(item)
                }
            },
            comfirm(){
                this.showModal = false
                
            },
            async deleteTag(item){              
                let index1 = this.ramTag.indexOf(item)
                this.ramTag.splice(index1,1)
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
                }, 4000)
            },
            async getAllTag(){
                try{
                    this.tagList = await tagService.getAllTags();
                    this.tagList = this.tagList.data;
                }catch(err){
                    console.log(err)
                }
            },

            async userSignin(){
                try{
                    if(this.password == "" || this.cofirmPw ==""){
                        alert("Password cannot be empty")
                    }
                    else if(this.password != this.cofirmPw){
                        alert("Two passwords entered are not same")
                    }else{
                        if (this.username == "" || this.firstname == "" || this.lastname == "" || this.email == "" || this.native == null || this.translate == null){
                            alert("There are some information you haven't enter")
                        }else{
                            for(let i = 0; i < this.ramTag.length; i++){
                    
                    this.selectedTag.push(this.ramTag[i].tag_id)
                    }

                    if(!this.email.includes("@")){
                        alert("You email address is not correct")
                    }else{
                        let value = await userDataService.signInDetail(this.username, 
                                                                       this.$md5(this.password), 
                                                                       this.firstname,
                                                                       this.lastname, 
                                                                       this.email,
                                                                       this.selectedTag,
                                                                       this.native,
                                                                       this.translate)
                        if(value.data[0].check){
                        await userDataService.sendEmail(this.firstname,this.lastname,this.email)
                        this.$router.push({path: "/login"})  //back to the main page    
                    }else{
                        alert(value.data[0].message);
                        }
                    }
                }
                }
                      // insert signup info to the database
                }
                catch(err){
                    console.log(err)
                }
            }
        }
    }
</script>

<style scoped>
.total_container{
    text-align: center;
	background-color:white;
	border-radius: 15px;
	width: 85%;
	height: 85%;
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
    z-index: 3;
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
    text-align: center;
}
.signup{
    display: grid;
    grid-template-columns: 25% 65% 10%;
    grid-template-rows: repeat(6,13%);
    grid-row-gap: 3px;
}
#img{
    width:100%;
    height: 100%;
    border-radius: 15px 0px 0px 15px;
} 
#join{
    margin-top: -15px;
    font-size: 15px;
}

.title{
    grid-column: 1;
    margin-top: 2px;
}

.input{
    grid-column: 2;
    height: 29px;
    border-radius: 5px;
    border: 1.5px solid grey;
}
.icon-eye{
    grid-column: 3;
    grid-row: 5;
    height: 30px;
    width: 30px;
}

#button{
    font-size: 35px;
    color: black;
    background: white;
    position: absolute;
    top: -15px;
}
.tag{
    grid-column: 2;
    text-align: center;
}
.tagList{
    display: flex;
    flex-wrap: wrap;
    grid-column: 1;
    grid-row: 2;
}
#selectList{
    display: flex;
    flex-wrap: wrap;
    grid-column: 1;
    grid-row: 4;
}
.tags{
    font-size: 15px;
    border: 1px solid ;
    width: 13%;
    height: 20%;
    border-radius: 5px;
    padding: 0px 2px 13px 2px;
    margin-left: 10px;
    background-color: #F5DEB3;
}
.selectContainer{
    display: grid;
    grid-template-columns: 70% 30%; 
    grid-template-rows: 100%;
    margin-left: 10px;
    border-radius: 5px;
    width: 15%;
    height: 20%;
    border: 1px solid black;
    padding: 0px 4px 5px 4px;
    margin-top: 10px;
}
.selTags{
    grid-column: 1;
    margin-top: 0px;
}
.delete{
    grid-column: 2;
    width: 100%;
    height: 100%;
    margin-top: 0px;
}
.tags:hover{
    filter:alpha(Opacity=80);
    -moz-opacity:0.8;
    opacity: 0.8;
    background-color:#FFA07A;
}
.mask {  
  background-color: #000;
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 2;
  border-radius: 15px;
}
.alertMask{
  background-color: #000;
  opacity: 0.1;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  border-radius: 15px;
}

#comfirmBtn{
    grid-row: 5;
}

.pop {
  background-color:	#F0FFFF;
  position: fixed;
  top: 15%;
  left: 30%;
  width: calc(100% - 60%);
  height:calc(100% - 30%);
  z-index: 2;
  border-radius: 15px;
  display: grid;
  grid-template-rows: 10% 35% 10% 35% 10%;
  grid-template-columns: 100%;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: rgb(61, 61, 61) 0px 0px 10px;
  box-shadow: rgb(54, 54, 54) 0px 0px 10px;
}

#tagShow{
    grid-column: 1;
    grid-row: 1;
    text-align: left;
    margin-top: 8px;
    margin-left: 5px;
}
#yourTag{
    grid-column: 1;
    grid-row: 3;
    text-align: left;
    margin-top: 8px;
    margin-left: 5px;
    
}
.alert{
  background-color: #fff;
  position: fixed;
  top: 190px;
  left: 455px;
  width: calc(100% - 80%);
  height:calc(100% - 75%);
  z-index: 2;
  border-radius: 15px;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: rgb(61, 61, 61) 0px 0px 10px;
  box-shadow: rgb(54, 54, 54) 0px 0px 10px;
}
.btnAlert{
    width: 150px;
    height: 30px;
}
.btn {
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid black;
  padding: 4px;
}
#selBtn{
    width: 60%;
    padding: 5px;
    font-size: 15px;
    transition: transform .3s ease-in-out, box-shadow .3s cubic-bezier(.47, 0, .745, .715), border .3s linear .1s;
    color: white;
    background-color: #483D8B;
    border-radius: 7px;
    border: 0px solid white;
    margin-top: 30px;
}
#selBtn:hover{
    filter:alpha(Opacity=80);
    -moz-opacity:0.8;
    opacity: 0.8;
    box-shadow: 0 10px 50px rgba(51, 51, 51, .25);
    -webkit-transform: translateY(-10px);
    -moz-transform: translateY(-10px);
    transform: translateY(-10px);
}
.signupContainer{
    display: grid;
    grid-template-rows: 50% 50%;
    grid-template-columns: 100%;
    margin-top: 25px;
    text-align: center;
}
#signBtn{
    width: 60%;
    padding: 7px 0px 15px 0px;
    font-size: 15px;
    margin-left: 20%;
    transition: transform .3s ease-in-out, box-shadow .3s cubic-bezier(.47, 0, .745, .715), border .3s linear .1s;
    color: white;
    background-color: #483D8B;
    border-radius: 7px;
    border: 0px solid white;
}
#signBtn:hover{
    filter:alpha(Opacity=80);
    -moz-opacity:0.8;
    opacity: 0.8;
    box-shadow: 0 10px 50px rgba(51, 51, 51, .25);
    -webkit-transform: translateY(-10px);
    -moz-transform: translateY(-10px);
    transform: translateY(-10px);
}
.native{
    grid-column: 2;
    height: 29px;
    border-radius: 5px;
    border: 1px solid grey;
}
.translate{
    grid-column: 2;
    height: 29px;
    border-radius: 5px;
    border: 1px solid grey;
}



</style>