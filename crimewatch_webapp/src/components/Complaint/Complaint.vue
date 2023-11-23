<script setup lang="ts">
import FirstChart from "./FirstChart.vue";
import SecondChart from "./SecondChart.vue";
</script>

<template>
  <div :style="{ height: `calc(100vh - 3rem)` }" class="flex p-4 gap-2">
    <FirstChart />
    <SecondChart />
  </div>
</template>
