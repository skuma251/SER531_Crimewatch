<script setup lang="ts">
import { ref, reactive } from "vue";
import {
  getYearsShooting,
  getVictimCountByRace,
  getPerpCountByRace,
} from "../../utils/api";
import { generateRandomColor } from "../../utils/index";
import { BarChartData } from "../../utils/types";
import Chart from "../Chart/Chart.vue";

const selectedYear = ref<number>();
const selectedBoro = ref<string>("BRONX");
const boroList = ["MANHATTAN", "BRONX", "BROOKLYN", "STATEN ISLAND", "QUEENS"];
const yearList = ref<number[]>([]);

let barChartKey = 0;
let barChartdata = reactive<BarChartData>({
  labels: [],
  datasets: [
    {
      label: "Victim Count Group by Race",
      data: [],
      backgroundColor: [],
      borderColor: [],
      borderWidth: 1,
    },
  ],
});

let barChartKey1 = 0;
let barChartdata1 = reactive<BarChartData>({
  labels: [],
  datasets: [
    {
      label: "Perp Count Group by Race",
      data: [],
      backgroundColor: [],
      borderColor: [],
      borderWidth: 1,
    },
  ],
});

const fetchYears = async () => {
  try {
    const years = await getYearsShooting();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
    fetchVictimCountByRace(selectedYear.value, selectedBoro.value);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchVictimCountByRace = async (year: number, boro: String) => {
  try {
    const victimCount = await getVictimCountByRace(year, boro);
    const labels = Object.keys(victimCount);
    const victimData = Object.values(victimCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    barChartdata.labels = labels;
    barChartdata.datasets[0].data = victimData;
    barChartdata.datasets[0].backgroundColor = backgroundColor;
    barChartKey++;
    fetchPerpCountByRace(year, boro);
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

const fetchPerpCountByRace = async (year: number, boro: String) => {
  try {
    const perpCount = await getPerpCountByRace(year, boro);
    const labels = Object.keys(perpCount);
    const perpData = Object.values(perpCount);
    const backgroundColor = Array.from({ length: labels.length }, () =>
      generateRandomColor()
    );
    barChartdata1.labels = labels;
    barChartdata1.datasets[0].data = perpData;
    barChartdata1.datasets[0].backgroundColor = backgroundColor;
    barChartKey1++;
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
            >Select Boro</label
          >
          <select
            v-model="selectedBoro"
            class="select select-xs select-bordered w-full"
          >
            <option v-for="boro in boroList" :key="boro" :value="boro">
              {{ boro }}
            </option>
          </select>
        </div>
      </div>
      <div class="card-actions justify-center">
        <button
          @click="fetchVictimCountByRace(selectedYear!, selectedBoro)"
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
      <div v-if="barChartdata1.labels.length > 0">
        <Chart
          :chartType="'bar'"
          :chartData="barChartdata1"
          :key="barChartKey1"
        />
      </div>
    </div>
  </div>
</template>
