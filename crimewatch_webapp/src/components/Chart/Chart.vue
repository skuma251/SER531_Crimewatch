<template>
  <div>
    <canvas ref="chartCanvas" :id="chartId"></canvas>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, onBeforeUnmount, defineProps } from "vue";
import * as ChartJs from "chart.js/auto";
import { BarChartData, PieChartData, LineChartData, PolarAreaChart } from "../../utils/types";

const props = defineProps<{
  chartType: string;
  chartData: LineChartData | BarChartData | PieChartData | PolarAreaChart | undefined;
}>();

const chartId = "chart";
const chartCanvas = ref<HTMLCanvasElement | null>(null);
let chartInstance: ChartJs.Chart | null = null;

const renderChart = () => {
  const ctx = chartCanvas.value?.getContext("2d");
  if (ctx && props.chartData) {
    chartInstance = new ChartJs.Chart(ctx, {
      type: props.chartType as ChartJs.ChartType,
      data: props.chartData,
      options: {},
    });
  }
};

onMounted(renderChart);

watch(
  () => props.chartData,
  () => {
    if (chartInstance) {
      chartInstance.destroy();
      renderChart();
    }
  }
);

onBeforeUnmount(() => {
  if (chartInstance) {
    chartInstance.destroy();
    chartInstance = null;
  }
});
</script>
