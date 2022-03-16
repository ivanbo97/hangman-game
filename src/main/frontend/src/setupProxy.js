const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    ["/api", "/login", "/actuator", "/swagger-ui", "/v3/api-docs"],
    createProxyMiddleware({
      target: "http://localhost:8080",
      changeOrigin: true,
      xfwd: true,
    })
  );
};
