<template>
  <headbar></headbar>
  <div name="搜索" class="card">
    <el-form :inline="true" :model="form">
      <el-form-item label="会员ID">
        <el-input v-model="form.user" placeholder="ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">Query</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col :span="6"><el-statistic title="会员编号" :value="data.VIPno" /></el-col>
      <el-col :span="6"><el-statistic title="会员等级" :value="data.VIPlevel" /></el-col>
      <el-col :span="6"><el-statistic title="会员权限" :value="data.VIPpower" /></el-col>
      <el-col :span="6"><el-statistic title="会员时长" :value="data.VIPduration" /></el-col>
      <el-col :span="6"><el-statistic title="积分" :value="data.VIPpoints" /></el-col>
      <el-col :span="6"><el-statistic title="会员信息" :value="data.VIPinformation" /></el-col>
    </el-row>
  </div>
  <div class="card">
    <div class="title">
      会员列表
      <el-button @click="dialogFormVisible = true" type="primary">
        add
      </el-button>
    </div>
    <el-dialog v-model="dialogFormVisible" title="添加会员">
      <el-form :model="addform">
        <el-form :model="addform">
          <el-form-item label="会员编号" :label-width="formLabelWidth">
            <el-input v-model="addform.VIPno" autocomplete="off" />
          </el-form-item>
          <el-form-item label="会员等级" :label-width="formLabelWidth">
            <el-input v-model="addform.VIPlevel" autocomplete="off" />
          </el-form-item>
          <el-form-item label="会员权限" :label-width="formLabelWidth">
            <el-input v-model="addform.VIPpower" autocomplete="off" />
          </el-form-item>
          <el-form-item label="会员时长" :label-width="formLabelWidth">
            <el-input v-model="addform.VIPduration" autocomplete="off" />
          </el-form-item>
          <el-form-item label="积分" :label-width="formLabelWidth">
            <el-input v-model="addform.VIPpoints" autocomplete="off" />
          </el-form-item>
          <el-form-item label="会员信息" :label-width="formLabelWidth">
            <el-input v-model="addform.VIPinformation" autocomplete="off" />
          </el-form-item>
        </el-form>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="onadd"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="modifyDialogFormVisible" title="修改会员信息">
      <el-form :model="modifyform">
        <el-form-item label="会员编号" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPno" autocomplete="off" />
        </el-form-item>
        <el-form-item label="会员等级" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPlevel" autocomplete="off" />
        </el-form-item>
        <el-form-item label="会员权限" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPpower" autocomplete="off" />
        </el-form-item>
        <el-form-item label="会员时长" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPduration" autocomplete="off" />
        </el-form-item>
        <el-form-item label="积分" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPpoints" autocomplete="off" />
        </el-form-item>
        <el-form-item label="会员信息" :label-width="formLabelWidth">
          <el-input v-model="modifyform.VIPinformation" autocomplete="off" />
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
      <el-table-column prop="VIPno" label="VIP编号" width="180" />
      <el-table-column prop="VIPlevel" label="VIP等级" width="180" />
      <el-table-column prop="VIPpower" label="VIP权限" />
      <el-table-column prop="VIPduration" label="VIP时长" />
      <el-table-column prop="VIPpoints" label="VIP积分" />
      <el-table-column prop="VIPinformation" label="VIP信息" />
      <el-table-column fixed="right" label="操作" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="modifyClick(scope.$index, scope.row)">修改</el-button>
          <el-button link type="primary" @click="deleteClick(scope.$index, scope.row)">
            <el-icon>
              <Delete />
            </el-icon>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center">
      <el-pagination layout="prev, pager, next" :total="10" style="margin: 0 auto; width: 200px" />
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
        VIPno: "",
        VIPlevel: "",
        VIPpower: "",
        VIPduration: "",
        VIPpoints: "",
        VIPinformation: "",
      },
      modifyform: {
        VIPno: "",
        VIPlevel: "",
        VIPpower: "",
        VIPduration: "",
        VIPpoints: "",
        VIPinformation: "",
      },
      data: {
        VIPno: "N/A",
        VIPlevel: "N/A",
        VIPpower: "N/A",
        VIPduration: "N/A",
        VIPpoints: "N/A",
        VIPinformation: "N/A",
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
      url: "http://127.0.0.1:9000/front_desk/vip",
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
        if (e.VIPno == this.form.user) {
          this.data = e;
        }
      });
    },
    onadd() {
      this.dialogFormVisible = false;
      console.log(this.addform);
      var data = {
        VIPno: String(this.addform.VIPno),
        VIPlevel: this.addform.VIPlevel,
        VIPpower: this.addform.VIPpower,
        VIPduration: this.addform.VIPduration,
        VIPpoints: this.addform.VIPpoints,
        VIPinformation: this.addform.VIPinformation,
      };
      var config = {
        method: "post",
        url: "http://127.0.0.1:9000/front_desk/vip",
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
            url: "http://127.0.0.1:9000/front_desk/vip",
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
        VIPno: "",
        VIPlevel: "",
        VIPpower: "",
        VIPduration: "",
        VIPpoints: "",
        VIPinformation: "",
      };
      this.addform = clearform;
    },
    onmodify() {
      this.modifyDialogFormVisible = false;
      console.log(this.modifyform);
      var data = {
        VIPno: String(this.modifyform.VIPno),
        VIPlevel: this.modifyform.VIPlevel,
        VIPpower: this.modifyform.VIPpower,
        VIPduration: this.modifyform.VIPduration,
        VIPpoints: this.modifyform.VIPpoints,
        VIPinformation: this.modifyform.VIPinformation,
      };
      var config = {
        method: "put",
        url: "http://127.0.0.1:9000/front_desk/vip",
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
            url: "http://127.0.0.1:9000/front_desk/vip",
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
        VIPno: "",
        VIPlevel: "",
        VIPpower: "",
        VIPduration: "",
        VIPpoints: "",
        VIPinformation: "",
      };
      this.modifyform = clearform;
    },
    deleteClick(index, row) {
      // 发送审核删除请求
      console.log(index, row);
      const _this = this;

      var data = {
        VIPno: String(row.VIPno),
      };

      var config = {
        method: "delete",
        url: "http://127.0.0.1:9000/front_desk/vip",
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        data: data,
      };

      axios(config)
        .then(function (response) {
          var config = {
            method: "get",
            url: "http://127.0.0.1:9000/front_desk/vip",
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
      this.modifyform.VIPno = String(row.VIPno);
      this.modifyform.VIPlevel = row.VIPlevel;
      this.modifyform.VIPpower = row.VIPpower;
      this.modifyform.VIPduration = row.VIPduration;
      this.modifyform.VIPpoints = row.VIPpoints;
      this.modifyform.VIPinformation = row.VIPinformation;
      this.modifyDialogFormVisible = true;
    },
  },
};
</script>
