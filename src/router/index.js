import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/LoginView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
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
  {
<<<<<<< Updated upstream:src/router/index.js
    path: '/test',
    name: 'test',
    component: () => import('../views/TestView.vue')
=======
    path: '/homepage',
    name: 'homepage',
    component: () => import('../views/HomePage.vue')
>>>>>>> Stashed changes:Front_End_Conde/src/router/index.js
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
