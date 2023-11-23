import { createRouter, createWebHistory } from "vue-router";

// Import your components
import Home from "@components/Home.vue";
import About from "@components/About.vue"
import Complaint from "@components/Complaint/Complaint.vue"

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
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
