<template>
    <div>

    <div class="view-header">
      <Button label="New Material" icon="pi pi-plus" @click="openCreate" />
    </div>

    <Message v-if="store.error" severity="error">{{ store.error }}</Message>

      <DataTable :value="store.rawMaterials" :loading="store.loading"
           paginator :rows="10" :rowsPerPageOptions="[10, 25, 50]" stripedRows>
      <Column field="name"          header="Name" />
      <Column field="quantity" header="Stock" />
      <Column header="Actions"      style="width:140px">
        <template #body="{ data }: { data: RawMaterial }">
          <div class="action-buttons">
            <Button icon="pi pi-pencil" text rounded severity="info"  @click="openEdit(data)" />
            <Button icon="pi pi-trash"  text rounded severity="danger" @click="confirmDelete(data)" />
          </div>
        </template>
      </Column>
    </DataTable>

    <Dialog v-model:visible="showDialog"
            :header="editingId ? 'Edit Material' : 'New Material'"
            modal style="width:420px">
      <div class="form-grid">
        <div class="field">
          <label>Name *</label>
          <InputText v-model="form.name" placeholder="e.g. Steel" fluid />
        </div>
        <div class="field">
          <label>Stock Quantity *</label>
          <InputNumber v-model="form.quantity" :min="0"
                       :minFractionDigits="0" :maxFractionDigits="4" fluid />
        </div>
      </div>
      <template #footer>
        <Button label="Cancel" text @click="showDialog = false" />
        <Button label="Save" icon="pi pi-check" :loading="saving" @click="save" />
      </template>
    </Dialog>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRawMaterialStore } from '../stores/rawMaterialsStore'
import { useToast } from 'primevue/usetoast'
import { useConfirm } from 'primevue/useconfirm'
import type { RawMaterial, RawMaterialForm } from '../types'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import InputNumber from 'primevue/inputnumber'
import Message from 'primevue/message'

const store   = useRawMaterialStore()
const toast   = useToast()
const confirm = useConfirm()

const showDialog = ref<boolean>(false)
const editingId  = ref<number | null>(null)
const saving     = ref<boolean>(false)

const emptyForm = (): RawMaterialForm => ({ name: '', quantity: 0})
const form = ref<RawMaterialForm>(emptyForm())

onMounted(() => store.fetchAll())

function openCreate(): void {
  editingId.value  = null
  form.value       = emptyForm()
  showDialog.value = true
}

function openEdit(material: RawMaterial): void {
  editingId.value  = material.id
  form.value       = { name: material.name, quantity: material.quantity }
  showDialog.value = true
}

async function save(): Promise<void> {
  saving.value = true
  try {
    if (editingId.value) {
      await store.update(editingId.value, form.value)
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Material updated successfully', life: 3000 })
    } else {
      await store.create(form.value)
      toast.add({ severity: 'success', summary: 'Created', detail: 'Material created successfully', life: 3000 })
    }
    showDialog.value = false
  } catch (e: any) {
    toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Operation failed', life: 4000 })
  } finally {
    saving.value = false
  }
}

function confirmDelete(material: RawMaterial): void {
  confirm.require({
    message: `Are you sure you want to delete "${material.name}"?`,
    header: 'Confirm Delete',
    icon: 'pi pi-exclamation-triangle',
    rejectLabel: 'Cancel',
    acceptLabel: 'Delete',
    acceptClass: 'p-button-danger',
    accept: async () => {
      try {
        await store.remove(material.id)
        toast.add({ severity: 'success', summary: 'Deleted', detail: 'Material deleted successfully', life: 3000 })
      } catch (e: any) {
        toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Delete failed', life: 4000 })
      }
    }
  })
}
</script>

<style scoped>
.container { max-width: 80%; margin: 0 auto; padding: 2rem; }
.view-header { display: flex; justify-content: flex-end; margin-bottom: 1rem; }
.form-grid { display: flex; flex-direction: column; gap: 1rem; padding-top: .5rem; }
.field { display: flex; flex-direction: column; gap: .4rem; }
.field label { font-size: .875rem; font-weight: 500; }
.action-buttons { display: flex; gap: .25rem; }
</style>