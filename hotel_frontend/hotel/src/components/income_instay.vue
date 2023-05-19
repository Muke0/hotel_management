<template>
  <el-row>
    <el-col :span="24">
<!--      start-->
      <el-row>
        <el-col :span="12">
          <div class="grid-content bg-purple-light">
            <el-card shadow="hover" style="height: 90px;font-size: 50px;line-height: 50px">
              <i class="el-icon-user-solid" style="color: #67C23A;font-size: 50px"></i>
              {{flow_today}}
            </el-card>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-card shadow="hover" style="height: 90px;line-height: 50px;font-size: 50px">
              <i style="color: #E6A23C;font-size: 50px">&#xa5;</i>
              {{money_today}}
            </el-card>
          </div>
        </el-col>
      </el-row>
<!--      main part-->
      <el-row style="margin-top: 25px">
        <el-col :span="24">
          <el-row>
            <div class="grid-content bg-purple" style="display: inline-block;margin: 0 auto">
              <i class="el-icon-s-data"></i>
              近期收益/客流量数据统计
            </div>
            <el-radio-group v-model="tabPosition" @change="change" style="display: inline-block;float: right" size="small">
              <el-radio-button label="1">周</el-radio-button>
              <el-radio-button label="2">月</el-radio-button>
              <el-radio-button label="3">年</el-radio-button>
            </el-radio-group>
          </el-row>
          <el-row>
            <el-col :span="24" style="margin-top: 20px">
              <div class="grid-content bg-purple-dark" id="main"></div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>

    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "income_instay",
  data(){
    return {
      money_today:"",
      flow_today:"",
      tabPosition:'1',
      option: {
        color:['#5470C6','#EE6666'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          data: ['客流量',  '收益']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '客流量/人次',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: '#5470C6'
              }
            },
            axisLabel: {
              formatter: '{value} '
            }
          },
          {
            type: 'value',
            name: '收益/RMB',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: '#EE6666'
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '客流量',
            type: 'bar',
            data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
          },
          {
            name: '收益',
            type: 'line',
            yAxisIndex: 1,
            data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
          }
        ]
      }
    }
  },
  methods:{
    init(){
      var myChart = this.$echarts.init(document.getElementById('main'))
      myChart.setOption(this.option)
    },
    change(){
      this.$axios({
        method:'get',
        url:'http://localhost:9000/showdata/'+this.tabPosition
      }).then(res=>{
        var data=res.data
        var x_data=data.b
        var y_data_flow=data.a
        var y_data_money=data['money']
        for(var i=0;i<x_data.length;i++){
          var year=x_data[i].slice(0,4)
          var month=x_data[i].slice(4,6)
          var day=x_data[i].slice(6,8)
          if(this.tabPosition==1)
            x_data[i]=year+'-'+month+'-'+day
          if(this.tabPosition==2)
            x_data[i]=year+'-'+month+'-'+day
          if(this.tabPosition==3)
            x_data[i]=year+'-'+month
          x_data[i]=year+'-'+month+'-'+day
        }
        this.option.xAxis[0].data=x_data
        this.option.series[0].data=y_data_flow
        this.option.series[1].data=y_data_money
        this.init()
      })
    }
  },
  mounted() {
    this.$axios({
      method:'get',
      url:'http://localhost:9000/showincome'
    }).then(res=>{
      var data=res.data
      this.flow_today=data['n']
      this.money_today=data['revenue']
    })
    this.$axios({
      method:'get',
      url:'http://localhost:9000/showdata/'+this.tabPosition
    }).then(res=>{
      var data=res.data
      // console.log(data)
      var x_data=data.b
      var y_data_flow=data.a
      var y_data_money=data['money']
      for(var i=0;i<x_data.length;i++){
        var year=x_data[i].slice(0,4)
        var month=x_data[i].slice(4,6)
        var day=x_data[i].slice(6,8)
        x_data[i]=year+'-'+month+'-'+day
      }
      this.option.xAxis[0].data=x_data
      this.option.series[0].data=y_data_flow
      this.option.series[1].data=y_data_money
      this.init()
    })
  }
}
</script>

<style scoped>
#main{
  width: 100%;
  height: 407px;
  margin: 0 auto;
  background-color: white;
}
</style>