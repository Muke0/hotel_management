<template>
  <headbar></headbar>
  <div name="搜索" class="card">
    <el-form :inline="true" :model="form">
      <el-form-item label="用户ID">
        <el-input v-model="form.user" placeholder="ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">Query</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col :span="6"><el-statistic title="用户身份证" :value="data.Uno" /></el-col>
      <el-col :span="6"><el-statistic title="会员编号" :value="data.VIPno" /></el-col>
      <el-col :span="6"><el-statistic title="用户姓名" :value="data.Uname" /></el-col>
      <el-col :span="6"><el-statistic title="用户性别" :value="data.Usex" /></el-col>
      <el-col :span="6"><el-statistic title="用户邮箱" :value="data.Umail" /></el-col>
    </el-row>
  </div>
  <div class="card">
    <div class="title">
      员工列表
      <el-button @click="dialogFormVisible = true" type="primary">
        add
      </el-button>
    </div>
    <el-dialog v-model="dialogFormVisible" title="添加会员">
      <el-form :model="addform">
        <el-form-item label="用户编号" :label-width="formLabelWidth">
          <el-input v-model="addform.Uno" autocomplete="off" />
        </el-form-item>
        <el-form-item label="会员编号" :label-width="formLabelWidth">
          <el-input v-model="addform.VIPno" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户名称" :label-width="formLabelWidth">
          <el-input v-model="addform.Uname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户性别" :label-width="formLabelWidth">
          <el-input v-model="addform.Usex" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户邮箱" :label-width="formLabelWidth">
          <el-input v-model="addform.Umail" autocomplete="off" />
        </el-form-item>
      </el-form>      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="onadd"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="modifyDialogFormVisible" title="修改员工信息">
      <el-form :model="modifyform">
        <el-form-item label="用户编号" :label-width="formLabelWidth">
          <el-input v-model="modifyform.Uno" autocomplete="off" />
        </el-form-item>
        <el-form-item label="会员编号" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPno" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户名称" :label-width="formLabelWidth">
          <el-input v-model="modifyform.Uname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户性别" :label-width="formLabelWidth">
          <el-input v-model="modifyform.Usex" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户邮箱" :label-width="formLabelWidth">
          <el-input v-model="modifyform.Umail" autocomplete="off" />
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
      <el-table-column prop="Uno" label="用户编号" width="180" />
      <el-table-column prop="VIPno" label="会员编号" width="180" />
      <el-table-column prop="Uname" label="用户名称" />
      <el-table-column prop="Usex" label="用户性别" />
      <el-table-column prop="Umail" label="用户邮箱" />
      <el-table-column fixed="right" label="操作" width="160">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="modifyClick(scope.$index, scope.row)"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            @click="deleteClick(scope.$index, scope.row)"
          >
            <el-icon>
              <Delete />
            </el-icon>
          </el-button>
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
      addform: {
      Uno: "",
      VIPno: "",
      Uname: "",
      Usex: "",
      Umail: "",
      },
      modifyform: {
        Sid: "",
        Sname: "",
        Sposition: "",
        Sbasepay: "",
        Sbonus: "",
        Sworktime: "",
      },
      data: {
        Uno: "N/A",
        VIPno: "N/A",
        Uname: "N/A",
        Usex: "N/A",
        Umail: "N/A",
      },
      tableData: [],
      dialogFormVisible: false,
      modifyDialogFormVisible: false,
      formLabelWidth: "140px",
    };
  },
  mounted() {
    var config = {
      method: "get",
      url: "http://127.0.0.1:9000/front_desk/user",
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };
    const _this = this;
    axios(config)
      .then(function (response) {
        console.log(response.data);
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
        if (e.Uno == this.form.user) {
          this.data = e;
        }
      });
    },
    onadd() {
      this.dialogFormVisible = false;
      console.log(this.addform);
      var data = {
        Uno: this.addform.Uno,
        VIPno: this.addform.VIPno,
        Uname: this.addform.Uname,
        Usex: this.addform.Usex,
        Umail: this.addform.Umail,
      };
      var config = {
        method: "post",
        url: "http://127.0.0.1:9000/front_desk/user",
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
            url: "http://127.0.0.1:9000/front_desk/user",
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
      this.addform = clearform;
    },
    onmodify() {
      this.modifyDialogFormVisible = false;
      console.log(this.modifyform);
      var data = {
        Uno: String(this.modifyform.Sid),
        VIPno: this.modifyform.Sname,
        Uname: this.modifyform.Sposition,
        Usex: this.modifyform.Sbasepay,
        Umail: this.modifyform.Sbonus,
};

      var config = {
        method: "put",
        url: "http://127.0.0.1:9000/manager/staff/",
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
            url: "http://127.0.0.1:9000/manager/staff/",
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
    deleteClick(index, row) {
      // 发送审核删除请求
      console.log(index, row);
      const _this = this;

      var data = {
        Uno: String(row.Uno),
      };

      var config = {
        method: "delete",
        url: "http://127.0.0.1:9000/front_desk/user/",
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        data: data,
      };

      axios(config)
        .then(function (response) {
          var config = {
            method: "get",
            url: "http://127.0.0.1:9000/front_desk/user/",
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
    },
    modifyClick(index, row) {
      this.modifyform.Uno = String(row.Uno);
      this.modifyform.VIPno = row.VIPno;
      this.modifyform.Uname = row.Uname;
      this.modifyform.Usex = row.Usex;
      this.modifyform.Umail = row.Umail;
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
