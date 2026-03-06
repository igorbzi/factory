<template>
  <aside class="sidebar">

    <router-link to="/" class="sidebar-logo">
      <i class="pi pi-cog" />
      <span>FactoryOpt</span>
    </router-link>

    <nav class="sidebar-nav">
      <router-link to="/raw-materials" class="nav-item">
        <i class="pi pi-box" />
        <span>Raw Materials</span>
      </router-link>
      <router-link to="/products" class="nav-item">
        <i class="pi pi-tag" />
        <span>Products</span>
      </router-link>
      <router-link to="/optimizer" class="nav-item">
        <i class="pi pi-chart-bar" />
        <span>Optimizer</span>
      </router-link>
    </nav>

    <div class="sidebar-footer">
      <span>Factory Optimizer v1.0</span>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 240px;
  min-height: 100vh;
  background: #1e293b;
  display: flex;
  flex-direction: column;
  padding: 1.5rem 1rem;
  gap: 2rem;
  position: fixed;
  top: 0;
  left: 0;
}

.sidebar-logo {
  display: flex;
  align-items: center;
  gap: .75rem;
  padding: .5rem;
  color: #fff;
  font-size: 1.2rem;
  font-weight: 700;
  text-decoration: none;
  border-radius: 8px;
  transition: background .15s;
}

.sidebar-logo:hover {
  background: #334155;
}

.sidebar-logo i { font-size: 1.4rem; color: #2563eb; }

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: .25rem;
  flex: 1;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: .75rem;
  padding: .7rem 1rem;
  border-radius: 8px;
  color: #94a3b8;
  text-decoration: none;
  font-size: .9rem;
  font-weight: 500;
  transition: all .15s;
}

.nav-item i { font-size: 1rem; width: 18px; text-align: center; }
.nav-item:hover { background: #334155; color: #e2e8f0; }
.nav-item.router-link-active { background: #2563eb; color: #fff; }

.sidebar-footer {
  font-size: .75rem;
  color: #475569;
  text-align: center;
  padding-top: 1rem;
  border-top: 1px solid #334155;
}
</style>