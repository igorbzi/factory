import { createRouter, createWebHistory } from 'vue-router'
import RawMaterialsView from '../views/RawMaterialView.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/raw-materials' },
    { path: '/raw-materials', component: RawMaterialsView, meta: { title: 'Raw Materials' } }
  ]
})