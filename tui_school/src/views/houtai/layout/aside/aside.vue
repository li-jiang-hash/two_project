<template>
    <div class="aside">
        <el-aside id="asideNav">
            <div class="logo-name">
                <p>菜先生-后台</p>
            </div>
            <el-menu :unique-opened="true" background-color="#5e6d82" text-color="#fff" active-text-color="#ffd04b"
                :default-active="activeIndex" router>
                <menuTree :menuData="menuData"></menuTree>
            </el-menu>
        </el-aside>
    </div>
</template>

<script>
import menuTree from "./menuTree"
import routes from '../../../../router'

export default {
    name: "asideNav",
    components: {
        menuTree
    },
    data() {
        return {
            //菜单
            menuData: [],
            activeIndex: 'aa',
        }
    },
    created() { // $(function(){ //页面加载完毕后执行该函数。 })
        this.initMenu();
    },
    watch: {
        /*监听路径的变化*/
        "$route.path": 'selectmenu'
    },
    methods: {
        //查找对应的路径地址
        selectmenu(key, indexpath) {
            let name = "";
            //递归
            let navTitle = function (path, routerARR) {
                for (let i = 0; i < routerARR.length; i++) {

                    //如果子菜单的路由个数大于0（存在子）或者当是前路径，就执行
                    if (routerARR[i].children.length > 0 || routerARR[i].path === path) {

                        //为当前路径，不存在子
                        if (routerARR[i].path === path && routerARR[i].children.length < 1) {

                            //将当前的路径赋值给name
                            name = routerARR[i].name;
                            break
                        }
                        //递归调用方法，给路径赋值
                        navTitle(path, routerARR[i].children)
                    }
                }
                return name
            }
            //dispatch含有异步操作，，作用为传值给vuex的mutation改变state
            this.$store.dispatch("addTab", {
                title: navTitle(key, routes.options.routes),
                path: key
            })
        },

        //初始化侧边导航栏，，获取所有的菜单
        initMenu() {

            const that = this;
            this.$http.get("/syssystem/e-permission/leftMenus").then(function (resp) {
                that.menuData = resp.data.data;
                console.log(that.menuData)
            });
        },
    }
}
</script>

<style lang="scss">
$top: top;
$bottom: bottom;
$left: left;
$right: right;

%w100 {
    width: 100%;
}

%h100 {
    height: 100%;
}

%cursor {
    cursor: pointer;
}

@mixin set-value($side, $value) {
    @each $prop in $leftright {
        #{$side}-#{$prop}: $value;
    }
}

.aside {
    width: 200px;
    background-color: #0080c9;
    overflow: hidden;
}

.el-aside {
    height: 100%;
    overflow-x: hidden;
    overflow-y: hidden;
}

#asideNav {
    width: auto !important;
    display: flex;
    flex-direction: column;
    border-right: solid 1px #e6e6e6;

    .logo-name {
        background-color: #03152A !important;

        width: 200px;
        font-weight: 300;
        z-index: 999;

        p {
            margin-right: 60px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            font-size: 16px;
            color: #5e6d82;
        }
    }

    .el-menu-vertical:not(.el-menu--collapse) {
        width: 200px;
        @extend %h100;
        overflow-y: scroll;
        overflow-x: hidden;
    }

    .el-menu {
        flex: 1;
        overflow: inherit;
        border-right: none;

        &::-webkit-scrollbar {
            display: none;
        }

        .fa {
            vertical-align: middle;
            margin-right: 5px;
            width: 24px;
            text-align: center;
            font-size: 18px;
        }

        .el-menu-item {
            background-color: #020f1d !important;
            border-bottom: 1px solid #020f1d;

            &:hover {
                color: #ffffff !important;
                background-color: #375573 !important;
            }
        }

        .el-menu-item.is-active {
            background-color: #56a9ff !important
        }

        .is-opened>.el-submenu__title>.el-icon-arrow-down {
            color: #ffffff;
            font-weight: 500;
            font-size: 18px;
        }
    }

}
</style>
