<script setup lang="ts">
import { ref } from "vue";
import { getYears } from "../../utils/api";

const selectedYear = ref<number>();
const yearList = ref<number[]>([]);

const selectedBoro = ref<String>();
const availableBoro = [
  "Bronx",
  "Brooklyn",
  "Manhattan",
  "Queens",
  "Staten Island",
];

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
      <div class="flex mb-4 gap-2">
        <div class="w-1/2">
          <label class="text-black text-sm flex flex-col items-center"
            >Select year</label
          >
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
            >Select boro</label
          >
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
    </div>
  </div>
</template>
