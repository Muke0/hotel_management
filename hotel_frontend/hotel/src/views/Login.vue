<template>
  <div class="login-register">
    <div class="contain">
      <div class="big-box" :class="{active:isLogin}">
        <div class="big-contain" key="bigContainLogin" v-if="isLogin">
          <div class="btitle">账户登录</div>
          <div class="bform">
            <input type="text" placeholder="用户名" v-model="form.username">
            <span class="errTips" v-if="usernameError">* 用户名错误 *</span>
            <input type="password" placeholder="密码" v-model="form.userpwd" >
            <span class="errTips" v-if="passwordError">* 密码填写错误 *</span>
            <a href="./forget">忘记密码</a>
          </div>
          <button class="bbutton" @click="login">登录</button>

        </div>
        <div class="big-contain" key="bigContainRegister" v-else>
          <div class="btitle">创建账户</div>
          <div class="bform">
            <input type="text" placeholder="用户名" v-model="form.username">
            <span class="errTips" v-if="existed">* 用户名已经存在！ *</span>
            <input type="password" placeholder="密码" v-model="form.userpwd"@blur="pwdError">
            <span class="errTips" v-if="cre_pwdError">* 密码格式填写错误 *</span>
            <input type="email" placeholder="邮箱" v-model="form.useremail" @blur="eamilFormTest">
            <span class="errTips" v-if="emailError">{{eamil_form_error_text}}</span>
            <input type="text" placeholder="验证码" v-model="testCode">
            <span class="errTips" v-if="codeError">* 验证码填写错误 *</span>
          </div>
          <div class="two-button">
            <button :class="{disbtn:disbtnvalue}" class="bbutton" @click="getCode" id="sendCode" :disabled="code_able">{{codeText}}</button>
            <button class="bbutton" @click="register">注册</button>
          </div>
        </div>
      </div>
      <div class="small-box" :class="{active:isLogin}">
        <div class="small-contain" key="smallContainRegister" v-if="isLogin">
          <div class="stitle">你好，朋友!</div>
          <p class="scontent">开始注册，和我们一起旅行</p>
          <button class="sbutton" @click="changeType">注册</button>
        </div>
        <div class="small-contain" key="smallContainLogin" v-else>
          <div class="stitle">欢迎回来!</div>
          <p class="scontent">与我们保持联系，请登录你的账户</p>
          <button class="sbutton" @click="changeType">登录</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import superUser from "@/components/superUser";
import main from "@/views/main";
import customer_table from "@/components/customer_table";
import room_table from "@/components/room_table";
import income_instay from "@/components/income_instay";
import checkin from "@/components/checkin";
import store from "@/store";

export default{
  name:'login-register',
  data(){
    return {
      eamil_form_error_text:"* 邮箱错误 *",
      disbtnvalue:false,
      testCode:"",
      code_able:false,
      codeText:"发送验证码",
      usernameError:false,
      isLogin:true,
      emailError: false,
      passwordError: false,
      existed: false,
      cre_pwdError:false,
      codeError:false,
      form:{
        username:'',
        useremail:'',
        userpwd:''
      },
      timer:null,
      count_time:60
    }
  },
  methods:{
    eamilFormTest(){
      var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
      if(!reg.test(this.form.useremail)){
        this.emailError=true
        this.eamil_form_error_text="* 邮箱错误 *"
      }
      else{
        this.emailError=false
      }
    },
    pwdError(){
      if (this.form.userpwd.length >= 8) {
        this.cre_pwdError= true
      } else {
        this.cre_pwdError= false
      }
    },
    timeSet(){
      if(this.count_time==0){
        clearInterval(this.timer)
        this.count_time=60
        this.timer=null
        this.code_able=false
        this.disbtnvalue=false
        this.codeText="发送验证码"
      }
      else{
        this.codeText=this.count_time
        this.count_time--
      }
    },
    getCode() {
      if (this.form.useremail === "") {
        alert("请先输入邮箱")
      } else {
// 点击后60s内不得再次发送 点击框颜色变化+禁用
        this.disbtnvalue=true
        this.code_able=true
        this.timer=setInterval(this.timeSet,1000)
        let param = new URLSearchParams()
        param.append('email', this.form.useremail)
        axios.post('http://localhost:9000/getcode', param).then(
            res=>{
              document.cookie=res.data
              // console.log(document.cookie)
            }
        )
      }
    },
    changeType() {
      this.isLogin = !this.isLogin
      this.form.username = ''
      this.form.useremail = ''
      this.form.userpwd = ''
      this.disbtnvalue=false
      this.testCode=""
      this.code_able=false
      this.codeText="发送验证码"
      this.usernameError=false
      this.emailError= false
      this.passwordError= false
      this.existed= false
      this.cre_pwdError=false
      this.codeError=false
      this.count_time=60
      clearInterval(this.timer)
      this.timer=null
      document.cookie=""
    },
    login() {
      const self = this;
      if (self.form.username != "" && self.form.userpwd != "") {
        self.$axios({
          method:'post',
          url: 'http://localhost:9000/login',
          data: {
            password: self.form.userpwd,
            username: self.form.username
          }
        })
            .then(res=>{
              if(res.data=="false2"){
                alert('未认证')
              }
              if(res.data=="true1"){
                document.cookie=this.form.username
                window.sessionStorage.setItem('user',"0")
                this.$router.addRoute({
                  path:'/main',
                  name:"main",
                  component: main,
                  children:[
                    {
                      path:"/customer_table",
                      name:"customer_table",
                      component:customer_table
                    },
                    {
                      path:"/room_table",
                      name:"room_table",
                      component:room_table
                    },
                    {
                      path:"/income_instay",
                      name:"income_instay",
                      component:income_instay
                    },
                    {
                      path:"/checkin",
                      name:"checkin",
                      component:checkin
                    }
                  ]
                })
                store.state.user_router=this.$router
                this.$router.push({
                  path:'/main'
                })
              }
              if(res.data=="true2"){
                document.cookie=this.form.username
                window.sessionStorage.setItem('user',"1")
                this.$router.addRoute({
                    path:'/main',
                    name:"main",
                    component: main,
                    children:[
                      {
                        path:"/customer_table",
                        name:"customer_table",
                        component:customer_table
                      },
                      {
                        path:"/room_table",
                        name:"room_table",
                        component:room_table
                      },
                      {
                        path:"/income_instay",
                        name:"income_instay",
                        component:income_instay
                      },
                      {
                        path:"/checkin",
                        name:"checkin",
                        component:checkin
                      },
                      {
                        path:"/superUser",
                        name:"superUser",
                        component:superUser
                      }
                    ]
                })
                store.state.user_router=this.$router
                this.$router.push({
                  path:'/main'
                })
              }
              if(res.data=="false1"){
                this.usernameError=true
                this.passwordError=true
              }
            })
      } else{
        alert("填写不能为空！");
      }
    },
    register(){
      const self = this
      if(this.testCode==document.cookie){
        this.codeError=false
        if(self.form.username != "" && self.form.useremail != "" && self.form.userpwd != ""){
          self.$axios({
            method:'post',
            url: 'http://localhost:9000/register',
            data: {
              username: self.form.username,
              email: self.form.useremail,
              password: self.form.userpwd
            }
          })
              .then( res => {
                if(res.data==true){
                  alert("注册成功")
                  location.reload()
                }
                if(res.data=="false2"){
                  this.existed=true
                }
                if(res.data=="false1"){
                  this.emailError=true
                  this.eamil_form_error_text="* 邮箱已存在 *"
                }

              })
        } else {
          alert("填写不能为空！");
        }
      }
      else{
        this.codeError=true
      }

    }
  }
}
</script>

<style scoped="scoped">
.login-register{
  width: 100vw;
  height: 100vh;
  box-sizing: border-box;
  background-color: #2c3f55;
}
.contain{
  width: 60%;
  height: 60%;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 0 3px #f0f0f0,
  0 0 6px #f0f0f0;
}
.big-box{
  width: 70%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 30%;
  transform: translateX(0%);
  transition: all 1s;
}
.big-contain{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.two-button{
  width: 50%;
  height: 10%;
  display: flex;
  flex-direction: revert;
  justify-content: center;
  align-items: center;
}

.btitle{
  font-size: 1.5em;
  font-weight: bold;
  color: rgb(57,167,176);
}
.bform{
  width: 100%;
  height: 40%;
  padding: 2em 0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.bform .errTips{
  display: block;
  width: 50%;
  text-align: left;
  color: red;
  font-size: 0.7em;
  margin-left: 1em;
}
.bform input{
  width: 50%;
  height: 30px;
  border: none;
  outline: none;
  border-radius: 10px;
  padding-left: 2em;
  background-color: #f0f0f0;
}


.bbutton{
  width: 46%;
  height: 40px;
  border-radius: 24px;
  border: none;
  outline: none;
  background-color: rgb(57,167,176);
  color: #fff;
  font-size: 0.9em;
  cursor: pointer;
  text-align: center;
  display: inline-block;
  margin-left: auto;
  margin-right: auto;
}
.small-box{
  width: 30%;
  height: 100%;
  background: linear-gradient(135deg,rgb(57,167,176),rgb(56,183,145));
  position: absolute;
  top: 0;
  left: 0;
  transform: translateX(0%);
  transition: all 1s;
  border-top-left-radius: inherit;
  border-bottom-left-radius: inherit;
}
.small-contain{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.stitle{
  font-size: 1.5em;
  font-weight: bold;
  color: #fff;
}
.scontent{
  font-size: 0.8em;
  color: #fff;
  text-align: center;
  padding: 2em 4em;
  line-height: 1.7em;
}
.sbutton{
  width: 60%;
  height: 40px;
  border-radius: 24px;
  border: 1px solid #fff;
  outline: none;
  background-color: transparent;
  color: #fff;
  font-size: 0.9em;
  cursor: pointer;
}

.big-box.active{
  left: 0;
  transition: all 0.5s;
}
.small-box.active{
  left: 100%;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  border-top-right-radius: inherit;
  border-bottom-right-radius: inherit;
  transform: translateX(-100%);
  transition: all 1s;
}
.disbtn{
  background-color:gray;
  cursor: not-allowed;
}
</style>
