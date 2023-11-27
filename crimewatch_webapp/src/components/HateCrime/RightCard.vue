<script setup lang="ts">
import { ref, reactive } from "vue";
import {
    getYearsHateCrime,
    getHateCrimeCountByBoro,
} from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import { BarChartData } from "../../utils/types";
import Chart from "../Chart/Chart.vue";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);


let barChartKey = 0;
let barChartdata = reactive<BarChartData>({
  labels: [],
  datasets: [
    {
      label: "Hate Crime Count by Boro",
      data: [],
      backgroundColor: [],
      borderColor: [],
      borderWidth: 1,
    },
  ],

});



const fetchYears = async () => {
  try {
    const years = await getYearsHateCrime();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    fetchCountByBoro(selectedYear.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchCountByBoro = async (year: number) => {
  try {
    const hatecrimeCount = await getHateCrimeCountByBoro(year);
    const labels = Object.keys(hatecrimeCount);
    const boroCount = Object.values(hatecrimeCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    barChartdata.labels = labels;
    barChartdata.datasets[0].data = boroCount;
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
          @click="fetchCountByBoro(selectedYear)"
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
    </div>
  </div>
</template>
