import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/components/Login.vue'
import Home from '@/components/Home.vue'
import Userlist from '@/components/Userlist.vue'
import Welcome from '@/components/Welcome.vue'
import Right from '@/components/power/Right'
import Roles from '@/components/power/Roles'
import Suggestion from '@/components/information/Suggestion'
import News from '@/components/information/News.vue'
import Conference from '@/components/information/Conference'
import User from '@/components/User.vue'
import Staff from "../components/order/Staff"
import Person from '../components/person/person';
import Reports from '@/components/information/Reports'
import Reports2 from '@/components/information/Reports2'
import Upload from "../components/order/Upload"
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
        { path:'/person', component: Person},
        { path: '/roles', component: Roles },
        { path: '/rights', component: Right },
        { path: '/suggestion', component: Suggestion },
        { path: '/news', component: News },
        { path: '/conference', component: Conference },
        { path: '/user', component: User },
        { path: '/staff',component: Staff},
        { path: '/reports',component: Reports},
        { path: '/reports2',component: Reports2},
        { path: '/upload',component: Upload}
      ],
    },
  ],
})
