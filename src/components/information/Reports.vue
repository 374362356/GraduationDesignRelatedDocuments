<template>
    <div id="chartPie" class="pie-wrap"></div>

</template>

<script>
    import * as echarts from 'echarts';
    require('echarts/theme/macarons');//引入主题

    export default {
    data() {
      return {
        chartPie: null,
        names:[],   //
        brower:[],
      }
    },
    created(){
      this.getDataInfo();
    },
    mounted() {
      this.$nextTick(() => {
        this.drawPieChart();
      })
    },
    methods: {
      drawPieChart() {
        let mytextStyle = {
          color: "#333",                          
          fontSize: 18,                            
        };
        let mylabel = {
          show: true,                 
          position: "right",          
          offset: [30, 40],             
          formatter: '{b} : {c} ({d}%)',      
          textStyle: mytextStyle
        };
        this.chartPie = echarts.init(document.getElementById('chartPie'),'macarons');
        this.chartPie.setOption({
          title: {
            text: 'Pie Chart',
            subtext: '会议信息统计',
            x: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)",
          },
          legend: {
            data: ['开展会议', '公告发布', '用户人数', '建议条数', '工作人员'],
            left:"center",                              
            top:"bottom",                              
            orient:"horizontal",                        
          },
          series: [
            {
              name: '访问来源',
              type: 'pie',
              radius: ['50%', '70%'],
              center: ['50%', '50%'],
              data: //this.brower
              [
                {value: 335, name: '开展会议'},
                {value: 310, name: '公告发布'},
                {value: 234, name: '用户人数'},
                {value: 135, name: '建议条数'},
                {value: 1548, name: '工作人员'}
              ]
              ,
              animationEasing: 'cubicInOut',
              animationDuration: 2600,
              label: {           
                emphasis: mylabel
              }
            }
          ],
        });
      },
      getDataInfo(){
          this.$axios({
            method:'get',
            url:'reports/queryInfo',
          }).then((res)=>{
              /*[
              {"name":"开展会议","value":"6"},
              {"name":"公告发布","value":"6"},
              {"name":"用户人数","value":"8"},
              {"name":"建议条数","value":"5"},
              {"name":"工作人员","value":"88"},
              {"name":"测试","value":"22"}
              ]*/
               this.brower = res.data.data;
              // for(var key in result){
              //   this.names.push(key)
              //   this.brower.push({
              //         name:key,
              //         value:result[key]
              //     });
              // }
              alert(JSON.stringify(this.brower))
          })
          .catch(()=>{
            this.$message.error('获取图标数据失败')
          })
      },
      
    }
  }

</script>

<style lang='less' scope>
    .pie-wrap{
        width:100%;
        height:450px;
    }
</style>