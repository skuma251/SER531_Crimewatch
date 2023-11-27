<template>
    <div class="flex">
      <div class="w-1/2 p-4">
        <LeftCard />
      </div>
      <div class="w-1/2 p-4">
        <RightCard />
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import LeftCard from "./LeftCard.vue";
  import RightCard from "./RightCard.vue";
  </script>
  