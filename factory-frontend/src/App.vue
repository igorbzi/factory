<template>
  <div class="app-layout">
    <AppSidebar />
    <div class="main-wrapper">
      <header class="app-header">
        <h1 class="header-title">{{ route.meta.title as string }}</h1>
      </header>
      <main class="main-content">
        <router-view />
      </main>
    </div>
  </div>
  <Toast />
  <ConfirmDialog />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import AppSidebar from './components/AppSidebar.vue'
import Toast from 'primevue/toast'
import ConfirmDialog from 'primevue/confirmdialog'

const route = useRoute()
</script>

<style>

.app-layout {
  display: flex;
  height: 100vh;
}

.main-wrapper {
  margin-left: 240px;
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100vh;
}

</style>