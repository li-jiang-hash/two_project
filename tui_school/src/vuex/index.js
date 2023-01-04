import Vue from "vue"
import Vuex from "vuex"
// import Cookies from "js-cookie"
// import routerData from "./modules/routerData"
// import role from "./modules/role"
import layout from "./modules/layout/index"
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    permissions:[]
  },
  mutations: {
    setPermissions(state,args){
        // state.permissions.push(args);
        state.permissions=args;
    }
  },
  actions: {

  },
  getters: {
    getPermission(state){
        return state.permissions;
    },
    tabnavBox: state => state.layout.tabnavBox,
    visible: state => state.layout.visible,
    left: state => state.layout.left,
    top: state => state.layout.top,
    rightNav: state => state.layout.rightNav
  },
  modules: {
    // routerData,
    // role,
    layout
  }
})

export default store
