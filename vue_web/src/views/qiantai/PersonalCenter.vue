<template>
    <div class="personalCenter">
        <app-header></app-header>
        <div class="container account_cont clearfix">
            <app-side></app-side>
            <div class="person_content">
                <router-view></router-view>
            </div>
        </div>
        <app-footer></app-footer>
    </div>
</template>

<script>
    import AppHeader from "@/components/common/header";
    import AppFooter from "@/components/common/footer";
    import AppSide from "@/components/account/Side";
    export default {
        name: "PersonalCenter",
        components: {
            AppFooter,
            AppHeader,
            AppSide,
        },
        methods: {
            showBind () {
                this.hideBind = false
            },
            closeBind () {
                this.hideBind = true
            }
        }
    }
</script>
<style lang="scss" rel="stylesheet/scss"  scoped>
    .person_body {
        width: 1200px;
        margin: 30px auto 10px;
        min-height: 1000px;
    }
    .account_cont{
        margin: 10px auto 10px;
    }
    .person_content {
        width: 1012px;
        float: right;
        background: #fff;
        border-radius: 6px;
    }
</style>