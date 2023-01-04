<template>
    <div class="main_box">
        <ul class="tabs clearfix">
            <a class="tab on">学习记录</a>
        </ul>
        <div class="main_cont">
            <div class="notdata" v-if="notdata">
                <i class="iconfont">&#xe6be;</i>暂时没有数据
            </div>
            <div v-else>
                <table class="course_table table" >
                    <thead>
                    <tr>
                        <th>课程名称</th>
                        <th>学习内容</th>
                        <th>学习时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in pageObj.tableData">
                        <td>{{item.title}}</td>
                        <td class="name">{{item.chapterTitle}}</td>
                        <td>{{item.playTimePoint}}</td>
                    </tr>
                    </tbody>
                </table>
                <el-pagination
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        layout="total, sizes, prev, pager, next, jumper"
                        :current-page="currentPage"
                        :page-size="pageCount"
                        :page-sizes="[5, 10, 20, 50, 100]"
                        :total="pageObj.totalCount">
                </el-pagination>
            </div>

        </div>
    </div>
</template>

<script>
    import DPage from '@/components/Page'
    export default {
        name: "LearningRecord",
        components: {
            DPage
        },
        data () {
            return {
                notdata: true,
                pageCurrent: 1,
                pageObj: {
                    tableData: [],
                    pageCurrent: '',
                    pageSize: '',
                    totalCount: 0,
                    totalPage: ''
                },
                currentPage:1,
                pageCount:5,

                formData:{

                }
            }
        },
        methods: {
            getPage (int) {
                this.pageCurrent = int
                this.getStudyList()
            },
            getStudyList () {
                var that = this
                // this.$http.post(`order/play-history/queryPlayHistoryBy`).then(function (resp) {
                this.$http.post(`/order/play-history/queryPlayHistoryBy/${this.currentPage}/${this.pageCount}/${sessionStorage.getItem("memberId")}`,this.formData).then(function (resp) {
                    that.pageObj.tableData = resp.data.result.records;
                    that.pageObj.totalCount = resp.data.result.total;
                    if (resp.data.result.records.length>0){
                        that.notdata =  false
                    }else {
                        that.notdata = true
                    }
                })
            },
            //分页查询
            handleSizeChange(val) {
                this.pageCount=val;
                this.getStudyList();
            },
            handleCurrentChange(val) {
                this.currentPage=val;
                this.getStudyList();
            },

        },
        mounted () {
            this.getStudyList()
        }
    }
</script>

<style scoped>
    .table td.name {
        text-align: center;
    }
</style>
