import { defineStore } from 'pinia'
import { ref } from 'vue'
import { productService } from '../services/api'
import type { Product, ProductForm } from '../types'

export const useProductStore = defineStore('product', () => {
  const products = ref<Product[]>([])
  const loading  = ref<boolean>(false)
  const error    = ref<string | null>(null)

  async function fetchAll(): Promise<void> {
    loading.value = true
    error.value   = null
    try {
      const { data } = await productService.findAll()
      products.value = data
    } catch {
      error.value = 'Failed to load products.'
    } finally {
      loading.value = false
    }
  }

  async function create(form: ProductForm): Promise<void> {
    const { data } = await productService.create(form)
    products.value.push(data)
  }

  async function update(id: number, form: ProductForm): Promise<void> {
    const { data } = await productService.update(id, form)
    const index = products.value.findIndex(p => p.id === id)
    if (index !== -1) products.value[index] = data
  }

  async function remove(id: number): Promise<void> {
    await productService.delete(id)
    products.value = products.value.filter(p => p.id !== id)
  }

  return { products, loading, error, fetchAll, create, update, remove }
})