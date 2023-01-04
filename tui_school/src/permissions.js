// 注册一个全局自定义指令 `v-focus`
import Vue from 'vue'
import $store from './vuex'
/**权限指令v-on v-bind v-text**/
/*id:表示指令的名称
* inserted: 当使用指令时；
* */
Vue.directive('has',{
    inserted:function (el,binding) {
        let className = binding.value; //这个地方要注意了，看下面页面上的代码，对应取得相应的值
        if (el.parentNode && !Vue.prototype.$_has(className)) {
            el.parentNode.removeChild(el); //
        }
    }
})
Vue.prototype.$_has = function (val) {//user::list
    let isShow = false;
    let btnPowerArr = sessionStorage.getItem("permissions");
    // let btnPowerArr = $store.getters.getPermission;
    if(btnPowerArr===undefined || btnPowerArr===null){
        return false;
    }
    if(btnPowerArr.indexOf(val) > -1){
        isShow = true;
    }
    return isShow;
}
