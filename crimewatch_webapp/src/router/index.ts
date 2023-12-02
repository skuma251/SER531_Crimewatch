import { createRouter, createWebHistory } from "vue-router"

// Import your components
import Home from "../components/Home.vue"
import Complaint from "../components/Complaints/Complaints.vue"
import Shooting from "../components/Shootings/Shooting.vue"
import HateCrime from "../components/HateCrime/HateCrime.vue"
import Miscellaneous from "../components/Miscellaneous/Miscellaneous.vue"

const routes = [
  {
    path: "/",
    component: Home,
    name: "Home",
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
  {
    path: "/hate-crime",
    component: HateCrime,
    name: "Hate-Crime",
  },
  {
    path: "/miscellaneous",
    component: Miscellaneous,
    name: "Miscellaneous",
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
