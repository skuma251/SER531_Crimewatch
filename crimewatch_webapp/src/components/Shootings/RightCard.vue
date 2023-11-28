<script setup lang="ts">
import { ref, reactive } from "vue";
import { getYearsShooting, getCountByBoro } from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import { PolarAreaChart } from "../../utils/types";
import Chart from "../Chart/Chart.vue";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);

let chartKey = 0;
let data = reactive<PolarAreaChart>({
  labels: [],
  datasets: [
    {
      label: "Shooting Count by Boro",
      data: [],
      backgroundColor: [],
    },
  ],
});

const fetchYears = async () => {
  try {
    const years = await getYearsShooting();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    fetchCountByBoro(selectedYear.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchCountByBoro = async (year: number) => {
  try {
    const crimeCount = await getCountByBoro(year);
    const labels = Object.keys(crimeCount);
    const boroCount = Object.values(crimeCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    data.labels = labels;
    data.datasets[0].data = boroCount;
    data.datasets[0].backgroundColor = backgroundColor;
    chartKey++;
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

fetchYears();
</script>

<template>
  <div class="card w-full bg-base-100 shadow-xl">
    <div class="card-body" v-if="yearList.length > 0">
      <label class="flex flex-col items-center text-black text-sm">
        Select year
      </label>
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
      <div class="card-actions justify-center">
        <button
          @click="fetchCountByBoro(selectedYear!)"
          class="btn btn-xs btn-primary"
        >
          Load
        </button>
      </div>
      <div v-if="data.labels.length > 0">
        <Chart :chartType="'polarArea'" :chartData="data" :key="chartKey" />
      </div>
    </div>
  </div>
</template>
