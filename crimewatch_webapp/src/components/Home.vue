<template>
  <div class="flex px-4 pt-10 pb-10">
    <div class="w-1/2 md:px-0">
      <h1 class="text-4xl font-bold">NYC CrimeWatch</h1>
      <p class="md:text-xl">
        A tool to analyze various crimes in New York City based on past data
      </p>
      <div class="mt-8">
        <p>
          This application is a data-driven web platform focused on analyzing
          crime-related data in New York City. It specializes in examining
          shooting incidents and hate crimes. By utilizing advanced technologies
          like Semantic Web Engineering, ontologies, and RDF, the application
          provides insights crucial for decision-making and proactive crime
          prevention.
        </p>
      </div>
      <div class="mt-8">
        <h2 class="text-2xl font-semibold">Key Functionalities</h2>
        <ul class="list-disc mt-4 ml-6">
          <li>
            <span class="font-bold">Racial Disparity Analysis:</span> Examining
            shooting incident data by race for both perpetrators and victims.
          </li>
          <li>
            <span class="font-bold">Temporal Patterns:</span> Providing insights
            into the timing of shooting incidents to identify temporal patterns.
          </li>
          <li>
            <span class="font-bold">Prevalence Ratio:</span> Calculating and
            displaying the ratio of shooting incidents relative to total
            reported crimes.
          </li>
          <li>
            <span class="font-bold">Demographic Analysis: </span>Analyzing
            shooting incident ratios relative to factors like race, age, or
            borough.
          </li>
          <li>
            <span class="font-bold">Hate Crime Insights: </span>Providing
            detailed insights into hate crimes by location, time, demographics,
            and more.
          </li>
        </ul>
        <p class="mt-4">
          The application aims to empower stakeholders by presenting valuable
          visualizations and analytical results. It allows for informed
          decision-making, resource allocation, and proactive measures to
          address interconnected crime issues in New York City.
        </p>
      </div>
      <div class="mt-4">
        <p>
          <span>To download and study the full report, </span>
          <a
            href="src/assets/Crimewatch.pdf"
            download="CrimewatchReport.pdf"
            class="text-blue-500 hover:underline"
          >
            click here
          </a>
        </p>

        <p class="mt-2 text-center text-xl">
          <router-link to="/complaint" class="text-blue-500 hover:underline">
            Get Started
          </router-link>
        </p>
      </div>
    </div>
    <div class="w-full xl:w-1/2 relative overflow-hidden">
      <div class="flex">
        <div
          v-for="(image, index) in crimeImages"
          :key="index"
          :class="{ 'ml-10': index !== 0 }"
          class="w-full flex-shrink-0 transition-transform duration-500"
          :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
        >
          <img
            :src="image"
            :alt="`image_${index}`"
            class="w-full h-full object-cover"
          />
        </div>
      </div>
      <h2 class="text-2xl font-bold mt-8 text-center">Crime News Alert !</h2>
      <div class="card w-full bg-base-100 shadow-xl">
        <div class="card-body">
          <transition name="fade">
            <h2 class="card-title" :key="currentNews?.title">
              {{ currentNews?.title }}
            </h2>
          </transition>
          <transition name="slide">
            <div class="flex flex-col" :key="currentNews?.title">
              <p class="mb-4">{{ currentNews?.description }}</p>
              <p class="text-right">{{ currentNews?.author }}</p>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { fetchNews } from "../utils/api";
import { NewsArticle } from "../utils/types";
import gunCrime from "@/assets/images/gun_crime.jpg";
import hateCrime from "@/assets/images/hate_crime.jpg";
import hateCrime2 from "@/assets/images/hate_crime2.jpg";
const currentIndex = ref(0);
const intervalDuration = 4000;
const crimeImages = [gunCrime, hateCrime, hateCrime2];
const imageCount = computed(() => crimeImages.length);
const newsCount = computed(() => newsArticles.value.length);
const newsArticles = ref<NewsArticle[]>([]);
const currentNews = ref<NewsArticle>();
const currentNewsIndex = ref(0);

function startImageRotation() {
  setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % imageCount.value;
  }, intervalDuration);
}

function startNewsRotation() {
  setInterval(() => {
    currentNewsIndex.value = (currentNewsIndex.value + 1) % newsCount.value;
  }, intervalDuration);
}

async function fetchNYNews() {
  try {
    const articles = await fetchNews();
    newsArticles.value = articles;
    currentNews.value = newsArticles.value[currentNewsIndex.value];
  } catch (error) {
    console.error("Error fetching news:", error);
  }
}

onMounted(() => {
  startImageRotation();
  startNewsRotation();
  fetchNYNews();
});
</script>
