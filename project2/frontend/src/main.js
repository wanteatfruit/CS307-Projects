// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
// import TableFor from 'vue-table-for'
// import VueTableDynamic from 'vue-table-dynamic'
// Vue.use(VueTableDynamic)
import VueGoodTablePlugin from 'vue-good-table';

// import the styles
import 'vue-good-table/dist/vue-good-table.css'

Vue.use(VueGoodTablePlugin);
axios.defaults.baseURL= 'http://localhost:8443/api'
Vue.prototype.$axios=axios
Vue.prototype.$ajax=axios
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
