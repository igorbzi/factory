import { defineStore } from 'pinia'
import { ref } from 'vue'
import { rawMaterialService } from '../services/api'
import type { RawMaterial, RawMaterialForm } from '../types'

export const useRawMaterialStore = defineStore('rawMaterial', () => {
  const rawMaterials = ref<RawMaterial[]>([])
  const loading      = ref<boolean>(false)
  const error        = ref<string | null>(null)

  async function fetchAll(): Promise<void> {
    loading.value = true
    error.value   = null
    try {
      const { data } = await rawMaterialService.findAll()
      rawMaterials.value = data
    } catch {
      error.value = 'Failed to load raw materials.'
    } finally {
      loading.value = false
    }
  }

  async function create(form: RawMaterialForm): Promise<void> {
    const { data } = await rawMaterialService.create(form)
    rawMaterials.value.push(data)
  }

  async function update(id: number, form: RawMaterialForm): Promise<void> {
    const { data } = await rawMaterialService.update(id, form)
    const index = rawMaterials.value.findIndex(m => m.id === id)
    if (index !== -1) rawMaterials.value[index] = data
  }

  async function remove(id: number): Promise<void> {
    await rawMaterialService.delete(id)
    rawMaterials.value = rawMaterials.value.filter(m => m.id !== id)
  }

  return { rawMaterials, loading, error, fetchAll, create, update, remove }
})