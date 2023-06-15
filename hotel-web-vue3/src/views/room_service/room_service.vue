<template>
  <headbar></headbar>
  <div class="card">
    <div class="title">
      需求列表
    </div>
    <el-dialog v-model="modifyDialogFormVisible" title="处理需求">
      <el-form :model="modifyform">
          <el-form-item label="金额" :label-width="formLabelWidth">
            <el-input v-model="modifyform.Dmoney" autocomplete="off" />
          </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="modifyDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="onmodify"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="Dno" label="需求ID" width="180" />
      <el-table-column prop="Dinitiator" label="需求发起人" />
      <el-table-column prop="Dcontent" label="处理内容" />
      <el-table-column prop="Ddescription" label="内容描述" />

      <el-table-column fixed="right" label="操作" width="160">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="modifyClick(scope.$index, scope.row)"
            >处理</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center">
      <el-pagination
        layout="prev, pager, next"
        :total="10"
        style="margin: 0 auto; width: 200px"
      />
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "review",
  data() {
    return {
      form: {
        user: "",
      },
      modifyform: {
        Dno:"",
        Dmoney: "",
      },
      data: {
        Dno: "N/A",
        Dperformer: "N/A",
        Dinitiator: "N/A",
        Dcontent: "N/A",
        Dtime: "N/A",
        Ddescription: "N/A",
      },
      tableData: [],
      dialogFormVisible: false,
      modifyDialogFormVisible: false,
      formLabelWidth: "140px",
    };
  },
  mounted() {
    if (localStorage.getItem('token')) {
            // 获取用户信息
            axios.get('/user/info', {
                headers: {
                    Authorization: localStorage.getItem('token')
                }
            }).then(res => {
                if (res.data.user_name) {
                    this.username = res.data.user_name;
                } else {
                    ElMessage({
                        message: res.data.message,
                        type: 'error',
                    })
                    this.$router.push('/login');
                }
            }).catch(err => {
                ElMessage({
                    message: "错误",
                    type: 'error',
                })
                this.$router.push('/login');
            })
        }
    var config = {
      method: "get",
      url: "http://127.0.0.1:9000/room_Service/service_need",
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };
    const _this = this;
    axios(config)
      .then(function (response) {
        //console.log(response.data);
        response.data.forEach((data) => {
          _this.tableData.push(data);
        });
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    onQuery() {
      this.tableData.forEach((e) => {
        if (e.Sid == this.form.user) {
          this.data = e;
        }
      });
    },
    onmodify() {
      this.modifyDialogFormVisible = false;
      var data = {
        Dperformer:this.username,
        Dmoney:this.modifyform.Dmoney,
        Dno:this.modifyform.Dno,
      };
      console.log(data)

      var config = {
        method: "put",
        url: "http://127.0.0.1:9000/room_Service/service_need",
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        data: data,
      };
      const _this = this;
      axios(config)
        .then(function (response) {
          console.log(response.data);
          var config = {
            method: "get",
            url: "http://127.0.0.1:9000/room_Service/service_need",
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          };

          axios(config)
            .then(function (response) {
              console.log(response.data);
              _this.tableData = [];
              response.data.forEach((data) => {
                _this.tableData.push(data);
              });
            })
            .catch(function (error) {
              console.log(error);
            });
        })
        .catch(function (error) {
          console.log(error);
        });
      var clearform = {
        sId: "",
        sName: "",
        sPosition: "",
        sType: "",
      };
      this.modifyform = clearform;
    },
    modifyClick(index, row) {
      this.modifyform.Dno = row.Dno;
      this.modifyDialogFormVisible = true;
    },
    positionholder(type) {
      return type == 1 ? this.modifyform.sPosition : this.addform.sPosition;
    },
    typeholder(type) {
      return type == 1 ? this.modifyform.sType : this.addform.sType;
    },
  },
};
</script>
