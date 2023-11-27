import { createRouter, createWebHistory } from "vue-router";

// Import your components
import Home from "@components/Home.vue";
import About from "@components/About.vue"
import Complaint from "@components/Complaints/Complaints.vue"
import Shooting from "@components/Shootings/Shooting.vue"

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
  {
    path: "/complaint",
    component: Complaint,
    name: "Complaint",
  },
  {
    path: "/shooting",
    component: Shooting,
    name: "Shooting",
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
