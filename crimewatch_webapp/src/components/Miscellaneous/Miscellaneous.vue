<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import {
  getYearsShooting,
} from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import Chart from 'chart.js/auto';
  
const planetChartCanvas = ref<HTMLCanvasElement | null>(null);
const planetChartData = {
    type: "bar",
    data: {
      labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"],
      datasets: [
        {
          label: "Number of Moons",
          type: "line",
          data: [0, 0, 1, 2, 79, 82, 27, 14],
          backgroundColor: "rgba(54,73,93,.5)",
          borderColor: "#36495d",
          borderWidth: 3
        },
        {
          label: "Planetary Mass (relative to the Sun x 10^-6)",
          type: "bar",
          data: [0.166, 2.081, 3.003, 0.323, 954.792, 285.886, 43.662, 51.514],
          backgroundColor: "rgba(71, 183,132,.5)",
          borderColor: "#47b784",
          borderWidth: 3
        }
      ]
    }
  };

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);

const fetchYears = async () => {
  try {
    const years = await getYearsShooting();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    //fetchVictimCountByRace(selectedYear.value, selectedBoro.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};
const renderChart = () => {
    if (planetChartCanvas.value) {
      const ctx = planetChartCanvas.value.getContext('2d');
      if (ctx) {
        new Chart(ctx, planetChartData);
      }
    }
  };
  
onMounted(renderChart);
fetchYears();
</script>

<template>
    <div class="flex justify-center items-center h-screen">
    <div class="card w-10/12 bg-base-100 shadow-xl">
      <div class="card-body ">
        <div class="grid grid-cols-3 gap-6 w-full">
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
          <div>
            <button class="btn btn-xs btn-primary">Load</button>
          </div>
        </div>
        <canvas class="w-full" ref="planetChartCanvas"></canvas>
      </div>
    </div>
    </div>
  </template>
