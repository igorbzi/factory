<template>
  <div class="container">

    <div class="view-header">
      <Button label="New Product" icon="pi pi-plus" @click="openCreate" />
    </div>

    <Message v-if="store.error" severity="error">{{ store.error }}</Message>

    <DataTable :value="store.products" :loading="store.loading"
               paginator :rows="10" stripedRows>
      <Column field="name"  header="Name" />
      <Column field="price" header="Price">
        <template #body="{ data }: { data: Product }">
          {{ formatCurrency(data.price) }}
        </template>
      </Column>
      <Column header="Ingredients">
        <template #body="{ data }: { data: Product }">
          <div class="tag-list">
            <Tag v-for="i in data.rawMaterials" :key="i.id"
                 :value="`${i.rawMaterial.name}: ${i.quantity}`"
                 severity="secondary" />
          </div>
        </template>
      </Column>
      <Column header="Actions" style="width:140px">
        <template #body="{ data }: { data: Product }">
          <div class="action-buttons">
            <Button icon="pi pi-pencil" text rounded severity="info"  @click="openEdit(data)" />
            <Button icon="pi pi-trash"  text rounded severity="danger" @click="confirmDelete(data)" />
          </div>
        </template>
      </Column>
    </DataTable>

    <Dialog v-model:visible="showDialog"
            :header="editingId ? 'Edit Product' : 'New Product'"
            modal style="width:500px">
      <div class="form-grid">
        <div class="field">
          <label>Name *</label>
          <InputText v-model="form.name" placeholder="e.g. Steel Chair" fluid />
        </div>
        <div class="field">
          <label>Price (R$) *</label>
          <InputNumber v-model="form.price" mode="currency"
                       currency="BRL" locale="pt-BR" fluid />
        </div>

        <Divider />

        <div class="ingredients-header">
          <label>Ingredients</label>
          <Button label="Add" icon="pi pi-plus" text size="small" @click="addIngredient" />
        </div>

        <Message v-if="form.rawMaterials.length === 0" severity="info" size="small">
          Add at least one ingredient.
        </Message>

        <div v-for="(ing, idx) in form.rawMaterials" :key="idx" class="ingredient-row">
          <Select v-model="ing.rawMaterialId"
                  :options="rawMaterialStore.rawMaterials"
                  optionLabel="name"
                  optionValue="id"
                  placeholder="Select material"
                  class="ingredient-select" />
          <InputNumber v-model="ing.quantity"
                       :min="0.0001"
                       :minFractionDigits="1"
                       placeholder="Qty"
                       class="ingredient-qty" />
          <Button icon="pi pi-times" text rounded severity="danger"
                  @click="removeIngredient(idx)" />
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
import { useProductStore } from '../stores/productsStore'
import { useRawMaterialStore } from '../stores/rawMaterialsStore'
import { useToast } from 'primevue/usetoast'
import { useConfirm } from 'primevue/useconfirm'
import type { Product, ProductForm } from '../types'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import InputNumber from 'primevue/inputnumber'
import Select from 'primevue/select'
import Tag from 'primevue/tag'
import Divider from 'primevue/divider'
import Message from 'primevue/message'

const store            = useProductStore()
const rawMaterialStore = useRawMaterialStore()
const toast            = useToast()
const confirm          = useConfirm()

const showDialog = ref<boolean>(false)
const editingId  = ref<number | null>(null)
const saving     = ref<boolean>(false)

const emptyForm = (): ProductForm => ({ name: '', price: 0, rawMaterials: [] })
const form = ref<ProductForm>(emptyForm())

const formatCurrency = (v: number): string =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v)

onMounted(async () => {
  await store.fetchAll()
  await rawMaterialStore.fetchAll()
})

function openCreate(): void {
  editingId.value  = null
  form.value       = emptyForm()
  showDialog.value = true
}

function openEdit(product: Product): void {
  editingId.value  = product.id
  form.value       = {
    name: product.name,
    price: product.price,
    rawMaterials: product.rawMaterials.map(i => ({
      rawMaterialId: i.rawMaterial.id,
      quantity: i.quantity
    }))
  }
  showDialog.value = true
}

function addIngredient(): void {
  form.value.rawMaterials.push({ rawMaterialId: null, quantity: 0 })
}

function removeIngredient(idx: number): void {
  form.value.rawMaterials.splice(idx, 1)
}

async function save(): Promise<void> {
  saving.value = true
  try {
    if (editingId.value) {
      await store.update(editingId.value, form.value)
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Product updated successfully', life: 3000 })
    } else {
      await store.create(form.value)
      toast.add({ severity: 'success', summary: 'Created', detail: 'Product created successfully', life: 3000 })
    }
    showDialog.value = false
  } catch (e: any) {
    toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Operation failed', life: 4000 })
  } finally {
    saving.value = false
  }
}

function confirmDelete(product: Product): void {
  confirm.require({
    message: `Are you sure you want to delete "${product.name}"?`,
    header: 'Confirm Delete',
    icon: 'pi pi-exclamation-triangle',
    rejectLabel: 'Cancel',
    acceptLabel: 'Delete',
    acceptClass: 'p-button-danger',
    accept: async () => {
      try {
        await store.remove(product.id)
        toast.add({ severity: 'success', summary: 'Deleted', detail: 'Product deleted successfully', life: 3000 })
      } catch (e: any) {
        toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Delete failed', life: 4000 })
      }
    }
  })
}
</script>

<style scoped>
.tag-list { display: flex; flex-wrap: wrap; gap: .35rem; }
.view-header { display: flex; justify-content: flex-end; margin-bottom: 1rem; }
.ingredients-header { display: flex; align-items: center; justify-content: space-between; }
.ingredient-row { display: flex; align-items: center; gap: .75rem; }
.ingredient-select { flex: 2; }
.ingredient-qty { flex: 1; }
</style>