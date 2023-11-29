<template>
  <header
    class="navbar bg-primary min-h-fit h-12 flex items-center justify-between"
  >
    <div class="flex items-center">
      <router-link to="/" class="flex items-center text-white">
        <h1 class="text-xl">CrimeWatch</h1>
        <Icon icon="ic:baseline-home" class="ml-4 text-2xl" />
      </router-link>
    </div>
    <div class="hidden md:flex items-center justify-center flex-1">
      <nav class="menu menu-horizontal space-x-4 text-white">
        <li>
          <router-link to="/complaint" aria-current="page"
            >Complaints</router-link
          >
        </li>
        <li>
          <router-link to="/shooting">Shooting</router-link>
        </li>
        <li>
          <router-link to="/hate-crime">Hate Crime</router-link>
        </li>
        <li>
          <router-link to="/miscellaneous">Miscellaneous</router-link>
        </li>
      </nav>
    </div>
  </header>
</template>

<script setup lang="ts">
import { Icon } from "@iconify/vue";
</script>
