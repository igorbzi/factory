import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RawMaterialsView from '../views/RawMaterialsView.vue'
import ProductsView from '../views/ProductsView.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/',              component: HomeView,          meta: { title: 'Home' } },
    { path: '/raw-materials', component: RawMaterialsView,  meta: { title: 'Raw Materials' } },
    { path: '/products', component: ProductsView, meta: { title: 'Products' } }
  ]
})



