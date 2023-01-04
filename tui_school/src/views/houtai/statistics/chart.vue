<template>
    <div style="width: 1290px;height: 520px;overflow-y:hidden;">
        <el-card class="box-card">
        <el-container>
            <el-aside style="width: 400px">
                <div id="memberMain" style="height: 430px;width: 100%;margin-top: 20px"></div>
            </el-aside>
            <el-main>
                <el-form :inline="true" size="mini" :model="moneyForm" >
                    <el-form-item >
                                <el-date-picker
                                        v-model="moneyForm.gmtCreate"
                                        type="year"
                                        placeholder="选择开始年"
                                        :picker-options="gmtCreate"
                                        value-format="yyyy">
                                </el-date-picker>

                            <el-date-picker
                                    align="right"
                                    type="year"
                                    v-model="moneyForm.gmtModified"
                                    placeholder="选择结束年"
                                    value-format="yyyy"
                            >
                            </el-date-picker>

                        <el-button icon='el-icon-search' type="primary" @click="moneyQuery">查询</el-button>
                                        <el-button icon='el-icon-refresh' class="filter-item" @click="handleReset1">重置</el-button>

                    </el-form-item>
                </el-form>
                <div id="container" style="height: 430px;width: 62%; margin-left: 150px"></div>

            </el-main>
        </el-container>



        </el-card>
    </div>
</template>

<script>

    export default {
        name: "chart",
        data(){
            return{
                memberData:[],
                name:[],
                value:[],

                //扇形图
                isType:[],
                count:[],
                //开始的时间
                //数据
                moneyForm:{
                    gmtCreate:"",
                    gmtModified:"",
                },
                memberForm:{
                    gmtCreate:"",
                    gmtModified:"",

                },
                //开始的时间
                gmtCreate: {
                    disabledDate(time) {
                        return time.getTime() >Date.now();
                    },
                },

                //结束时间
                gmtModified: {
                    disabledDate: (time) => {
                        // console.log(time)
                        if(this.formData.gmtModified!= ""){
                            return time.getTime() < new Date(this.formData.gmtModified).getTime() || time.getTime() >Date.now();//禁用开始日期之前的日期
                        }
                    }
                },
            }
        },
        mounted(){
            this.drawMenberChart();
            this.initMoney();
        },

        methods:{
            chartQuery(){
                this.initTable();
            },
            moneyQuery(){
                this.initMoney();
            },
            handleReset(){
                this.memberForm={},
                this.initTable();
            },
            handleReset1(){
              this.moneyForm={},
              this.initMoney();
            },
            //用户类型饼图
            drawMenberChart() {
                var that=this;
                //基于准备好的dom，初始化echarts实例
                var myChart = this.$echarts.init(document.getElementById('memberMain'));

                //发送请求获取数据
                // this.$axios.get("http://localhost:8051/member/queryMemberTypeBang/").then((resp)=>{
                this.$http.post("/stat/member/queryMemberTypeBang",this.memberForm).then(function (resp) {
                    // console.log(resp.data.result)
                    var servicedata=[];
                    if (resp.data.code===2000){
                        // servicedata=resp.data.result;
                                    var servicedata=[];
                                    for (let i = 0; i < resp.data.result.length; i++) {
                                        var obj=new Object();
                                        that.name[i]=resp.data.result[i].name;
                                        that.value[i]=resp.data.result[i].value;
                                        obj.name= that.name[i]
                                        obj.value=that.value[i]
                                        servicedata[i]=obj;
                                    }
                        console.log(servicedata)
                        // 绘制图表
                        myChart.setOption({
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b}: {c} ({d}%)'
                            },

                            legend: {
                                top: 'top',
                                orient: 'vertical',
                                left: 'left',
                                data:servicedata,
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            series: [
                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    selectedMode: 'single',
                                    radius: [0, '15%'],
                                    label: {
                                        position: 'inner',
                                        fontSize: 14,
                                    },
                                    labelLine: {
                                        show: false
                                    },
                                    data: servicedata,
                                },

                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    radius: ['0%', '40%'],
                                    labelLine: {
                                        length: 10,
                                    },

                                    label: {
                                        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                                        backgroundColor: '#F6F8FC',
                                        borderColor: '#8C8D8E',
                                        borderWidth: 1,
                                        borderRadius: 4,

                                        rich: {
                                            a: {
                                                color: '#6E7079',
                                                lineHeight: 12,
                                                align: 'center'
                                            },
                                            hr: {
                                                borderColor: '#8C8D8E',
                                                width: '100%',
                                                borderWidth: 1,
                                                height: 0
                                            },
                                            b: {
                                                color: '#4C5058',
                                                fontSize: 8,
                                                fontWeight: 'bold',
                                                lineHeight: 13
                                            },
                                            per: {
                                                color: '#fff',
                                                backgroundColor: '#4C5058',
                                                padding: [1, 2],
                                                borderRadius: 4
                                            }
                                        }
                                    },
                                    data:servicedata,
                                }
                            ]
                        });
                    }
                })

            },
            initMoney(){
                // console.log(this.moneyForm.gmtCreate)
                var myChart = this.$echarts.init(document.getElementById('container'));
                var that=this;
                this.$http.post("/stat/order/queryAllOrderBang",this.moneyForm).then(function (resp) {
                    console.log(resp)
                    // console.log(resp)
                    // console.log(resp.data.result)
                    var servicedata=[];
                    if (resp.data.code===2000){
                        // for (let i = 0; i < resp.data.result.length; i++) {
                            servicedata=resp.data.result;
                        //     var obj=new Object();
                        //     that.name[i]=resp.data.result[i].gmtCreate;
                        //     that.value[i]=resp.data.result[i].totalFee;
                        //     obj.name=that.name[i]
                        //     obj.value=that.value[i]
                        //     servicedata[i]=obj;
                        // }
                        // console.log(servicedata)
                        // console.log(that.name)
                        // console.log(that.value)

                    myChart.setOption({
                        title: {
                            text: '每年销售额',


                            left: 'left'
                        },

                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },

                        toolbox: {
                            show: true,
                            feature: {
                                mark: {show: true},
                                dataView: {show: true, readOnly: false},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },
                        legend: {
                            left: 'center',
                            top: 'bottom',
                            data:servicedata,
                        },

                        series: [
                            {
                                name: '每年销售额',
                                type: 'pie',
                                radius: [40, 170],
                                center: ['40%', '50%'],
                                roseType: 'area',
                                itemStyle: {
                                    borderRadius: 8
                                },
                                data: servicedata,


                            }
                        ]
                    })
                    }
                })

            },

        },
    }

</script>

<style scoped>
    .el-aside {



        height: 520px;
        width: 900px;
    }

    .el-main {

        height: 520px;
        width: 650px;
    }
    body{ overflow-x:hidden; overflow-y:hidden;}
</style>
