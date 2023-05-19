<template>
  <el-container class="main_body">
      <el-aside width="300px" style="height: 100%">
        <el-menu default-active="2"  class="el-menu-vertical-demo" router
                 background-color="#304156" text-color="#acb9c8" active-text-color="#0c9eff" style="height: 100%">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>住客管理</span>
            </template>
            <el-menu-item index="/customer_table">出入信息管理</el-menu-item>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>客房管理</span>
            </template>
            <el-menu-item index="/room_table">客房信息管理</el-menu-item>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-pie-chart"></i>
              <span>统计信息</span>
            </template>
            <el-menu-item index="/checkin">当日入住状况</el-menu-item>
            <el-menu-item index="/income_instay">近期收益/入住状况</el-menu-item>
          </el-submenu>

          <el-submenu index="4" v-if="limit">
            <template slot="title">
              <i class="el-icon-suitcase"></i>
              <span>权限管理</span>
            </template>
            <el-menu-item index="/superUser">权限管理</el-menu-item>
          </el-submenu>

        </el-menu>
      </el-aside>
    <el-container>
      <el-header style="height: 50px;background-color:#eff2f7;line-height: 50px;padding: 0 10px;font-size: 15px;color:rgba(0,0,0,.8);">
        <el-container>
          <el-container style="justify-content: right;font-size: 15px">
            <el-dropdown>
              <div>
                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
              </div>
              <el-dropdown-menu slot="dropdown" style=" top: 2px !important;">
                <el-dropdown-item @click.native="returnLogin" disabled style="color: #2c3f55">
                  你好
                  <span>
              {{document.cookie}}
            </span>
                </el-dropdown-item>
                <el-dropdown-item @click.native="returnLogin">
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-container>
        </el-container>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<style>

.el-dropdown-menu {
  top: 40px !important;
}
*{
  padding: 0;
  margin: 0;
}
.main_body{
  height: 100%;
}
</style>
<script>

export default {
  name: '',
  data(){
    return{
      limit:true,
      document
    }

  },
  methods:{
    returnLogin(){
      window.sessionStorage.removeItem('user')
      this.$router.push({
        path:'/login'
      })
    }
  },
  components: {

  }
  ,created() {
    if(window.sessionStorage.getItem('user')=="0"){
      this.limit=false
    }
  }
}
</script>
