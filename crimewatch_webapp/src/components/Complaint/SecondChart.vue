<script setup lang="ts">
import { ref } from "vue";
import { getYears } from "../../utils/api";
import Chart from "../Chart/Chart.vue";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);

const selectedBoro = ref<string>();
const availableBoro = [
  "Bronx",
  "Brooklyn",
  "Manhattan",
  "Queens",
  "Staten Island",
];

const type = "bar";
let chartKey = 0;

const labels = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July"];
const data = {
  labels: labels,
  datasets: [
    {
      label: "My First Dataset",
      data: [65, 59, 80, 81, 56, 55, 40],
      backgroundColor: [
        "rgba(255, 99, 132, 0.2)",
        "rgba(255, 159, 64, 0.2)",
        "rgba(255, 205, 86, 0.2)",
        "rgba(75, 192, 192, 0.2)",
        "rgba(54, 162, 235, 0.2)",
        "rgba(153, 102, 255, 0.2)",
        "rgba(201, 203, 207, 0.2)",
      ],
      borderColor: [
        "rgb(255, 99, 132)",
        "rgb(255, 159, 64)",
        "rgb(255, 205, 86)",
        "rgb(75, 192, 192)",
        "rgb(54, 162, 235)",
        "rgb(153, 102, 255)",
        "rgb(201, 203, 207)",
      ],
      borderWidth: 1,
    },
  ],
};

const fetchYears = async () => {
  try {
    const years = await getYears();
    yearList.value = years;
    selectedYear.value = yearList.value[0];
  } catch (error) {
    console.error("Error fetching years:", error);
  }
};

fetchYears();
</script>

<template>
  <div class="card w-full md:w-1/2 overflow-auto bg-base-100 shadow-2xl">
    <div class="card-body">
      <div class="flex mb-4 space-x-2">
        <div class="w-1/2">
          <label class="text-black text-sm flex flex-col items-center">
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
        <div class="w-1/2">
          <label class="flex flex-col items-center text-black text-sm">
            Select boro
          </label>
          <select
            v-model="selectedBoro"
            class="select select-xs select-bordered w-full"
          >
            <option v-for="boro in availableBoro" :key="boro" :value="boro">
              {{ boro }}
            </option>
          </select>
        </div>
      </div>
      <div class="flex items-center justify-center -mt-4">
        <button class="btn btn-xs btn-primary w-16">Load</button>
      </div>
      <div
        class="chart-container w-full h-1/2 flex items-center justify-center"
      >
        <Chart :type="type" :data="data" :key="chartKey" />
      </div>
    </div>
  </div>
</template>
