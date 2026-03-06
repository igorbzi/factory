import { defineStore } from 'pinia'
import { ref } from 'vue'
import { optimizerService } from '../services/api'
import type { ProductionPlan } from '../types'

export const useOptimizerStore = defineStore('optimizer', () => {
  const plan    = ref<ProductionPlan | null>(null)
  const loading = ref<boolean>(false)
  const error   = ref<string | null>(null)

  async function optimize(): Promise<void> {
    loading.value = true
    error.value   = null
    plan.value    = null
    try {
      const { data } = await optimizerService.optimize()
      plan.value = data
    } catch {
      error.value = 'Optimization failed. Please try again.'
    } finally {
      loading.value = false
    }
  }

  return { plan, loading, error, optimize }
})