<template>
  <div class="flex relative pt-24 pb-16 px-4 sm:px-6 md:px-10">
      <div class="w-1/2 px-4 sm:px-6 md:px-10">
        <h1 class="text-8xl font-bold mb-4">
          Welcome to the New York City Crimewatch
        </h1>
        <p class="text-2xl font-bold mb-4">
          (A tool to analyze the New York City various crimes based on the past data)
        </p>
      </div>

      <div class="w-full lg:w-2/3 xl:w-1/2 relative px-2">
      <div class="overflow-hidden h-full">
        <div class="w-full h-full flex">
          <!-- Images -->
          <div
            v-for="(image, index) in images"
            :key="index"
            :class="{ 'ml-0': index === 0, 'ml-10': index !== 0 }"
            class="w-full flex-shrink-0 transition-transform duration-500"
            :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
          >
            <img :src="image" alt="image" class="w-full h-full object-cover" />
          </div>
        </div>
      </div>


      <div class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
        <span
          v-for="(image, index) in images"
          :key="index"
          :class="{ 'bg-gray-800': currentIndex === index, 'bg-gray-300': currentIndex !== index }"
          class="h-3 w-3 rounded-full cursor-pointer"
          @click="changeImage(index)"
        ></span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import gunCrime from '@/images/gun_crime.jpg';
import hateCrime from '@/images/hate_crime.jpg';
import hateCrime2 from '@/images/hate_crime2.jpg';

const currentIndex = ref(0);
const intervalDuration = 4000; // Change image every 4 seconds

function changeImage(index: number) {
  currentIndex.value = index;
}

const images = [gunCrime, hateCrime, hateCrime2]; // Use the imported images

setInterval(() => {
  currentIndex.value = (currentIndex.value + 1) % images.length;
}, intervalDuration);
</script>
