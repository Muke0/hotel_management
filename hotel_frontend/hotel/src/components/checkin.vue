<template>
  <el-main style="background-color:#e5e9f2">
    <el-row :gutter="20">
      <el-col :span="12">
        <div class="grid-content bg-purple">

          <div class="calender">
            <div class="cal-header">
              <span @click="month--">&lt;</span>
              <span>{{year}}年{{month}}月</span>
              <span @click="month++">&gt;</span>
            </div>
            <div class="cal-body">
              <div class="week-day">
                <div>日</div><div>一</div><div>二</div><div>三</div><div>四</div><div>五</div><div>六</div>
              </div>
              <div class="date-day">
                <div v-for="(item,key) in daysArr" :key="key"
                     :class="{'prev':item.type=='prev','current':item.type=='current','next':item.type=='next','currentDay':item.formatDate==today}">
                  {{item.day}}
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="grid-content bg-purple">
          <div id='line'></div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="24">
        <div class="grid-content bg-purple">
          <div id="week"></div>
        </div>
      </el-col>
    </el-row>

  </el-main>

</template>

<script>
export default {
  name:'total',
  data(){
    return{
      weekA:[],
      weekB:[],
      weekC:[],
      weekD:[],
      predata:[],
      day:'',
      month:'',
      year:'',
      today:'',
      daysArr:[],
    }
  },
  methods: {
    //获取当天日期年月 日
    _getCurrentDate(){
      let date = new Date()
      this.day = date.getDate()
      this.month = date.getMonth()+1
      this.year = date.getFullYear()
      this._getFirstDaysForMonth(this.year,this.month)
      this.today = this.formatDate(this.year,this.month,this.day)
      console.log(this.today);
    },
    //获取切换页月份第一天是周几与最后一天并获取月前与月后填补数组
    _getFirstDaysForMonth(year,month){
      //计算月份第一天是周几
      let weekdays = new Date(`${year}-${month}-01`).getDay()
      // if(weekdays==0){
      //     weekdays=6
      // }else{
      //     weekdays -=1
      // }
      let beforeMonthDaysArr = []
      let currentMonthDaysArr = []
      let afterMonthDaysArr = []
      //获取上一月总天数
      if(month){
        let temp=new Date(year,month-1,1);
        let day = new Date(temp.getTime() - 864e5).getDate();
        for(let i=0;i<day;i++){
          beforeMonthDaysArr.push({
            year:year,
            month:month - 1,
            day:i+1,
            type:'prev',
            formatDate:this.formatDate(year,month-1,i+1)
          })
        }
        beforeMonthDaysArr.splice(0,beforeMonthDaysArr.length-weekdays)
      }else{
        //12月
      }
      //获取当前月总天数
      let temp=new Date(year,month,1);
      let day = new Date(temp.getTime() - 864e5).getDate();
      let afterWeekdays = new Date(`${year}-${month}-${day}`).getDay()
      for(let i=0;i<day;i++){
        currentMonthDaysArr.push({
          year:year,
          month:month,
          day:i+1,
          type:'current',
          formatDate:this.formatDate(year,month,i+1)
        })
      }
      //获取下一月总天数
      if(beforeMonthDaysArr.length+currentMonthDaysArr.length+6-afterWeekdays===35){
        for(let i=0;i<13-afterWeekdays;i++){
          afterMonthDaysArr.push({
            year:year,
            month:month+1,
            day:i+1,
            type:'next',
            formatDate:this.formatDate(year,month+1,i+1)
          })
        }
      }else{
        for(let i=0;i<6-afterWeekdays;i++){
          afterMonthDaysArr.push({
            year:year,
            month:month+1,
            day:i+1,
            type:'next',
            formatDate:this.formatDate(year,month+1,i+1)
          })
        }
      }
      this.daysArr = [...beforeMonthDaysArr,...currentMonthDaysArr,...afterMonthDaysArr]
      console.log(this.daysArr);
    },
    formatDate(y,m,d){
      return `${y}-${m<10?'0'+m:m}-${d<10?'0'+d:d}`
    }
  },
  watch:{
    'month'(){
      if(this.month===0){
        this.month = 12
        this.year--
      }
      if(this.month===13){
        this.month = 1
        this.year++
      }
      this._getFirstDaysForMonth(this.year,this.month)
    }
  },
  mounted(){
    this._getCurrentDate()
    this.$axios({
      method:'get',
      url:'http://localhost:9000/beforehomeshow'
    }).then(res=>{
      console.log(res.data.x)
      var Data=res.data.x
      for (let i=0;i<4;i++){
        for (let j=0;j<7;j++){
          if (i===0){
            this.weekA[j]=Data[j][i]
          }
          if (i===1){
            this.weekB[j]=Data[j][i]
          }
          if (i===2){
            this.weekC[j]=Data[j][i]
          }
          if (i===3){
            this.weekD[j]=Data[j][i]
          }
        }

      }

    })


    this.$axios({
      method:'get',
      url:'http://localhost:9000/showdata/today'
    }).then(res=>{
      console.log(res.data)
      var arr = []
      for(let i in res.data){
        let o = {};
        o["name"] = res.data[i].a;
        o["value"] =res.data[i].count;
        arr.push(o)
      }
      this.predata = arr;
      console.log(this.predata)
      const daychart = this.$echarts.init(document.getElementById("line"));
      const weekchart = this.$echarts.init(document.getElementById("week"));
      weekchart.setOption({
        title: {
          text: '一周内各房型入住统计',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // Use axis to trigger tooltip
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: ['前六天', '前五天', '前四天', '前三天', '前两天', '前一天', '今日']
        },
        series: [
          {
            name: 'A',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.weekA
          },
          {
            name: 'B',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.weekB
          },
          {
            name: 'C',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.weekC
          },
          {
            name: 'D',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.weekD
          },
        ]
      }),
          daychart.setOption(
              {
                title: {
                  text: '各房型入住统计',
                  subtext:"今日",
                  left: 'center'
                },
                tooltip: {
                  trigger: 'item'
                },
                legend: {
                  orient: 'vertical',
                  left: 'left'
                },
                series: [
                  {
                    name: '房间类型',
                    type: 'pie',
                    radius: '50%',
                    data:this.predata,
                    emphasis: {
                      itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                      }
                    }
                  }
                ]
              }
          )
    })

  }
}
</script>

<style>

.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: white;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
#line{
  width: 100%;
  height: 407px;
  margin: 0 auto;
}
#week{
  width: 100%;
  height: 407px;
  margin: 0 auto;
}

</style>
<style lang="less" scoped>
.calender{
  width: 100%;
  height: calc(100% - 41px);

  border-radius: 2px;
  .cal-header{
    padding: 4px;
    height: 40px;
    text-align: center;
    span{
      padding: 0 10px;
      text-align: center;
    }
  }
  .cal-body{
    height: calc(100% - 27px);
    .week-day{
      display:flex;
      background: #99a9bf;
      height: 35px;
      line-height: 35px;
      margin: 0 10px;
      div{
        flex: 1;
        text-align: center;
      }
    }
    .date-day{
      display: grid;
      line-height: 50px;
      grid-template-columns:repeat(7,1fr);
      text-align: center;
      height: calc(100% - 35px);
      div{
        display: flex;
        justify-content: center;
        align-items: center;
      }
      .current{
        cursor: pointer;
      }
      .prev,.next{
        color: #909399;
      }
      .currentDay{
        color:#307AD1;
      }
    }
  }
}
</style>