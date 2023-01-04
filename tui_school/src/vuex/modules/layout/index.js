/**
 * Created by WebStorm.
 * User: nirongxu
 * Date: 2018/12/10
 * Description: 文件描述
 */
export default {
  state: {
    isCollapse: false,
    logoShow: false,
    uniquerouter: true,
    rightNav: {},
    tabnavBox: JSON.parse(sessionStorage.getItem("addTab")) || [{
      title: "首页",
      path: "/dashboard"
    }]
  },
  mutations: {
    /*添加一个的菜单*/
    addTab (state, arg) {
      state.isActive = arg.path
      if (state.tabnavBox[0] && state.tabnavBox[0].title !== "首页") {
        state.tabnavBox.unshift({
          title: "首页",
          path: "/dashboard"
        })
      }

      for (let i = 0; i < state.tabnavBox.length; i++) {
        if (state.tabnavBox[i].path === arg.path) {
          return false
        }
      }
      state.tabnavBox.push({
        title: arg.title,
        path: arg.path
      })

      sessionStorage.setItem("addTab", JSON.stringify(state.tabnavBox))
    },
    /*头部开启一个新的菜单*/
    openMenu (state, arg) {
      state.rightNav = arg
    },
    /*头部右键移除当前的菜单*/
    removeTab (state, arg) {
      let index = state.tabnavBox.findIndex(function (value, key) {
        return value.path === arg.tabItem.path
      })
      state.tabnavBox.splice(index, 1)
      if (arg.tabItem.path === arg.fullPath) {
        let tabActive = state.tabnavBox[index] || state.tabnavBox[index - 1]
        arg.router.push(tabActive.path)
      }
      sessionStorage.setItem("addTab", JSON.stringify(state.tabnavBox))
    },
    /*头部右键移除其他的菜单*/
    removeOtherTab (state, arg,p) {
      state.tabnavBox = [{
        title: "首页",
        path: "/dashboard"
      }]
      if (!p){
        if (arg.all) {
          arg.router.push("/dashboard")
          return false
        }
      }

      state.tabnavBox.push(arg.tabItem)
      arg.router.push(arg.tabItem.path)
      sessionStorage.setItem("addTab", JSON.stringify(state.tabnavBox))
    },

    collapse (state, arg) {
      state.isCollapse = !state.isCollapse
      if (state.logoShow) {
        setTimeout(function () {
          state.logoShow = false
        }, 300)
      } else {
        state.logoShow = true
      }
    }
  },
  actions: {
    addTab ({commit}, arg) {
      commit("addTab", arg)
    },
    openMenu ({commit}, arg) {
      commit("openMenu", arg)
    },
    removeTab ({commit}, arg) {
      commit("removeTab", arg)
    },
    removeOtherTab ({commit}, arg) {
      commit("removeOtherTab", arg)
    },
    collapse ({commit}, arg) {
      commit("collapse", arg)
    }
  }
}
