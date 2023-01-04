<template>
    <div id="histogramChart" style="left: 25% ;width: 600px; height: 600px;">
    </div>
</template>

<script>
    export default {
        name:'Histogram',
        data(){
            return {

            }
        },
        created() {
            this.drawHistogarm();
        },
        methods:{
            drawHistogarm(){
                var that=this;
                this.$http.get('user/numberUser').then(function (resp) {
                    // console.log(resp.data.data)
                        var array = resp.data.data;
                        //初始化echarts实例
                    var histogramChart = document.getElementById('histogramChart');
                    var myChart = that.$echarts.init(histogramChart);

                    var option = {
                        title: {
                            text: '用户商家统计对比',
                            subtext: 'second mrcai',
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
                                name: 'Access From',
                                type: 'pie',
                                radius: '60%',
                                data: array,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    },
                                    normal:{
                                        label:{
                                            show: true,
                                            formatter: '{b} : {c} ({d}%)'
                                        },
                                        labelLine :{show:true}
                                    }
                                }
                            }
                        ]
                    };

                    option && myChart.setOption(option);
                })

            }
        }
    }
</script>

<style scoped>
    #histogramChart {
        width: 500px;
        height: 500px;
    }
</style>
