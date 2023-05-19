<template>
  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column
        prop="username"
        label="管理员"
        width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.username }}</span>
      </template>
    </el-table-column>
    <el-table-column
        prop="isuser"
        label="权限"
        width="180">
      <template slot-scope="scope">
        <i class="el-icon-check" v-if=scope.row.isuser></i>
        <i class="el-icon-close" v-if=!scope.row.isuser></i>
      </template>
    </el-table-column>
    <el-table-column label="操作"
        prop="id">
      <template slot-scope="scope">
        <el-button
            size="mini"
            @click="handleEdit(scope.row.id,scope.row.isuser)">授予权限</el-button>
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id,scope.row.isuser)">禁用权限</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "superUser",
  data(){
    return{
      document,
      data:[],
      tableData:[]
    }
  },
  methods:{
    handleEdit(id,isuser){
      if(isuser==0){
        this.$alert('是否确认', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
        })
        .then(()=>{
          this.sendMessage(id,isuser)
        })
      }else{
        this.$message({
          type: 'info',
          message: '操作失败'
        })
      }
    },
    handleDelete(id,isuser){
      if(isuser==1){
        this.$alert('是否确认', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
        })
        .then(()=>{
          this.sendMessage(id,isuser)
        })
      }else{
        this.$message({
          type: 'info',
          message: '操作失败'
        })
      }
    },
    sendMessage(id,isuer){
      if(isuer==1){
        var url="updateUserfalse"
      }else{
        var url="updateUsertrue"
      }
      var tar=null
      for(var i=0;i<this.data.length;i++){
        if(this.data[i].id=id){
          tar=this.data[i]
          break
        }
      }
      this.$axios({
        method:'post',
        url:'http://localhost:9000/'+url,
        data:tar
      }).then(res=>{
        if(res.data==true){
          this.$message({
            type: 'success',
            message: '操作成功'
          })
        this.$axios({
          method:"get",
          url:'http://localhost:9000/supperControl'
        }).then(res=>{
          this.data=res.data
          this.tableData=[]
          for(var i=0;i<this.data.length;i++){
            this.tableData.push({username:this.data[i].username,isuser:this.data[i].isuser,id:this.data[i].id})
          }
        })
        }else{
          this.$message({
            type: 'info',
            message: '操作失败'
          })
        }
      })
    }
  },
  created() {
    this.$axios({
      method:"get",
      url:'http://localhost:9000/supperControl'
    }).then(res=>{
      this.data=res.data
      for(var i=0;i<this.data.length;i++){
        this.tableData.push({username:this.data[i].username,isuser:this.data[i].isuser,id:this.data[i].id})
      }
    })
  }
}
</script>

<style scoped>

</style>