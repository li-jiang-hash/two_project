<template>
    <div class="homePage">
        <app-header></app-header>
        <div class="container account_cont clearfix">
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
    export default {
        name: "HomePage",
        components: {
            AppFooter,
            AppHeader,
        },
    }
</script>

<style lang="scss" rel="stylesheet/scss"  scoped>
    .person_body {
        width: 1200px;
        margin: 30px auto 10px;
        min-height: 1000px;
    }
    .account_cont{
        width: 100%;
        margin: 10px auto 10px;
    }

</style>