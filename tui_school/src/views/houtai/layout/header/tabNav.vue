<template>
     <div>
         <div class="tabnavBox">
             <transition-group name="list" tag="ul">
                 <li v-for="(item, index) in $store.getters.tabnavBox" @contextmenu.prevent="openMenu(item,$event,index)"
                     :key="item.title" class="tabnav" :class="{ active: $route.path === item.path }">
                     <router-link :to="item.path">{{item.title}}</router-link>
                     <i @click="removeTab(item)" class="el-icon-error" v-if="index !== 0"></i>
                 </li>
             </transition-group>
         </div>
         <ul v-show="this.rightMenuShow" :style="{left:this.left+'px',top:this.top+'px'}" class="menuBox">
             <li @click="removeTab($store.getters.rightNav)"><i class="fa fa-remove"></i>关闭</li>
             <li @click="removeOtherTab($store.getters.rightNav)">关闭其他</li>
             <li @click="removeAllTab">关闭所有</li>
         </ul>
     </div>
</template>

<script>


    export default {
        name: "tabNav",
        data() {
            return {
                rightMenuShow: false,
                left: 0,
                top: 0,
            }
        },

        methods: {
            //点击右键时，显示关闭的方法选项框
            openMenu(item, e, index) {
                if (index === 0) {
                    return false
                }
                this.rightMenuShow = true
                this.left = e.clientX + 10
                this.top = e.clientY
                this.$store.dispatch("openMenu", item)
            },
            removeTab(tabItem) {//关闭表头的操作
                this.$store.dispatch("removeTab", {tabItem, fullPath: this.$route.path, router: this.$router})
            },
            removeOtherTab(tabItem) {//关闭其他的导航头
                this.$store.dispatch("removeOtherTab", {tabItem, router: this.$router})
            },
            removeAllTab() {//关闭所有的导航头
                this.$store.dispatch("removeOtherTab", {all: true, router: this.$router})
            }
        },
        watch: {
            //监听rightMenuShow方法的使用
            rightMenuShow(value) {
                if (value) {
                    document.body.addEventListener("click", () => {
                        this.rightMenuShow = false
                    })
                } else {
                    document.body.removeEventListener("click", () => {
                        this.rightMenuShow = false
                    })
                }
            }
        }
    }
</script>
<style>
    .tabnav {
        display: inline-block;
        transition: all 0.5s;
    }

    .list-enter, .list-leave-to {
        opacity: 0;
        transform: translateY(30px);

    }

    .list-enter-active {
        transition: all 0.5s;
    }

    .list-leave-active {
        position: absolute;
        transition: all 1s;
    }
</style>
<style lang="scss">
    $top: top;
    $bottom: bottom;
    $left: left;
    $right: right;
    $leftright: ($left, $right);

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

    .tabnavBox {
        @extend %w100;
        height: 42px;
        min-height: 42px;
        overflow: hidden;
        border-#{$top}: 1px solid #f6f6f6;
        border-#{$bottom}: 2px solid #d8dce5;
        /*box-shadow: white;*/
        -webkit-box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
        ul {
            display: flex;
            justify-content: flex-start;
            padding-#{$left}: 20px;
            flex-wrap: nowrap;
            overflow-x: auto;
            li {
                height: 30px;
                line-height: 31px;
                display: flex;
                align-items: center;
                @extend %cursor;
                margin-#{$top}: 6px;
                margin-#{$right}: 5px;

                border: 1px solid #cccccc;

                overflow: hidden;
                &:not(:first-child) {
                    padding-#{$right}: 10px;
                    min-width: 80px;
                }
                a {
                    @include set-value(padding, 13px);
                    display: inline-block;
                    @extend %h100;
                    font-size: 12px;
                    color: #999999;
                }
                &:nth-child(n+2) {
                    a {
                        padding-#{$right}: 15px;
                    }
                }
                i {
                    @extend %cursor;
                    &:hover {
                        color: red;
                    }
                }
            }
            li.active {
                background: #409eff;
                color: #ffffff;
                a {
                    color: #ffffff;
                }
            }
        }
    }

    .menuBox {
        margin: 0;
        background: #fff;
        z-index: 999;
        position: absolute;
        padding: 5px 0;
        border: 1px solid #cccccc;
        font-size: 12px;
        font-weight: 400;
        color: #333;
        box-shadow: 2px 1px 6px 0 rgba(0, 0, 0, .3);
        li {
            margin: 0;
            padding: 7px 16px;
            @extend %cursor;
            &:hover {
                background: #e1e6ea;
            }
        }
    }
</style>
