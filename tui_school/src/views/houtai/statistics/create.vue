<template>
    <div tyle="width: 1200px;height: 485px;overflow-y:hidden;">


    <el-card class="box-card" style="width: 1290px;height: 695px">
        <el-row :gutter="20">
            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>

        </el-row>
        <el-form :inline="true" size="mini" :model="classBang">
            <el-form-item >
                <el-date-picker
                        align="right"
                        type="date"
                        v-model="classBang.gmt_create"
                        placeholder="选择开始日期"

                        value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
                <el-date-picker
                        align="right"
                        type="date"
                        v-model="classBang.gmt_modified"
                        placeholder="选择结束日期"
                        value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
                <el-button icon='el-icon-search' type="primary" @click="likeQuery">查询</el-button>
            </el-form-item>
        </el-form>
        <div id='myChart' style="width: 900px;height: 450px" ></div>

        <div id='main' style="width: 800px;height: 550px;;margin-left: 600px;margin-top:60px;margin-bottom: 400px;position: relative;top: -500px"  ></div>
    </el-card>
    </div>
</template>

<script>
    export default {
        name: "create",
        data () {
            return {
                chart: null,
                data: {},
                people: '',
                msg: 'Welcome to Your Vue.js App',
                // Tables:[],
                title:[],
                buyCount:[],
                classBang: {
                    id:"",
                    teacherId:"",
                    subjectId:"",
                    subjectParentId:"",
                    title:"",
                    price:"",
                    lessonNum:"",
                    cover:"",
                    buyCount:"",
                    viewCount:"",
                    version:"",
                    status:"",
                    isDeleted:"",
                    gmtCreate:"",
                    gmtModified:"",
                    remark:"",
                    gmt_create:"",
                    gmt_modified:"",

                },
                //开始的时间
                gmt_create: {
                    disabledDate(time) {

                        return time.getTime() >Date.now();
                    },
                },
                //结束时间
                gmt_modified: {
                    disabledDate: (time) => {
                        if(this.classBang.gmt_modified!= ""){
                            return time.getTime() < new Date(this.classBang.gmt_modified).getTime() || time.getTime() >Date.now();//禁用开始日期之前的日期
                        }
                    }
                },

            }
        },
        mounted(){
            this.initTable();
            this.initbingtu();
        },

        methods: {
            likeQuery(){
                this.initTable();
                this.initbingtu();
            },
            handleReset(){
                this.classBang={};
                this.initTable();
            },
            //
            initTable(){
                var  myChart = this.$echarts.init(document.getElementById('myChart'))
                var that=this;
                // console.log(this.classBang.gmtCreate);
                that.$http.post("/stat/course/queryAllCourseRanking",this.classBang).then(function (resp) {
                    for (var i = 0; i < resp.data.result.result.length; i++) {
                        that.title[i]=resp.data.result.result[i].title;
                        that.buyCount[i]=resp.data.result.result[i].buyCount;
                    }
                    // console.log(that.buyCount);
                    // console.log(that.title)
                    // console.log(that.buyCount.toString())
                    // that.drawLine();
                    myChart.setOption({
                        title: { text: '课程销售量' },
                        tooltip: {},

                        // },
                        xAxis: {
                            "axisLabel":{     //加上这个强制显示
                                interval: 0
                            },
                            type: 'category',
                            data: that.title,
                            axisLabel:{interval:0,rotate:-10,margin:10},//将柱状图显示横坐标字体旋转30度
                            splitLine: {show: false},
                            axisTick: {
                                alignWithLabel: true
                            }
                        },
                        legend: {
                            orient: 'vertical',
                            right: 'right',
                        },

                        yAxis: {},
                        series: [{
                            // name: that.title[i],
                            type: 'bar',
                            data: that.buyCount,
                            itemStyle: {
                                normal: {
                                    //通过数组下标选择颜色
                                    color: function(params) {
                                        var colorList = [
                                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'
                                        ];
                                        return colorList[params.dataIndex]
                                    },
                                }
                            }
                        }]
                    });
                })
                // this.drawLine();
            },
        //    饼图
            initbingtu(){
                var myChart = this.$echarts.init(document.getElementById('main'));
                var that=this;
                this.$http.post("/stat/course/queryAllCourseRanking",this.classBang).then(function (resp) {
                    var arr=[];
                    for (let i = 0; i <resp.data.result.result.length; i++) {
                        var obj=new Object();
                        that.title[i]=resp.data.result.result[i].title;
                        that.buyCount[i]=resp.data.result.result[i].buyCount;
                        obj.name=that.title[i];
                        obj.value=that.buyCount[i];
                        arr[i]=obj;
                    }
                    // console.log(that.title);
                    myChart.setOption({

                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },
                        legend: {
                            top: '5%',
                            right: 'center'
                        },
                        series: [
                            {
                                name: '课程销售量',
                                type: 'pie',
                                radius: ['15%', '30%'],
                                avoidLabelOverlap: false,
                                label: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    label: {
                                        show: true,
                                        fontSize: '40',
                                        fontWeight: 'bold'
                                    }
                                },
                                labelLine: {
                                    show: false
                                },
                                data: arr
                            }
                        ]

                    })
                })
            },


        }
    }
</script>
<style scoped>
    .el-card__body {
        padding: 20px;
        height: 485px;
    }
</style>
