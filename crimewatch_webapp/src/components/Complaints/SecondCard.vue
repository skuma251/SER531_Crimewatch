<script setup lang="ts">
import { ref, reactive } from "vue";
import { getYears, getBoroCrimeCountByYear } from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import { BarChartData, LineChartData } from "../../utils/types";
import Chart from "../Chart/Chart.vue";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);

let barChartKey = 0;
let barChartdata = reactive<BarChartData>({
  labels: [],
  datasets: [
    {
      label: "Top Crime complaint data",
      data: [],
      backgroundColor: [],
      borderColor: [],
      borderWidth: 1,
    },
  ],
});

const labels = ["Jan", "Feb", "Mar", "Apr", "May"];
const data2 = {
  labels: labels,
  datasets: [
    {
      label: "My First Dataset",
      data: [65, 59, 80, 81, 56, 55, 40],
      fill: false,
      borderColor: "rgb(75, 192, 192)",
      tension: 0.1,
    },
    {
      label: "My Second Dataset",
      data: [88, 55, 80, 60, 56, 100, 20],
      fill: false,
      borderColor: "rgb(85, 90, 170)",
      tension: 0.1,
    },
  ],
};

const fetchYears = async () => {
  try {
    const years = await getYears();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    fetchBoroCrimeCountByYear(selectedYear.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchBoroCrimeCountByYear = async (year: number) => {
  try {
    const crimeCount = await getBoroCrimeCountByYear(year);
    const labels = Object.keys(crimeCount);
    const crimeData = Object.values(crimeCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    barChartdata.labels = labels;
    barChartdata.datasets[0].data = crimeData;
    barChartdata.datasets[0].backgroundColor = backgroundColor;
    barChartKey++;
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
          @click="fetchBoroCrimeCountByYear(selectedYear!)"
          class="btn btn-xs btn-primary"
        >
          Load
        </button>
      </div>
      <div v-if="barChartdata.labels.length > 0">
        <Chart
          :chartType="'bar'"
          :chartData="barChartdata"
          :key="barChartKey"
        />
      </div>
      <div v-if="data.labels.length > 0">
        <Chart :chartType="'line'" :chartData="data" :key="chartKey" />
      </div>
    </div>
  </div>
</template>
