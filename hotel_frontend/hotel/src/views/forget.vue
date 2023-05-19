<template>
  <div class="login-register">
    <div class="contain">
      <div class="big-box" :class="{active:isLogin}">
        <div class="big-contain" key="bigContainRegister">
          <div class="btitle">忘记密码</div>
          <div class="bform">
            <input type="text" placeholder="邮箱" v-model="form.useremail"@blur="emailError">
            <span class="errTips" v-if="for_emailError">* 邮箱填写错误 *</span>
            <input type="password" placeholder="新密码" v-model="form.userpwd"@blur="pwdError">
            <span class="errTips" v-if="for_pwdError">* 密码填写错误 *</span>
            <input type="password" placeholder="验证码" v-model="form.code">
          </div>
          <div class="two-button">
            <button :class="{disbtn:btn_able}" class="bbutton" @click="getcode" id="sendCode" :disabled="code_able">{{codeText}}</button>
            <button class="bbutton" @click="reset">重置密码</button>
          </div>
        </div>
      </div>
      <div class="small-box" :class="{active:isLogin}">
        <div class="small-contain" key="smallContainLogin">
          <div class="stitle">重置密码</div>
          <p class="scontent">请输入你绑定的邮箱密码，我们会发送验证码到你的邮箱，通过验证后可以重置密码</p>
          <button class="sbutton" @click="returnLogin">返回登录</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default{
  name:'login-register',
  data(){
    return {
      btn_able:false,
      codeText:"发送验证码",
      isLogin:false,
      for_emailError: false,
      for_pwdError: false,
      existed: false,
      count: '',
      code_able:false,
      timer: null,
      show:true,
      count_time:60,
      form:{
        useremail:'',
        userpwd:'',
        code:'',
      }
    }
  },

  methods:{
    timeSet(){
      if(this.count_time<=0){
        clearInterval(this.timer)
        this.count_time=60
        this.timer=null
        this.code_able=false
        this.btn_able=false
        this.codeText="发送验证码"
      }
      else{
        this.codeText=this.count_time
        this.count_time--
      }
    },
    //获取验证码
    getcode(){
      if(this.form.useremail==""){
        alert("请先输入邮箱")
      }else{
        this.btn_able=true
        this.code_able=true
        this.timer=setInterval(this.timeSet,1000)
        if(this.for_emailError===false){
          let param = new URLSearchParams()
          param.append('email', this.form.useremail)
          axios.post('http://localhost:9000/getcode', param,
          ).then(
              res=>{
                document.cookie=res.data
              }
          )
        }
      }

    },
    //判断密码格式是否正确
    pwdError(){
      if (this.form.userpwd.length >= 8) {
        this.for_pwdError= true
      } else {
        this.for_pwdError= false
      }
    },
    // 判断邮箱格式是否正确
    emailError(){
      let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (!reg.test(this.form.useremail)){
        this.for_emailError=true
      } else {
        this.for_emailError=false
      }
    },
    //返回登录界面
    returnLogin(){
      this.$router.replace("/login")
    },
    //重置密码（相当于注册）
    reset(){
      if(this.form.code!==document.cookie){
        alert("验证码错误")
      }
      if(this.form.code===document.cookie&&this.for_emailError===false&&this.for_pwdError===false){
        this.$axios({
          method:'post',
          //地址
          url:'http://localhost:9000/forgetfind',
          data:{
            //  往后端传的参数
            email:this.form.useremail,
            password: this.form.userpwd
          }
        }).then(res=>{
          if(res.data===true){
            alert("注册成功")
          }else {
            alert("注册失败")
          }
        })
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


.two-button{
  width: 50%;
  height: 10%;
  display: flex;
  flex-direction: revert;
  justify-content: center;
  align-items: center;
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
