
import { createRouter, createWebHistory } from 'vue-router'
// import UserManage from '../views/UserManage.vue'
import LoginView from '../views/LoginView.vue'
// import LoginView from '../views/LoginView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/userManage',
    name: 'userManage',
    component: () => import('../views/UserManage.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/meeting',
    name: 'meeting',
    component: () => import('../views/MeetingManage.vue')
  },
  {
    path: '/userCenter',
    name: 'userCenter',
    component: () => import('../views/UserCenter.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
