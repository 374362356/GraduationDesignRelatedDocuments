import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/components/Login.vue'
import Home from '@/components/Home.vue'
import Userlist from '@/components/Userlist.vue'
import Welcome from '@/components/Welcome.vue'
import Rights from '@/components/power/Rights'
import Roles from '@/components/power/Roles'
import Categories from '@/components/goods/Categories'
import Params from '@/components/goods/Params'
import Goods from '@/components/goods/Goods'
import  User from '@/components/User.vue'
Vue.use(Router)

export default new Router({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/users', component: Userlist },
        { path: '/roles', component: Roles },
        { path: '/rights', component: Rights },
        { path: '/categories', component: Categories },
        { path: '/params', component: Params },
        { path: '/goods', component: Goods },
        { path: '/user', component: User },
      ],
    },
  ],
})
