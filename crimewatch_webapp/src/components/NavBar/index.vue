<script setup lang="ts">
import { Icon } from "@iconify/vue";
</script>

<template>
  <nav class="navbar bg-primary min-h-fit h-12 flex items-center justify-between">
    <!-- Left-aligned content -->
    <div class="flex items-center">
      <span class="text-white font-custom text-xl">CrimeWatch</span>
      <router-link to="/">
        <div class="ml-4">
          <Icon icon="ic:baseline-home" class="text-white text-2xl" />
        </div>
      </router-link>
    </div>

    <!-- Center-aligned content -->
    <div class="flex items-left">
     <ul class="menu menu-horizontal">
           <li><a>Complaints</a></li>
           <li><a>Shooting</a></li>
           <li><a>Hate Crime</a></li>
         </ul>
    </div>

    <div class="flex items-center">
    </div>
  </nav>
</template>