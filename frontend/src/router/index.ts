import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/auth',
      name: 'login',
      children: [
        {
          path: 'login',
          component: () => import('../views/Auth/LoginView.vue'),
        },
        {
          path: 'signup',
          component: () => import('../views/Auth/SignUpView.vue'),
        },
      ],
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: () => import('../views/MyPageView.vue'),
    },
    {
      path: '/play',
      name: 'play',
      component: () => import('../views/PlayView.vue'),
    },
    {
      path: '/ranking',
      name: 'ranking',
      component: () => import('../views/RankingView.vue'),
    },
    {
      path: '/board',
      name: 'board',
      children: [
        {
          path: 'list',
          component: () => import('../views/Board/ListView.vue'),
        },
        {
          path: 'write',
          component: () => import('../views/Board/WriteView.vue'),
        },
        {
          path: ':id',
          component: () => import('../views/Board/DetailView.vue'),
        },
      ],
    },
  ],
})

export default router
