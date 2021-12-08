import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("../views/Login.vue")
  },

  {
    path: '/profile',
    name: 'Profile',
    component: () => import("../views/Profile.vue")
  },
  {
    path: '/friendChat',
    name: 'friendChat',
    component: () => import("../views/ChatWithFriend.vue")
  },
  {
    path: '/chatroom',
    name: 'groupchat',
    component: () => import("../views/GroupChat")
  },
  {
    path: '/signin',
    name: 'Signin',
    component: () => import("../views/SignIn.vue")
  },
  {
    path: '/forget',
    name: 'forget',
    component: () => import("../views/forget.vue")
  }


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
