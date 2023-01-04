<template>
    <div class="navMenu">

        <template v-for="navMenu in menuData">
            <!-- 最后一级菜单 -->
            <el-menu-item v-if="navMenu.children.length===0"
                          :index="navMenu.url"
            >
                <i :class="navMenu.icon"></i>
                <span slot="title">{{navMenu.pname}}</span>
            </el-menu-item>

            <!-- 此菜单下还有子菜单 -->
            <el-submenu v-if="navMenu.children.length!==0"
                        :index="navMenu.url">
                <template slot="title">
                    <i :class="navMenu.icon"></i>
                    <span> {{navMenu.pname}}</span>
                </template>
                <!-- 递归 -->
                <menuTree :menuData="navMenu.children"></menuTree>
            </el-submenu>
        </template>

    </div>
</template>

<script>

    export default {
        name: "menuTree",
        props: ["menuData"],
    }

</script>

<style scoped>

</style>
