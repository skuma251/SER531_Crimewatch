<template>
  <div class="flex">
    <div class="w-1/2 p-4">
      <FirstCard />
    </div>
    <div class="w-1/2 p-4">
      <SecondCard />
    </div>
  </div>
</template>

<script setup lang="ts">
import FirstCard from "./FirstCard.vue";
import SecondCard from "./SecondCard.vue";
</script>
