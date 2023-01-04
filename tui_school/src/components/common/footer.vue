<template>
    <div class="i_footer">
        <div class="footer_content clearfix" v-if="list">
            <div class="footer_msg" v-for="(item, index) in list" :key="index">
                <ul>
                    <li class="m_header">{{item.navName}}</li>
                    <li class="m_row"></li>
                    <li v-for="(that, num) in item.websiteNavList" :key="num"><a :href="clientData.mainUrl+'/terrace/'+that.id">{{that.navName}}</a></li>
                </ul>
            </div>
            <div class="m_right" v-if="service">
                <div class="footer_msg">
                    <ul>
                        <li class="m_header">官方微信</li>
                        <li>
                            <img src="@/assets/image/ewm.jpg" alt="">
                        </li>
                    </ul>
                </div>
                <div class="footer_msg" v-if="service.weibo">
                    <ul>
                        <li class="m_header">官方微博</li>
                        <li>
                            <img :src="service.weibo" alt="">
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="friend_link" v-if="friendList && friendList.length">
            <ul class="clearfix">
                <li class="link_one">友情链接:</li>
                <li v-for="(item, index) in friendList" :key="index"><a :href="item.linkUrl" :target="item.linkTarget">{{item.linkName}}</a></li>
            </ul>
        </div>
        <div class="copyright" v-if="service">
            <span v-html="service.copyright"></span>
        </div>
        <div class="icp_num">
            <a href="http://www.doityun.com/" target="_blank" class="lingke_link">IT云提供计算服务</a>
            <span v-if="service.icp">&nbsp;|&nbsp;</span>
            <a href="http://www.miitbeian.gov.cn/" target="_blank" class="lingke_link">{{service.icp}}</a>
            <span v-if="service.prn">&nbsp;|&nbsp;</span>
            <a :href="'http://www.beian.gov.cn/portal/index'" target="_blank" v-if="service.prn" class="lingke_link"><img src="@/assets/image/prn_icon.png" class="prn_icon" alt="">&nbsp;{{service.prn}}</a>
        </div>
    </div>
</template>
<script>
    import YClassBlock from "../ClassBlock";
    export default {
        name: "app-footer",
        props: {
            hideTop: {
                type: Boolean
            },
            classList: {
                type: [Object, Array],
                default () {
                    return []
                }
            },
            height: {
                type: [String, Number],
                default: 456
            }
        },
        data () {
            return {
                clientData:  {
                    no: 'lingke', // 接口需要的机构号
                    id: 'lkb65617f842ad4c37895a733b8de43cbb', // 接口需要的clientId
                    name: '领课教育系统(开源版)-打造全行业都适用的在线教育系统', // 页面title
                    domain: 'roncoo.com', // cookie作用域
                    tokenName: 'OSTK', // cookie保存的名称
                    mainUrl: 'http://edu.os.roncoo.com' // 网站域名
                },
                service:
                    {
                        'copyright':'领课网络 版权所有 © 2015-2020',
                        'icp':'粤ICP备16009964号-1',
                        'prn':'粤公网安备 44010602005774号'
                    }
                ,
                list: [
                    {
                        'navName':'关于我们',
                        websiteNavList:[
                            {
                                'id':'1',
                                'navName':'联系我们'
                            },
                            {
                                'id':'2',
                                'navName':'公司简介'
                            }
                        ]
                    },
                    {
                        'navName':'商务合作',
                        websiteNavList:[
                            {
                                'id':'3',
                                'navName':'商业版购买'
                            }
                        ]
                    },
                    {
                        'navName':'常见问题',
                        websiteNavList:[
                            {
                                'id':'4',
                                'navName':'如何购买'
                            }
                        ]
                    },

                ],
                friendList: [
                    {
                        'linkName':'领课网络',
                        'linkUrl':'https://www.roncoo.net/',
                        'linkTarget':'_blank'
                    },
                    {
                        'linkName':'龙果学院',
                        'linkUrl':'https://www.roncoo.net/',
                        'linkTarget':'_blank'
                    },
                    {
                        'linkName':'IT云',
                        'linkUrl':'https://www.roncoo.net/',
                        'linkTarget':'_blank'
                    },
                ]
            }
        },
        methods: {}
    }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
    .i_footer {
        background: rgb(51, 51, 51);
        padding-top: 60px;
        .footer_content {
            width: 1200px;
            margin: 0 auto;
            padding-bottom: 30px;
            border-bottom: 1px solid rgb(102, 102, 102);
        }
        .footer_msg {
            float: left;
            width: 160px;
            img {
                width: 96px;
                height: 96px;
            }
            li {
                color: #ccc;
                font-size: 14px;
                margin-bottom: 20px;
                a {
                    color: #ccc;
                    &:hover {
                        text-decoration: none;
                        color: #fff;
                    }
                }
                &.m_header {
                    color: #fff;
                    font-size: 18px;
                }
                &.m_row {
                    width: 18px;
                    height: 3px;
                    background: #ccc;
                    margin-bottom: 25px;
                }
            }
        }
        .m_right {
            float: right;
            .big_phone {
                color: #fff;
                font-size: 18px;
                margin-left: 56px;
            }
            .small_phone {
                color: #fff;
                font-size: 14px;
                margin-top: 5px;
                margin-left: 56px;
            }
        }
        .m_phone {
            position: relative;
            img {
                width: 46px;
                height: 43px;
                position: absolute;
                top: 0;
                left: 0;
            }
        }
        .qq_btn {
            margin-top: 10px;
            position: relative;
            img {
                width: 11px;
                height: 13px;
                position: absolute;
                top: 12px;
                left: 25px;
            }
            a {
                display: block;
                width: 113px;
                height: 36px;
                padding-left: 15px;
                background: rgb(213, 20, 35);
                line-height: 36px;
                color: #fff;
                border-radius: 5px;
                text-align: center;
                &:hover {
                    text-decoration: none;
                    color: #fff;
                }
            }
        }
        .m_test {
            width: 262px;
            height: 30px;
            line-height: 30px;
            // background: rgb(34, 34, 34);
            color: #ccc;
            font-size: 14px;
            padding-left: 10px;
            margin-top: 8px;
            img {
                width: 19px;
                height: 14px;
                position: relative;
                top: 2px;
            }
        }
        .friend_link {
            width: 1200px;
            margin: 25px auto 0px;
            li {
                float: left;
                font-size: 14px;
                &.link_one {
                    color: #fff;
                    margin-right: 20px;
                }
                a {
                    color: #ccc;
                    margin: 0px 10px;
                    &:hover {
                        color: #fff;
                        text-decoration: none;
                    }
                }
            }
        }
        .copyright {
            background: rgb(102, 102, 102);
            height: 30px;
            line-height: 30px;
            text-align: center;
            color: #ccc;
            margin-top: 25px;
            a {
                color: #ccc;
            }
        }
        .icp_num {
            line-height: 30px;
            text-align: center;
            color: #ccc;
            .lingke_link {
                color: #ccc;
                &:hover {
                    text-decoration: underline;
                }
            }
        }
        .prn_icon {
            width: 16px;
            height: 16px;
            position: relative;
            top: 3px;
        }
    }
</style>