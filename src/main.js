import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './axios'

import './assets/css/global.css'
import './assets/fonts/iconfont.css'

import VueKindEditor from './plugin/kindeditor.js'
import '../static/kindeditor/themes/default/default.css'
import '../static/kindeditor/kindeditor-all-min.js'
import '../static/kindeditor/lang/zh-CN.js'

import * as echarts from 'echarts';
Vue.use(VueKindEditor)

Vue.use(ElementUI)
Vue.config.productionTip = false

new Vue({
  el:"#app",
  router,
  render: h => h(App),
}).$mount('#app')
