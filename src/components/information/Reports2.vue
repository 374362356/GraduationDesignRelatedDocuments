<template>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="chartLine" class="line-wrap"></div>
</template>

<script>
    import * as echarts from 'echarts';
    require('echarts/theme/shine');//引入主题

    export default {
    data() {
      return {
        chartLine: null
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.drawLineChart();
      })
    },
    methods: {
      drawLineChart() {
        this.chartLine = echarts.init(this.$el,'shine');// 基于准备好的dom，初始化echarts实例
        let option = {
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['开展会议', '公告发布', '用户人数', '建议条数', '工作人员']
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    axisTick: {
                        show: false
                    },
                    data : ['周一','周二','周三','周四','周五','周六','周日']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisTick: {
                        show: false
                    },
                    name: '（项/人）'
                }
            ],
            series : [
                {
                    name:'开展会议',
                    type:'line',
                    stack: '总量',
                    data:[12, 13, 4, 13, 9, 23, 21]
                },
                {
                    name:'公告发布',
                    type:'line',
                    stack: '总量',
                    data:[22, 18, 19, 23, 29, 3, 31]
                },
                {
                    name:'用户人数',
                    type:'line',
                    stack: '总量',
                    data:[15, 22, 20, 15, 10, 33, 10]
                },
                {
                    name:'建议条数',
                    type:'line',
                    stack: '总量',
                    data:[20, 32, 30, 8, 17, 22, 32]
                },
                {
                    name:'工作人员',
                    type:'line',
                    stack: '总量',
                    data:[82, 93, 90, 93, 129, 133, 132]
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表
        this.chartLine.setOption(option);

        this.$http({
            //axios调取数据
            url: "reports/type/1", //调取接口
            method: "get"
            }).then(back => {
            let { data, meta } = back.data;
            console.log(data);
            //响应成功之后再调用
            if (meta.status == 200) {
                //这个是添加那个折线统计图不铺满的问题
                data.xAxis[0].boundaryGap = false;
                //  console.log(data.xAxis[0].data)
                //把调用来的数据直接给这个方法
                myChart.setOption(data);
            }
            });
      }
    }
  }
</script>

<style lang='less' scope>
    .line-wrap{
        width:90%;
        height:450px;
    }
</style>