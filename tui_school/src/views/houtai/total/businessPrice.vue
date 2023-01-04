<template>
    <div id="main">
    </div>
</template>

<script>
    export default {
        name:'main',
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
                this.$http.get('user/priceBusiness').then(function (resp) {
                var array = resp.data.data;
                var name = array[0];
                var price = array[1];
                var main = document.getElementById('main');
                var myChart = that.$echarts.init(main);
                var option = {
                    title: {
                        text: '店铺收入统计',
                        subtext: 'second mrcai',
                        left: 'center'
                    },
                        xAxis: {
                            type: 'category',
                            data: name,
                            axisLabel:{
                                interval: 0,//X轴信息全部展示
                                rotate: 50,//60 标签倾斜的角度
                            }
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                data: price,
                                type: 'bar'
                            }
                        ]
                    };

                    option && myChart.setOption(option);


               })

            }
        }
    }
</script>

<style>
    #main {
        width: 600px;
        height: 500px;
        margin-left: 25%;
    }
</style>
