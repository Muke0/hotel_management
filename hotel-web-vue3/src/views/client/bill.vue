<template>
  <headbar></headbar>
  <div name="搜索" class="card">
    <el-form :inline="true" :model="form">
      <el-form-item label="房间编号">
        <el-input v-model="form.user" placeholder="ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">Query</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col :span="6"><el-statistic title="房间编号" :value="data.FJnumber" /></el-col>
      <el-col :span="6"><el-statistic title="类型编号" :value="data.LXnumber" /></el-col>
      <el-col :span="6"><el-statistic title="客房入住编号" :value="data.KFRZnumber" /></el-col>
      <el-col :span="6"><el-statistic title="客房金额" :value="data.KFmoney" /></el-col>
      <el-col :span="6"><el-statistic title="客房地址" :value="data.KFaddress" /></el-col>
      <el-col :span="6"><el-statistic title="客房配置" :value="data.KFnet" /></el-col>
      <el-col :span="6"><el-statistic title="客房特点" :value="data.KFnote" /></el-col>
    </el-row>
  </div>
  <div class="card">
    <div class="title">
      客房列表
    </div>
    <el-dialog v-model="modifyDialogFormVisible" title="修改客房入住信息">
      <el-form :model="modifyform">
        <el-form-item label="客房入住编号" :label-width="formLabelWidth">
          <el-input v-model="modifyform.KFRZnumber" autocomplete="off" />
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
      <el-table-column prop="FJnumber" label="房间编号" width="180" />
      <el-table-column prop="LXnumber" label="类型编号" width="180" />
      <el-table-column prop="KFRZnumber" label="客房入住编号" />
      <el-table-column prop="KFmoney" label="客房金额" />
      <el-table-column prop="KFaddress" label="客房地址" />
      <el-table-column prop="KFnet" label="客房配置" />
      <el-table-column prop="KFnote" label="客房特点" />

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
        KFRZnumber: "",
        FJnumber:"",
      },
      data: {
        FJnumber: "N/A",
        LXnumber: "N/A",
        KFRZnumber: "N/A",
        KFmoney: "N/A",
        KFaddress: "N/A",
        KFnet: "N/A",
        KFnote: "N/A",
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
      url: "http://127.0.0.1:9000/front_desk/room_info",
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
        if (e.FJnumber == this.form.user) {
          this.data = e;
        }
      });
    },
    onmodify() {
      this.modifyDialogFormVisible = false;
      console.log(this.modifyform);
      var data = {
        KFRZnumber: this.modifyform.KFRZnumber,
        FJnumber: this.modifyform.FJnumber,
      };
      var config = {
        method: "put",
        url: "http://127.0.0.1:9000/front_desk/room_info",
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
            url: "http://127.0.0.1:9000/front_desk/room_info",
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
        KFRZnumber: "",
      };
      this.modifyform = clearform;
    },
    modifyClick(index, row) {
      this.modifyform.FJnumber = String(row.FJnumber);
      this.modifyform.LXnumber = row.LXnumber;
      this.modifyform.KFRZnumber = row.KFRZnumber;
      this.modifyform.KFmoney = row.KFmoney;
      this.modifyform.KFaddress = row.KFaddress;
      this.modifyDialogFormVisible = true;
    },
  },
};
</script>
