<script setup lang="ts">
import { ref, reactive } from "vue";
import {
  getYears,
  getBoroCrimeCountByYear,
  getBoroMonthlyStatsByYear,
} from "../../utils/api";
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

let lineChartKey = 0;
let lineChartdata = reactive<LineChartData>({
  labels: [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ],
  datasets: [
    {
      label: "Crime for each boro based on year",
      data: [],
      fill: false,
      borderColor: generateRandomColor(),
      tension: 0.1,
    },
  ],
});

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
    fetchBoroMonthlyStatsByYear(selectedYear.value!);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchBoroMonthlyStatsByYear = async (year: number) => {
  try {
    const crimeStats = await getBoroMonthlyStatsByYear(year);
    const labels = Object.keys(crimeStats);
    const dataValues = Object.values(crimeStats);

    lineChartdata.datasets = labels.map((label, index) => {
      const multipliedData = Object.values(dataValues[index]).map(
        (value: number) => value * 1
      );

      return {
        label,
        data: multipliedData,
        fill: false,
        borderColor: generateRandomColor(),
        tension: 0.1,
      };
    });

    lineChartKey++;
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
      <div v-if="lineChartdata.datasets.length > 0">
        <Chart
          :chartType="'line'"
          :chartData="lineChartdata"
          :key="lineChartKey"
        />
      </div>
    </div>
  </div>
</template>
