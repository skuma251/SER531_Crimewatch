/** @type {import('tailwindcss').Config} */
const defaultTheme = require("tailwindcss/defaultTheme");

module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx,vue}",
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ["Lato", ...defaultTheme.fontFamily.sans],
      },
    },
  },
  daisyui: {
    themes: [
      {
        Crimewatch: {
          "primary": "#00c7c6",
          "primary-content": "#FFFFFF",
          "secondary": "#035e7f",
          "accent": "#f2a682",
          "neutral": "#000000",
          "base-100": "#F2EEE4",
          "base-300": "#707070",
          "info": "#41ABD8",
          "success": "#1BB184",
          "warning": "#F7B55F",
          "error": "#E02E5E",
        },
      },
    ],
    darkTheme: "Crimewatch",
  },
  plugins: [
    require("@tailwindcss/typography"),
    require("daisyui"),
  ],
};
