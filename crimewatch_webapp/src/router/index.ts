import { createRouter, createWebHistory } from "vue-router";

// Import your components
import Home from "@components/Home.vue";
import About from "@components/About.vue"

const routes = [
  {
    path: "/",
    component: Home,
    name: "Home",
  },
  {
    path: "/about",
    component: About,
    name: "About",
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
