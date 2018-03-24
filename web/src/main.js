// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import MintUI from 'mint-ui'
import 'babel-polyfill'
import 'mint-ui/lib/style.css'
import { get, put, post, del, patch } from './api/api'
Vue.prototype.get = get
Vue.prototype.put = put
Vue.prototype.post = post
Vue.prototype.del = del
Vue.prototype.patch = patch

Vue.config.productionTip = false
Vue.use(MintUI)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
