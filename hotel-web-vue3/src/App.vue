<template>
  <el-menu default-active="1" class="sidebar">
    <div class="title">酒店管理系统</div>
    <el-sub-menu index="1" v-show="userIdentity == 'client'">
      <template #title>
        <el-icon><Menu /></el-icon>
        <span class="menu">客户入口</span>
      </template>
      <el-menu-item index="1-1"  class="menu-item" @click="toTarget('/client/appointment')">预约服务</el-menu-item>
      <el-menu-item index="1-1"  class="menu-item" @click="toTarget('/client/room_service')">客房服务请求</el-menu-item>
      <el-menu-item index="1-1"  class="menu-item" @click="toTarget('/client/evaluate')">服务评价</el-menu-item>
      <el-menu-item index="1-1"  class="menu-item" @click="toTarget('/client/bill')">账单</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="2" v-show="userIdentity == 'manager'">
      <template #title>
        <el-icon><Menu /></el-icon>
        <span class="menu">经理入口</span>
      </template>
      <el-menu-item index="2-1" class="menu-item" @click="toTarget('/staff')">员工管理</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="3" v-show="userIdentity == 'roomservice'">
      <template #title>
        <el-icon><Menu /></el-icon>
        <span class="menu">客房人员入口</span>
      </template>
      <el-menu-item index="3-1" class="menu-item" @click="toTarget('/room_service')">服务需求</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="4" v-show="userIdentity == 'frontdesk'">
      <template #title>
        <el-icon><Menu /></el-icon>
        <span class="menu">前台人员入口</span>
      </template>
      <el-menu-item index="4-1" class="menu-item" @click="toTarget('/front_desk/room')">房间信息</el-menu-item>
      <el-menu-item index="4-2" class="menu-item" @click="toTarget('/front_desk/vip')">会员信息</el-menu-item>
      <el-menu-item index="4-3" class="menu-item" @click="toTarget('/front_desk/appointment')">预约信息</el-menu-item>
      <el-menu-item index="4-4" class="menu-item" @click="toTarget('/front_desk/user')">用户信息</el-menu-item>
    </el-sub-menu>
  </el-menu>
  <div class="container">
    <RouterView></RouterView>
  </div>
</template>

<script>
import { RouterView } from 'vue-router';
import axios from 'axios';
import { ElMessage } from 'element-plus'
export default {
  name: 'App',
  components: {},
  data() {
    return {
      username: "获取中...",
      userIdentity:"未知",
    };
  },
  created() { },
  methods: {
    toTarget(target) {
      this.$router.push(target);
    }
  },
  mounted() { 
    if (localStorage.getItem('token')) {
      // 获取用户信息
      axios.get('/user/info',{
        headers: {
          Authorization: localStorage.getItem('token')
        }
      }).then(res => {
        if (res.data.user_name) {
          this.username = res.data.user_name;
          this.userIdentity = res.data.user_identity;
          console.log(this.userIdentity)
        } else {
          ElMessage({
            message: res.data.message,
            type: 'error',
          })
        }
      })
    }
  },
};
</script>

<style scoped>
.sidebar {
  width: 200px;
  overflow: auto;
}

.title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin: 20px 0;
}

.menu {
  font-weight: bold;
  font-size: 18px;
}

.menu-item {
  font-size: 16px;
}

.container {
  width: 100%;
}


</style>
