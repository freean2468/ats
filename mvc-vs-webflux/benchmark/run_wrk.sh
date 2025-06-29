#!/bin/bash

echo "🔁 Benchmarking Spring MVC (8081)"
wrk -t4 -c100 -d30s http://localhost:8081/api/static

echo "🔁 Benchmarking Spring WebFlux (8082)"
wrk -t4 -c100 -d30s http://localhost:8082/api/static
