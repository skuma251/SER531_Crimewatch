module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    "@antfu",
  ],
  rules: {
    "@typescript-eslint/quotes": ["error", "double"],
    "semi": "off",
    "@typescript-eslint/semi": ["error", "always"],
    "no-console": "off",
    "no-unused-vars": "off",
    "@typescript-eslint/no-unused-vars": ["error", { argsIgnorePattern: "^_" }],
    "@typescript-eslint/brace-style": ["error", "1tbs", { allowSingleLine: true }],
  },
};
