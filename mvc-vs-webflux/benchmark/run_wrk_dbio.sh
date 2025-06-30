#!/bin/bash

# Define concurrency levels
concurrencies=(10 50 100 200 500)
# Define thread counts
threads=(2 4 8)

# Target endpoints
mvc_url="http://localhost:8081/api/dbtest"
webflux_url="http://localhost:8082/api/dbtest"

# Duration
duration="30s"

# Iterate over each combination
for c in "${concurrencies[@]}"; do
  for t in "${threads[@]}"; do
    echo "🔁 Benchmarking Spring MVC - Threads: $t Concurrency: $c"
    wrk -t"$t" -c"$c" -d"$duration" "$mvc_url"

    echo "🔁 Benchmarking Spring WebFlux - Threads: $t Concurrency: $c"
    wrk -t"$t" -c"$c" -d"$duration" "$webflux_url"
  done
done
