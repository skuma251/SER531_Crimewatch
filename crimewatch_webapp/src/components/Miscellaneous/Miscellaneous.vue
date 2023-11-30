<script setup lang="ts">
import { ref, onMounted } from "vue";
import Chart from 'chart.js/auto';
import { getShootingHateCrimeCountByYear } from "../../utils/api";
import { generateRandomColor } from "../../utils/index";

const planetChartCanvas = ref<HTMLCanvasElement | null>(null);
let planetChartKey = 0;
const planetChartData = ref<any>({
  type: "bar",
  data: {
    labels: ['Jan', 'Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
    datasets: [
      {
        label: "",
        type: "",
        data: [],
        backgroundColor: "",
        borderColor: "",
        borderWidth: 3
      },
      {
        label: "",
        type: "",
        data: [],
        backgroundColor: "",
        borderColor: "",
        borderWidth: 3
      }
    ]
  }
});

const isLoading = ref<boolean>(false);
const selectedYear = ref<number>();
const yearList = [2019,2020,2021,2022];

const fetchCountByYear = async (year: number) => {
  try {
    isLoading.value = true;
    const crimeCount = await getShootingHateCrimeCountByYear(year);
    isLoading.value = false;
    const labels = Object.keys(crimeCount);
    const monthlyCount = Object.values(crimeCount);
    planetChartData.value.data.datasets[0].label = labels[0];
    planetChartData.value.data.datasets[0].type = "line";
    planetChartData.value.data.datasets[0].data = monthlyCount[0];
    planetChartData.value.data.datasets[0].backgroundColor= generateRandomColor(),
    planetChartData.value.data.datasets[0].borderColor= generateRandomColor(),
    planetChartData.value.data.datasets[1].label = labels[1];
    planetChartData.value.data.datasets[1].type = "bar";
    planetChartData.value.data.datasets[1].data = monthlyCount[1];
    planetChartData.value.data.datasets[1].backgroundColor= generateRandomColor();
    planetChartData.value.data.datasets[1].borderColor = generateRandomColor();
    planetChartKey++;
    renderChart();
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

declare global {
  interface Window {
    myChart?: Chart;
  }
}

const renderChart = () => {
  if (planetChartCanvas.value) {
    const ctx = planetChartCanvas.value.getContext('2d');
    if (ctx) {
      if (window.myChart) {
        window.myChart.destroy(); 
      }
      window.myChart = new Chart(ctx, planetChartData.value);
    }
  }
};

onMounted(renderChart);

const fetchChart = async () => {
  try {
    // Initial selected year
    selectedYear.value = 2019;
    await fetchCountByYear(selectedYear.value);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};


fetchChart();
renderChart();
</script>

<template>
  <div class="flex justify-center items-center h-screen">
    <div class="card w-10/12 bg-base-100 shadow-xl">
      <div class="card-body">
        <div class="grid grid-cols-3 gap-5 w-full">
          <div>
            <label class="flex flex-col items-end text-black text-sm">
              Select year:
            </label>
          </div>
          <div>
            <select
              v-model="selectedYear"
              class="select select-xs select-bordered w-full"
            >
              <option
                v-for="year in yearList"
                :key="year"
                :value="year"
              class="items-center"
              >
                {{ year }}
              </option>
            </select>
          </div>
          <div class="flex items-center">
            <button class="btn btn-xs btn-primary" @click="fetchCountByYear(selectedYear!)">Load</button>
            <span v-if="isLoading" class="loading loading-spinner loading-lg"></span>
          </div>
        </div>
        <canvas class="w-full" ref="planetChartCanvas"></canvas>
      </div>
    </div>
  </div>
</template>
