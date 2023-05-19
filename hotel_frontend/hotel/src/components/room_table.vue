<template>
  <el-container>
    <el-header>
      <div style="margin-bottom: 10px">
        <i class="el-icon-time" style="color: green"></i>
        <span style="margin-left: 10px;font-size: 16px">实时动态图</span>
      </div>

      <el-form :inline="true" :model="formInline" class="demo-form-inline" size="middle">
        <el-form-item>
          <el-input v-model="formInline.homecode" placeholder="房间号查询" style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="formInline.floor" placeholder="房间楼层" style="width: 150px">
            <el-option label="一楼" value="1"></el-option>
            <el-option label="二楼" value="2"></el-option>
            <el-option label="三楼" value="3"></el-option>
            <el-option label="四楼" value="4"></el-option>
            <el-option label="五楼" value="5"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="formInline.state" placeholder="房间状态" style="width: 150px">
            <el-option label="干净-空房" value="available"></el-option>
            <el-option label="未打扫-空房" value="dirty"></el-option>
            <el-option label="已入住" value="full"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="formInline.type" placeholder="房间类型" style="width: 150px">
            <el-option label="A" value="A"></el-option>
            <el-option label="B" value="B"></el-option>
            <el-option label="C" value="C"></el-option>
            <el-option label="D" value="D"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-refresh" @click="renew">刷新</el-button>
        </el-form-item>
      </el-form>
    </el-header>

    <el-container>
      <el-main>
        <room_single v-for="room in this.room_data" :room_id="room.room_ID"
                     :room_status.sync="room.room_status" :room_type="room.room_type" :item_show.sync="room.item_show">
        </room_single>
      </el-main>
    </el-container>

  </el-container>
</template>

<script>
import room_single from "@/components/room_single";

var room_data=[]
var type=['A','B','B','C','D','D']
for(let i=1;i<=5;i++){
  for(let j=1;j<=6;j++){
    room_data.push({room_ID:type[j-1]+i+0+j,room_status:"available",room_type:type[j-1],item_show:"available"})
  }
}
export default {
  name: "room_table",
  components:{
    room_single
  },
  data(){
    return {
      formInline:{
        homecode:"",
        state:"",
        floor:"",
        type:""
      },
      room_data:room_data
    }
  },
  methods:{
    renew(){
      for(var i=0;i<30;i++){
        room_data[i]['item_show']=room_data[i]['room_status']
      }
    },
    onSubmit(){
      if(this.formInline.homecode==""&&this.formInline.type==""&&this.formInline.state==""&&this.formInline.floor==""){
        this.$message('请至少填写一个查询信息！')
      }
      else{
        var select_data=[]
        // 1.有房间号的情况
        // 房间号和楼层/类型重合
        // 有房间号 类型和楼层中至少有一个不为空
        // 有房间号 类型和楼层都为空
        if(this.formInline.homecode!=""){
          // !! 待修改bug A709 A109无法测得
          // 以及A只有1 B C D的限制
          if(this.formInline.homecode.length!=4||this.formInline.homecode[0]>'D'||this.formInline.homecode[0]<'A'||
              this.formInline.homecode[1]<'1'||this.formInline.homecode[1]>'5'||
              this.formInline.homecode[3]<'1'||this.formInline.homecode[3]>'6'){
            this.$message('请输入正确的房间号！')
          }else{
            // floor type代表当前的房间所在的楼层 类型
            let type=this.formInline.homecode[0]
            let floor=this.formInline.homecode[1]
            if(this.formInline.type=="")
              type=this.formInline.type
            if(this.formInline.floor=="")
              floor=this.formInline.floor
            if(floor!=this.formInline.floor||type!=this.formInline.type){
              this.$message('查询失败')
            }
            else{
              // 房间号和楼层/类型对应
              // 锁定后的房间与房间的类型不符合
              var i
              if(this.formInline.state!=""){
                for(i=0;i<30;i++){
                  if(this.room_data[i]['room_ID']==this.formInline.homecode){
                    if(this.room_data[i]['room_status']!=this.formInline.state){
                      this.$message('查询失败')
                    }
                    else{
                      select_data.push(this.room_data[i])
                    }
                    break
                  }
                }
              }
              else{
                for(i=0;i<30;i++){
                  if(this.room_data[i]['room_ID']==this.formInline.homecode){
                    select_data.push(this.room_data[i])
                    break
                  }
                }
              }
            }
          }
        }

        // 2.没有房间号的情况
        // 锁定后的房间与房间的类型不符合
        if(this.formInline.homecode==""){
          var sel_item=[]
          if(this.formInline.type!=""){
            sel_item.push({type:this.formInline.type})
          }
          if(this.formInline.state!=""){
            sel_item.push(({state:this.formInline.state}))
          }
          if(this.formInline.floor!=""){
            sel_item.push(({floor:this.formInline.floor}))
          }

          for(var j=0;j<30;j++){
            let data=this.room_data[j]
            let flag=1
            var obj={}
            obj['type']=data['room_type']
            obj['floor']=data['room_ID'][1]
            obj['state']=data['room_status']
            for(var i=0;i<sel_item.length;i++){
              if(flag==0){
                break
              }
              for(let item_item in sel_item[i]){
                if(sel_item[i][item_item]!=obj[item_item]){
                  flag=0
                  break
                }
              }
            }
            if(flag==1)
              select_data.push(this.room_data[j])
          }
        }

        // 上色
        if(select_data[0]!=undefined){
          for(var j=0;j<room_data.length;j++){
            room_data[j]['item_show']="false_show"
          }
          for(var j=0;j<select_data.length;j++){
            for(var k=0;k<room_data.length;k++){
              if(room_data[k]['room_ID']==select_data[j]['room_ID']){
                room_data[k]['item_show']=room_data[k]['room_status']
              }
            }
          }
        }
      }
    }
  },
  mounted() {
    this.$axios({
      method:'get',
      url:'http://localhost:9000/room_table',
    }).then(res=>{
      var res_data=res.data
      // 1表示空房 2表示入住 3表示未打扫
      for(var i=0;i<res_data.length;i++){
        for(var j=0;j<30;j++){
          if(res_data[i]['homecode']==room_data[j]['room_ID']){
            var state='available'
            if(res_data[i]['state']=='1'){
              state='available'
            }
            if(res_data[i]['state']=='2'){
              state='full'
            }
            if(res_data[i]['state']=='3'){
              state='dirty'
            }
            room_data[j]['room_status']=state
            break
          }
        }
      }
      console.log(this.room_data)
    })
  }
}
</script>

<style scoped>
  *{
    margin: 0;
    padding: 0;
  }
</style>