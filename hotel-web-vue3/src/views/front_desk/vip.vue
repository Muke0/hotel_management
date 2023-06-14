<template>
  <headbar></headbar>
  <div name="搜索" class="card">
    <el-form :inline="true" :model="form">
      <el-form-item label="员工ID">
        <el-input v-model="form.user" placeholder="ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">Query</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col :span="6"><el-statistic title="职工ID" :value="data.Sid" /></el-col>
      <el-col :span="6"><el-statistic title="姓名" :value="data.Sname" /></el-col>
      <el-col :span="6"><el-statistic title="职位" :value="data.Sposition" /></el-col>
      <el-col :span="6"><el-statistic title="基本工资" :value="data.Sbasepay" /></el-col>
      <el-col :span="6"><el-statistic title="奖金" :value="data.Sbonus" /></el-col>
      <el-col :span="6"><el-statistic title="工作时间" :value="data.Sworktime" /></el-col>
    </el-row>
  </div>
  <div class="card">
    <div class="title">
      员工列表
      <el-button @click="dialogFormVisible = true" type="primary">
        add
      </el-button>
    </div>
    <el-dialog v-model="dialogFormVisible" title="添加员工">
      <el-form :model="addform">
        <el-form-item label="员工ID" :label-width="formLabelWidth">
          <el-input v-model="addform.Sid" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="addform.Sname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="职位" :label-width="formLabelWidth">
          <el-select
            v-model="addform.Sposition"
            :placeholder="positionholder(0)"
          >
            <el-option
              v-for="item in positionOptions"
              :key="item.value"
              :lable="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
          <el-form-item label="基本工资" :label-width="formLabelWidth">
            <el-input v-model="addform.Sbasepay" autocomplete="off" />
          </el-form-item>
          <el-form-item label="奖金" :label-width="formLabelWidth">
            <el-input v-model="addform.Sbonus" autocomplete="off" />
          </el-form-item>
          <el-form-item label="工作时间" :label-width="formLabelWidth">
            <el-input v-model="addform.Sworktime" autocomplete="off" />
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
        <el-form-item label="员工ID" :label-width="formLabelWidth">
          <el-input v-model="modifyform.Sid" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="modifyform.Sname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="职位" :label-width="formLabelWidth">
          <el-select
            v-model="modifyform.Sposition"
            :placeholder="positionholder(0)"
          >
            <el-option
              v-for="item in positionOptions"
              :key="item.value"
              :lable="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
          <el-form-item label="基本工资" :label-width="formLabelWidth">
            <el-input v-model="modifyform.Sbasepay" autocomplete="off" />
          </el-form-item>
          <el-form-item label="奖金" :label-width="formLabelWidth">
            <el-input v-model="modifyform.Sbonus" autocomplete="off" />
          </el-form-item>
          <el-form-item label="工作时间" :label-width="formLabelWidth">
            <el-input v-model="modifyform.Sworktime" autocomplete="off" />
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
      <el-table-column prop="Sid" label="员工ID" width="180" />
      <el-table-column prop="Sname" label="姓名" width="180" />
      <el-table-column prop="Sposition" label="职位" />
      <el-table-column prop="Sbasepay" label="基本工资" />
      <el-table-column prop="Sbonus" label="奖金" />
      <el-table-column prop="Sworktime" label="工作时间" />

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
        Sid: "",
        Sname: "",
        Sposition: "",
        Sbasepay: "",
        Sbonus: "",
        Sworktime: "",
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
        Sid: "N/A",
        Sname: "N/A",
        Sposition: "N/A",
        Sbasepay: "N/A",
        Sbonus: "N/A",
        Sworktime: "N/A",
      },
      positionOptions: [
        {
          value: "前台",
          label: "前台",
        },
        {
          value: "客房",
          label: "客房",
        },
      ],
      tableData: [],
      dialogFormVisible: false,
      modifyDialogFormVisible: false,
      formLabelWidth: "140px",
    };
  },
  mounted() {
    var config = {
      method: "get",
      url: "http://127.0.0.1:9000/manager/staff",
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
        if (e.Sid == this.form.user) {
          this.data = e;
        }
      });
    },
    onadd() {
      this.dialogFormVisible = false;
      console.log(this.addform);
      var data = {
        Sid: String(this.addform.Sid),
        Sname: this.addform.Sname,
        Sposition: this.addform.Sposition,
        Sbasepay: this.addform.Sbasepay,
        Sbonus: this.addform.Sbonus,
        Sworktime: this.addform.Sworktime,
      };
      var config = {
        method: "post",
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
      this.addform = clearform;
    },
    onmodify() {
      this.modifyDialogFormVisible = false;
      console.log(this.modifyform);
      var data = {
        Sid: String(this.modifyform.Sid),
        Sname: this.modifyform.Sname,
        Sposition: this.modifyform.Sposition,
        Sbasepay: this.modifyform.Sbasepay,
        Sbonus: this.modifyform.Sbonus,
        Sworktime: this.modifyform.Sworktime,
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
        Sid: String(row.Sid),
      };

      var config = {
        method: "delete",
        url: "http://127.0.0.1:9000/manager/staff/",
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        data: data,
      };

      axios(config)
        .then(function (response) {
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
    },
    modifyClick(index, row) {
      this.modifyform.sId = String(row.sId);
      this.modifyform.sName = row.sName;
      this.modifyform.sPosition = row.sPosition;
      this.modifyform.sType = row.sType;
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
