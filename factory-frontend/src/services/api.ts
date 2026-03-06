import axios from 'axios'
import type { RawMaterial, RawMaterialForm } from '../types'
import type { Product, ProductForm } from '../types'
import type { ProductionPlan } from '../types'

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: { 'Content-Type': 'application/json' }
})

export const rawMaterialService = {
  findAll: ()                              => api.get<RawMaterial[]>('/raw-materials'),
  create:  (data: RawMaterialForm)         => api.post<RawMaterial>('/raw-materials', data),
  update:  (id: number, data: RawMaterialForm) => api.put<RawMaterial>(`/raw-materials/${id}`, data),
  delete:  (id: number)                    => api.delete(`/raw-materials/${id}`)
}

export const productService = {
  findAll: ()                          => api.get<Product[]>('/products'),
  create:  (data: ProductForm)         => api.post<Product>('/products', data),
  update:  (id: number, data: ProductForm) => api.put<Product>(`/products/${id}`, data),
  delete:  (id: number)                => api.delete(`/products/${id}`)
}

export const optimizerService = {
  optimize: () => api.get<ProductionPlan>('/production/optimize')
}