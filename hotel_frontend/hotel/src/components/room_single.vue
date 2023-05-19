<template>
  <div class="big_box">
    <el-card shadow="hover" class="normal" :class="[room_status,item_show]" :room_type="room_type" body-style="padding:0;"
             v-on:click.native="moredata">
      {{room_id}}
    </el-card>

<!--    遵循列表内部只能有列表item原则 ul/ol li-->
    <el-dialog title="房间信息" :visible.sync="dialogTableVisible">
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item label="房间号">{{ room_id }}</el-descriptions-item>
        <el-descriptions-item label="房间类型">{{ room_type }}</el-descriptions-item>
        <el-descriptions-item label="房间状态">{{ room_status }}</el-descriptions-item>
        <el-descriptions-item label="操作" v-if="if_unavailable">
          <el-button type="primary" size="small" @click.native.prevent="clean" v-if="if_clean">清房</el-button>
<!--          <el-button type="primary" size="small" @click.native.prevent="leave" v-if="if_full">退房</el-button>-->
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "room_single",
  data(){
    return{
      // if_full:false,
      dialogTableVisible:false,
      if_unavailable:false,
      if_clean:false
    }
  },
  props:['room_type','room_id','room_status','item_show']
  ,
  methods: {
    moredata() {
      this.dialogTableVisible = true
      if(this.room_status=="full"){
        // this.if_full=true
        this.if_unavailable=false
      }
      if(this.room_status=="dirty"){
        this.if_clean=true
        this.if_unavailable=true
      }
      if(this.room_status=="available"){
        this.if_clean=false
        this.if_unavailable=false
      }
    },
    clean(){
      this.$confirm('房间是否确认已打扫干净？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 对接时删除
        // 测试用
        // this.room_status='available'
        // this.item_show='available'
        // this.$emit('update:room_status',this.room_status)
        // this.$emit('update:item_show',this.room_status)
        // 测试用
        this.$axios({
          method:"get",
          url:'http://localhost:9000/clean/'+this.room_id
        }).then(res=>{
          // console.log(typeof res.data)
          if(res.data===1){
            this.room_status='available'
            this.item_show='available'
            this.$emit('update:room_status',this.room_status)
            this.$emit('update:item_show',this.room_status)
            this.$message({
              type: 'success',
              message: '更新成功!'
            })
          }else{
            this.$message({
              type: 'error',
              message: '更新失败!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>
  .big_box{
    box-sizing: border-box;
    width: 170px;
    height: 100px;
    display: inline-block;
    text-align: center;
    padding: 10px;
  }
  .normal{
    width: 150px;
    height: 80px;
    color: white;
    line-height: 80px;
    font-size: 30px;
  }
  .available{
    background-color: #67C23A;
  }
  .full{
    background-color: #F56C6C;
  }
  .dirty{
    background-color: #E6A23C;
  }
  .false_show{
    background-color: #909399;
  }
</style>