<template>
  <div class="home-container">
    <div class="home-card">
      <div class="home-icon">
        <i class="pi pi-cog" />
      </div>
      <h1 class="home-title">Factory Optimizer</h1>
      <p class="home-description">
        Manage your raw materials and products, and find the best production
        plan to maximize your revenue.
      </p>
      <div class="home-actions">
        <Button label="Raw Materials" icon="pi pi-box"
                @click="router.push('/raw-materials')" />
        <Button label="Products" icon="pi pi-tag"
                @click="router.push('/products')" />
        <Button label="Run Optimizer" icon="pi pi-chart-bar"
                @click="router.push('/optimizer')" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import Button from 'primevue/button'

const router = useRouter()
</script>

<style scoped>
.home-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 65px);
}

.home-card {
  background: #fff;
  border: 1px solid #e2e8f0;
  border-radius: 16px;
  padding: 3rem;
  text-align: center;
  max-width: 520px;
  width: 100%;
  box-shadow: 0 4px 24px rgba(0,0,0,.06);
}

.home-icon {
  width: 64px;
  height: 64px;
  background: #eff6ff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 1.5rem;
}

.home-icon i {
  font-size: 1.75rem;
  color: #2563eb;
}

.home-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: .75rem;
}

.home-description {
  color: #64748b;
  line-height: 1.6;
  margin-bottom: 2rem;
}

.home-actions {
  display: flex;
  gap: .75rem;
  justify-content: center;
  flex-wrap: wrap;
}
</style>