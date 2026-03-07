import { defineStore } from 'pinia'
import { ref } from 'vue'
import { measurementUnitService } from '../services/api'
import type { MeasurementUnit } from '../types'

export const useMeasurementUnitStore = defineStore('measurementUnit', () => {
  const units = ref<MeasurementUnit[]>([])

  async function fetchAll(): Promise<void> {
    const { data } = await measurementUnitService.findAll()
    units.value = data
  }

  return { units, fetchAll }
})