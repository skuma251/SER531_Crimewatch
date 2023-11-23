<template>
  <div class="w-full h-full">
    <canvas id="chart"></canvas>
  </div>
</template>

<script setup lang="ts">
import Chart from "chart.js/auto";
import { onMounted } from "vue";
import {
  SupportedChartType,
  BarChartData,
  PieChartData,
  LineChartData,
} from "../../utils/types";

const props = defineProps<{
  type: SupportedChartType;
  data: BarChartData | PieChartData | LineChartData;
}>();

onMounted(() => {
  const chrt = document.getElementById("chart") as HTMLCanvasElement;
  const chart = new Chart(chrt, {
    type: props.type,
    data: props.data,
  });
  chart;
});
</script>
