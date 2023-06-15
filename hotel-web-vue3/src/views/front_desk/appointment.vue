<template>
  <headbar></headbar>
  <div name="搜索" class="card">
    <el-form :inline="true" :model="form">
      <el-form-item label="预约编号">
        <el-input v-model="form.user" placeholder="ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">Query</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col :span="6"><el-statistic title="预约编号" :value="data.Rno" /></el-col>
      <el-col :span="6"><el-statistic title="用户身份证号" :value="data.Uno" /></el-col>
      <el-col :span="6"><el-statistic title="预约客房类型" :value="data.Rtype" /></el-col>
      <el-col :span="6"><el-statistic title="是否通过" :value="data.passed" /></el-col>
      <el-col :span="6"><el-statistic title="预约金额" :value="data.Rmoney" /></el-col>
    </el-row>
  </div>
  <div class="card">
    <div class="title">
      预约列表
    </div>
    <el-dialog v-model="modifyDialogFormVisible" title="修改预约信息">
      <el-form :model="modifyform">
        <el-form-item label="是否通过" :label-width="formLabelWidth">
          <el-input v-model="modifyform.passed" autocomplete="off" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="modifyDialogFormVisible = true">取消</el-button>
          <el-button type="primary" @click="onmodify"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="Rno" label="预约编号" width="180" />
      <el-table-column prop="Uno" label="用户身份证号" width="180" />
      <el-table-column prop="Rtype" label="预约客房类型" />
      <el-table-column prop="passed" label="是否通过" />
      <el-table-column prop="Rmoney" label="预约金额" />

      <el-table-column fixed="right" label="操作" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="modifyClick(scope.$index, scope.row)">修改</el-button>
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
      modifyform: {
        Rno: "",
        passed: "",
      },
      data: {
        Rno: "N/A",
        Uno: "N/A",
        Rtype: "N/A",
        passed: "N/A",
        Rmoney: "N/A",
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
      url: "http://127.0.0.1:9000/front_desk/appointment",
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
        if (e.Rno == this.form.user) {
          this.data = e;
        }
      });
    },
    onmodify() {
      this.modifyDialogFormVisible = false;
      console.log(this.modifyform);
      var data = {
        Rno: this.modifyform.Rno,
        passed: this.modifyform.passed,
      };
      var config = {
        method: "put",
        url: "http://127.0.0.1:9000/front_desk/appointment",
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
            url: "http://127.0.0.1:9000/front_desk/appointment",
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
        passed: "",
      };
      this.modifyform = clearform;
    },
    modifyClick(index, row) {
      this.modifyform.Rno = row.Rno;
      this.modifyform.passed = row.passed;
      this.modifyDialogFormVisible = true;
    },
  },
};
</script>
