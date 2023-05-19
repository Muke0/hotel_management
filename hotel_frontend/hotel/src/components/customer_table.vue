<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item>
      <el-input v-model="formInline.code" placeholder="顾客证件号"></el-input>
    </el-form-item>
    <el-form-item>
      <div class="block">
        <el-date-picker
            v-model="intime"
            value-format="yyyyMMdd"
            type="date"
            placeholder="开始日期">
        </el-date-picker>
      </div>
    </el-form-item>
    <el-form-item>
      <div class="block">
        <el-date-picker
            v-model="outtime"
            value-format="yyyyMMdd"
            type="date"
            placeholder="结束日期">
        </el-date-picker>
      </div>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
      <el-button type="primary" @click="add">增加</el-button>
    </el-form-item>
    <!--        修改1：分页方式 请求分页-->
    <el-table
        :data="tableData"
        style="width: 100%;" >
      <el-table-column
          prop="id"
          width="100"
          label="序号"
          align="center">
        <!--        修改2-->
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          align="center"
          width="150">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="性别"
          align="center"
          width="100">
      </el-table-column>
      <el-table-column
          prop="identity"
          label="身份信息"
          align="center"
          width="150">
      </el-table-column>
      <el-table-column
          prop="type"
          label="房间类型" align="center" width="100">
        <template slot-scope="scope">
          {{ totalAmount(scope.$index,tableData) }}
        </template>
      </el-table-column>
      <el-table-column
          prop="homecode"
          label="房间编号"
          align="center"
          width="180">
      </el-table-column>
      <el-table-column
          prop="outtime"
          label="退房时间"
          align="center"
          width="120">
      </el-table-column>

      <el-table-column
          label="操作"
          align="center"
          width="200">
        <template slot-scope="scope">
          <el-button @click.native.prevent="seeRow(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click.native.prevent="editRow(scope.$index, tableData)" type="text" size="small">编辑</el-button>
          <el-button @click.native.prevent="deleteRow(scope.$index, tableData)" type="text" size="small">退房</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block" style="margin-top:15px;text-align: center">
      <el-pagination  @current-change="handleCurrentChange"
                      :current-page="currentPage"
                      :page-size="10"
                      layout="total, prev, pager, next, jumper"
                      :total="total">
      </el-pagination>
    </div>


    <!--查看信息弹窗-->
    <el-dialog title="详细信息" :visible.sync="seeVisible" width="60%">
      <el-descriptions title="用户信息" border>
        <el-descriptions-item label="姓名">{{diaDate.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{diaDate.sex }}</el-descriptions-item>
        <el-descriptions-item label="房间编号">{{diaDate.homecode}}</el-descriptions-item>
        <el-descriptions-item label="入住时间">{{diaDate.intime }}</el-descriptions-item>
        <el-descriptions-item label="房间价格">{{diaDate.yuan}}</el-descriptions-item>
        <el-descriptions-item label="身份信息">{{diaDate.identity}}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>

    <!--增加信息弹窗-->
    <el-dialog title="顾客信息填写" :visible.sync="addVisible">
      <el-form :model="form_add">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form_add.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-input v-model="form_add.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房间编号" :label-width="formLabelWidth">
          <el-input v-model="form_add.homecode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="入住时间" :label-width="formLabelWidth">
          <el-date-picker
              v-model="form_add.intime"
              value-format="yyyyMMdd"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="身份信息" :label-width="formLabelWidth">
          <el-input v-model="form_add.identity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房间价格" :label-width="formLabelWidth">
          <el-input v-model="form_add.yuan" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel_add">取消</el-button>
        <el-button type="primary" @click="submit_add">提交</el-button>
      </div>
    </el-dialog>

    <!--编辑信息弹窗-->
    <el-dialog title="顾客信息填写" :visible.sync="editVisible" :show-close="false">
      <el-form :model="form_edit">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form_edit.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-input v-model="form_edit.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房间编号" :label-width="formLabelWidth">
          <el-input v-model="form_edit.homecode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="入住时间" :label-width="formLabelWidth">
          <el-date-picker
              v-model="form_edit.intime"
              value-format="yyyyMMdd"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="身份信息" :label-width="formLabelWidth">
          <el-input v-model="form_edit.identity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房间价格" :label-width="formLabelWidth">
          <el-input v-model="form_edit.yuan" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel_edit">取消</el-button>
        <el-button type="primary" @click="submit_edit">提交</el-button>
      </div>
    </el-dialog>
  </el-form>
</template>
<script>
export default {
  name: "customer_table",
  data() {
    return {
      formInline: {
        code: '',
      },
      ID:'',
      day:'',
      intime: '',
      outtime: '',
      currentId:'',
      editVisible:false,
      seeVisible:false,
      addVisible:false,
      currentPage: 1, // 当前页码
      total: null, // 总条数
      // ！！ 修改 查询后的总数据
      total_table_data:[],
      tableData: [
        {
          id:2,
          name:1,
          sex:'女',
          homecode:'1234',
          intime:'2022-09-17',
          identity:'12345',
          yuan:100,
          outtime:'2022-09-17',
          type:'A'
        },
        //   {
        //     name:1,
        //     sex:'女',
        //     homecode:'1234',
        //     intime:'2022-09-17',
        //     identity:'12345',
        //     yuan:100,
        //     outtime:'2022-09-17',
        //   },
      ],
      diaDate: {
        name:'',
        sex:'',
        homecode:'',
        intime:'',
        identity:'',
        yuan:'',
      },
      form_add: {
        name:'',
        sex:'',
        homecode:'',
        intime:'',
        identity:'',
        yuan:'',
      },
      form_edit: {
        name:'',
        sex:'',
        homecode:'',
        intime:'',
        identity:'',
        yuan:'',
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    //查询
    onSubmit(){
      console.log(this.intime)
      // ！！修改
      this.currentPage=1
      // ！！修改
      const self=this
      self.$axios({
        method:'get',
        url:'http://localhost:9000/searchdata/'+this.formInline.code+'+'+this.intime+'+'+this.outtime,
      })
          .then(res=>{
            console.log(res)
            var data=res.data
            this.total_table_data=data.dataList
            this.total=data.a
            this.tableData=this.total_table_data.slice(0,10)
          })
    },
    //计算房间类型
    totalAmount(i){
      if(this.tableData[i].yuan==='100'){
        return 'A'
      }
      if(this.tableData[i].yuan==='150'){
        return 'B'
      }
      if(this.tableData[i].yuan==='200'){
        return 'C'
      }
      if(this.tableData[i].yuan==='250'){
        return 'D'
      }
    },


    deleteRow(index, rows) {
      // console.log(this.tableData[index].id)
      this.$alert('请确定是否退房', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(()=> {
        let date = new Date();
        let dateYear = date.getFullYear();             //获取年
        let dateMonth = date.getMonth()+1;               //获取月
        let dateDate = date.getDate();                 //获取当日
        this.day=dateYear.toString()+dateMonth.toString()+dateDate.toString()
        console.log(this.day)
        this.$axios({
          url:'http://localhost:9000/deletdata/',
          method:"post",
          data:{
            id:this.tableData[index].id,
            outtime:this.day
          }
        })
            .then(res=>{
                  if(res.data===true){
                    rows.splice(index, 1);
                    this.$message({
                      type: 'success',
                      message: '退房成功'
                    });
                  }
                }
            ).catch(()=>{
          this.$message({
            type: 'info',
            message: '退房失败'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消退房'
        });
      });
    },

    //查看详情
    seeRow(row) {
      this.seeVisible=true
      this.diaDate = JSON.parse(JSON.stringify(row))
    },
    //编辑信息
    editRow(index,tabeldata){
      //改
      this.currentId=tabeldata[index].id
      this.ID=index
      this.editVisible=true
      this.form_edit=JSON.parse(JSON.stringify(tabeldata[index]))
      this.tableData.splice(index,1,this.form_edit)
    },

    // 增加信息
    add(){
      this.addVisible=true
    },


    //表单自动编号
    indexMethod(index) {
      return index + 1;
    },

    //当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
      // 1.载入界面的分页
      if(this.total>10&&this.total_table_data.length==10){
        this.currentPage = val;
        this.$axios({
          method:'get',
          url:'http://localhost:9000/getdata/'+this.currentPage,
        }).then(res=>{
          var data=res.data
          this.tableData=data.dataList
          this.total_table_data=data.dataList
        })
      }
      // 2.查询页面的分页
      if(this.total>10&&this.total_table_data.length>10){
        this.currentPage=val
        var btm=(this.currentPage-1)*10
        var top=(this.currentPage-1)*10+10
        this.tableData=this.total_table_data.slice(btm,top)
      }
    },

    // 提交
    submit_add(){
      // let id=this.tableData[this.total-(this.currentPage-1)*10].id
      //     console.log(id)
      //改
      axios.post('http://localhost:9000/adddata',{
        name:this.form_add.name,
        sex:this.form_add.sex,
        homecode:this.form_add.homecode,
        intime:this.form_add.intime,
        identity:this.form_add.identity,
        yuan:this.form_add.yuan,
      }).then(res=>{
        if(res.data===true){
          this.tableData.push({id:'*',name:this.form_add.name,sex:this.form_add.sex,homecode: this.form_add.homecode,intime:this.form_add.intime,identity:this.form_add.identity,yuan:this.form_add.yuan})
          alert('添加成功')
          this.form_add.name=''
          this.form_add.sex=''
          this.form_add.homecode=''
          this.form_add.intime=''
          this.form_add.identity=''
          this.form_add.yuan=''
          this.addVisible=false
          this.onSubmit()
        }
      })
    },
    submit_edit(){
      // console.log(this.currentId)
      //改
      console.log(this.form_edit.intime)
      axios.put('http://localhost:9000/updatedata',{
        id:this.currentId,
        name:this.form_edit.name,
        sex:this.form_edit.sex,
        homecode:this.form_edit.homecode,
        intime:this.form_edit.intime,
        identity:this.form_edit.identity,
        yuan:this.form_edit.yuan,
      }).then(res=>{
        if(res.data===true){
          alert('编辑成功')
          console.log(this.ID)
          this.tableData.splice(this.ID,1,this.form_edit)

          this.editVisible=false
        }
      })
    },

    cancel_add(){
      this.addVisible=false
      this.form.name=''
      this.form.sex=''
      this.form.homecode=''
      this.form.intime=''
      this.form.identity=''
      this.form.yuan=''
    },
    cancel_edit(){
      this.editVisible=false
    },
  },
  created() {
    const self=this
    self.$axios({
      method:'get',
      url:'http://localhost:9000/getdata/'+this.currentPage,
    }).then(res=>{
      var data=res.data
      this.total=data.a
      this.tableData=data.dataList
      this.total_table_data=data.dataList
    })
  }


}
</script>

<style scoped>


</style>