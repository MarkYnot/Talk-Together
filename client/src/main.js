import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import 'ant-design-vue/dist/antd.css';
import VueLazyload from 'vue-lazyload'
import Antd from 'ant-design-vue';
import md5 from 'js-md5';
import  VueCookies from 'vue-cookies';

Vue.config.productionTip = false
Vue.use(VueCookies);
Vue.use(Antd);
Vue.use(VueLazyload);
Vue.prototype.$md5 = md5

new Vue({
  router,
  store,
  el: '#app',
  components: { App },
  template: '<App/>',
  render: h => h(App)
}).$mount('#app')
