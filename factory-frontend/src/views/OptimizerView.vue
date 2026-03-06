<template>
  <div class="container">

    <div class="optimizer-description">
      <p>
        Analyzes current stock and suggests which products to manufacture
        to achieve the <strong>highest total revenue</strong>.
        Products are prioritized using <strong>Integer Linear Programming</strong>,
        guaranteeing the globally optimal solution.
      </p>
      <Button label="Run Optimization" icon="pi pi-play"
              :loading="store.loading" @click="store.optimize()" />
    </div>

    <Message v-if="store.error" severity="error" style="margin-top:1rem">
      {{ store.error }}
    </Message>

    <template v-if="store.plan">
      <!-- Revenue Banner -->
      <div class="revenue-banner">
        <div>
          <div class="revenue-label">Maximum Total Revenue</div>
          <div class="revenue-value">{{ formatCurrency(store.plan.totalRevenue) }}</div>
        </div>
        <div class="revenue-tags">
          <Tag :value="`${store.plan.suggestions.length} product(s)`" severity="success" />
          <Tag :value="`${totalUnits} total units`" severity="info" />
        </div>
      </div>

      <!-- Empty -->
      <Message v-if="store.plan.suggestions.length === 0" severity="warn" style="margin-top:1rem">
        No products can be produced with the current stock. Please check your raw material levels.
      </Message>

      <!-- Suggestions Table -->
      <div v-else class="suggestions-card">
        <DataTable :value="store.plan.suggestions" stripedRows>
          <Column header="#" style="width:60px">
            <template #body="{ index }: { index: number }">
              <Badge :value="String(index + 1)" />
            </template>
          </Column>
          <Column field="productName" header="Product" />
          <Column field="unitPrice" header="Unit Price">
            <template #body="{ data }: { data: ProductionSuggestion }">
              {{ formatCurrency(data.unitPrice) }}
            </template>
          </Column>
          <Column field="quantity" header="Qty to Produce">
            <template #body="{ data }: { data: ProductionSuggestion }">
              <Tag :value="String(data.quantity)" severity="info" />
            </template>
          </Column>
          <Column field="totalRevenue" header="Revenue">
            <template #body="{ data }: { data: ProductionSuggestion }">
              <strong class="revenue-cell">{{ formatCurrency(data.totalRevenue) }}</strong>
            </template>
          </Column>
          <Column header="% of Total">
            <template #body="{ data }: { data: ProductionSuggestion }">
              <div class="progress-cell">
                <ProgressBar :value="revenuePercent(data.totalRevenue)"
                             style="height:8px; width:120px" :showValue="false" />
                <span>{{ revenuePercent(data.totalRevenue).toFixed(1) }}%</span>
              </div>
            </template>
          </Column>
        </DataTable>
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useOptimizerStore } from '../stores/optimizerStore'
import type { ProductionSuggestion } from '../types'
import Button from 'primevue/button'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Tag from 'primevue/tag'
import Badge from 'primevue/badge'
import ProgressBar from 'primevue/progressbar'
import Message from 'primevue/message'

const store = useOptimizerStore()

const totalUnits = computed<number>(() =>
  store.plan?.suggestions.reduce((sum, s) => sum + s.quantity, 0) ?? 0
)

const formatCurrency = (v: number): string =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v)

const revenuePercent = (revenue: number): number => {
  if (!store.plan?.totalRevenue || store.plan.totalRevenue === 0) return 0
  return (revenue / store.plan.totalRevenue) * 100
}
</script>

<style scoped>
.optimizer-description {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1.5rem;
  border-radius: 10px;
  padding: 1.25rem 1.5rem;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
}

.optimizer-description p {
  line-height: 1.6;
  max-width: 600px;
}

.revenue-banner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 1rem;
  background: #f0fdf4;
  border: 1px solid #bbf7d0;
  border-left: 4px solid #16a34a;
  border-radius: 10px;
  padding: 1.5rem 2rem;
  margin-bottom: 1.5rem;
}

.revenue-label {
  font-size: .8rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: .04em;
  color: #15803d;
}

.revenue-value {
  font-size: 2.5rem;
  font-weight: 800;
  color: #16a34a;
}

.revenue-tags {
  display: flex;
  gap: .5rem;
  flex-wrap: wrap;
}

.suggestions-card {
  background: #fff;
  border: 1px solid #e2e8f0;
  border-radius: 10px;
  overflow: hidden;
}

.revenue-cell { color: #16a34a; }

.progress-cell {
  display: flex;
  align-items: center;
  gap: .6rem;
  font-size: .8rem;
  color: #64748b;
}
</style>