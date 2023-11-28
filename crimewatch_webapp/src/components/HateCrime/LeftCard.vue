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
          @click="fetchBiasCountByYear(selectedYear!)"
          class="btn btn-xs btn-primary"
        >
          Load
        </button>
      </div>
      <div v-if="data.labels.length > 0">
        <Chart :chartType="'doughnut'" :chartData="data" :key="chartKey" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import Chart from "../Chart/Chart.vue";
import { getYearsHateCrime, getBiasCountByYear } from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import { PieChartData } from "../../utils/types";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);
// let transformedWords = ref([]);

let chartKey = 0;
let data = reactive<PieChartData>({
  labels: [],
  datasets: [
    {
      label: "Hate Crime data",
      data: [],
      backgroundColor: [],
      hoverOffset: 4,
    },
  ],
});

const fetchYears = async () => {
  try {
    const years = await getYearsHateCrime();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    fetchBiasCountByYear(selectedYear.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchBiasCountByYear = async (year: number) => {
  try {
    const hatecrimeCount = await getBiasCountByYear(year);
    const labels = Object.keys(hatecrimeCount);
    const hateCrimeData = Object.values(hatecrimeCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    data.labels = labels;
    data.datasets[0].data = hateCrimeData;
    data.datasets[0].backgroundColor = backgroundColor;
    chartKey++;
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

fetchYears();
</script>
