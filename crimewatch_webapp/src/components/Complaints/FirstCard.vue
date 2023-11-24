<script setup lang="ts">
import { ref, reactive } from "vue";
import { getYears, getCrimeTypeCountByYear } from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import { PieChartData } from "../../utils/types";
import Chart from "../Chart/Chart.vue";

const selectedYear = ref<number>();
const selectedSex = ref<string>("Male");
const availableSex = ["Male", "Female", "Unknown"];
const yearList = ref<number[]>([]);

let chartKey = 0;
let data = reactive<PieChartData>({
  labels: [],
  datasets: [
    {
      label: "Top Crime complaint data",
      data: [],
      backgroundColor: [],
      hoverOffset: 4,
    },
  ],
});

const fetchYears = async () => {
  try {
    const years = await getYears();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    fetchCrimeTypeCountByYear(selectedYear.value, selectedSex.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchCrimeTypeCountByYear = async (year: number, sex: String) => {
  try {
    const crimeCount = await getCrimeTypeCountByYear(year, sex);
    const labels = Object.keys(crimeCount);
    const crimeData = Object.values(crimeCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    data.labels = labels;
    data.datasets[0].data = crimeData;
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
    <div class="card-body">
      <div class="grid grid-cols-2 gap-4">
        <div v-if="yearList.length > 0">
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
        </div>
        <div>
          <label class="flex flex-col items-center text-black text-sm"
            >Select sex</label
          >
          <select
            v-model="selectedSex"
            class="select select-xs select-bordered w-full"
          >
            <option v-for="sex in availableSex" :key="sex" :value="sex">
              {{ sex }}
            </option>
          </select>
        </div>
      </div>
      <div class="card-actions justify-center">
        <button
          @click="fetchCrimeTypeCountByYear(selectedYear!, selectedSex)"
          class="btn btn-xs btn-primary"
        >
          Load
        </button>
      </div>
      <div v-if="data.labels.length > 0">
        <Chart :chartType="'pie'" :chartData="data" :key="chartKey" />
      </div>
    </div>
  </div>
</template>
