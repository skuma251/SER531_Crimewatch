<script setup lang="ts">
import { ref, reactive, computed } from "vue";
import Chart from "../Chart/Chart.vue";
import { getYears, getCrimeTypeCountByYear } from "../../utils/api";
import { BarChartData, PieChartData } from "../../utils/types";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);
const selectedSex = ref<String>("Male");
const availableSex = ["Male", "Female"];
const type = "pie";
let chartKey = 0;

let data = reactive<BarChartData | PieChartData>({
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

const metadata = computed(() => {
  const totalCrimeCount = data.datasets[0].data.reduce(
    (acc, val) => acc + val,
    0
  );
  const numberOfCrimeTypes = data.labels.length;

  const crimePercentages = data.datasets[0].data.map((count: number) =>
    ((count / totalCrimeCount) * 100).toFixed(2)
  );

  return {
    totalCrimeCount,
    numberOfCrimeTypes,
    crimePercentages,
  };
});

const generateRandomColor = () => {
  return `rgb(${Math.floor(Math.random() * 256)}, ${Math.floor(
    Math.random() * 256
  )}, ${Math.floor(Math.random() * 256)})`;
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

fetchYears();
</script>

<template>
  <div
    v-if="yearList.length > 0"
    class="card w-full md:w-1/2 md:h-full overflow-auto bg-base-100 shadow-2xl"
  >
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
      <div class="flex items-center justify-center -mt-4">
        <button
          @click="fetchCrimeTypeCountByYear(selectedYear!, selectedSex)"
          class="btn btn-xs btn-primary w-16"
        >
          Load
        </button>
      </div>
      <div
        v-if="data.labels.length > 0"
        class="h-96 flex items-center justify-center"
      >
        <Chart :type="type" :data="data" :key="chartKey" />
      </div>
      <div class="card bg-base-100 shadow-xl">
        <div class="card-body">
          <h2 class="card-title flex justify-center">Chart Metadata:</h2>
          <div class="text-xs">
            <p>Total Crime Count: {{ metadata.totalCrimeCount }}</p>
            <p>Number of Crime Types: {{ metadata.numberOfCrimeTypes }}</p>
            <div
              v-for="(percentage, index) in metadata.crimePercentages"
              :key="index"
            >
              <p>{{ data.labels[index] }}: {{ percentage }}%</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
