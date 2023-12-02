import { fileURLToPath, URL } from "node:url"
import { defineConfig } from "vite"
import vue from "@vitejs/plugin-vue"
import ImportMetaEnvPlugin from "@import-meta-env/unplugin"

// https://vitejs.dev/config/
export default defineConfig({
  base: "/crimewatch/",
  build: {
    outDir: "cw",
  },
  plugins: [
    vue(),
    ImportMetaEnvPlugin.vite({
      example: ".env.example",
    }),
  ],
  resolve: {
    alias: [
      {
        find: "@",
        replacement: fileURLToPath(new URL("./src", import.meta.url)),
      },
      {
        find: "@assets",
        replacement: fileURLToPath(new URL("./src/assets", import.meta.url)),
      },
      {
        find: "@components",
        replacement: fileURLToPath(
          new URL("./src/components", import.meta.url)
        ),
      },
    ],
  },

  server: {
    host: true,
    port: 8000,

    watch: {
      usePolling: true,
    },
  },
})
