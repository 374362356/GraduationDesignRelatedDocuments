import axios from 'axios'
import Vue from 'vue'

// axios.defaults.baseURL = 'https://www.liulongbin.top:8888/api/private/v1/'
axios.defaults.baseURL = 'http://localhost:8989/pzh'
axios.interceptors.request.use((config) => {
  config.headers.token = window.localStorage.getItem('token')
  return config
})
Vue.prototype.$axios = axios
