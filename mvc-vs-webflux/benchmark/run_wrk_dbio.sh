# run_wrk_dbio.sh
#!/bin/bash

# Define concurrency levels
concurrencies=(1 2 4 8 16 32 64 128)
# Define thread counts
threads=(1)

# Target endpoints
mvc_url="http://localhost:8081/api/dbtest"
webflux_url="http://localhost:8082/api/dbtest"

# Duration
duration="10s"
timeout="60s"

# Output CSV file
output_file="./results/dbio/6t_2c1024m_mvc200_wf4.csv"

# Write CSV header
echo "Framework,Threads,Concurrency,RequestsPerSec,TransferPerSec,AvgLatencyMs,MaxLatencyMs" > "$output_file"

# Function to parse wrk output
parse_wrk_output() {
  local framework=$1
  local threads=$2
  local concurrency=$3
  local output=$4

  local rps=$(echo "$output" | grep "Requests/sec" | awk '{print $2}')
  local tps=$(echo "$output" | grep "Transfer/sec" | awk '{print $2 " " $3}')
  local avg_lat=$(echo "$output" | grep "Latency" | head -n1 | awk '{print $2}')
  local max_lat=$(echo "$output" | grep "Latency" | head -n1 | awk '{print $4}')

  # Convert latency to ms if necessary
  avg_lat_ms=$(convert_to_ms "$avg_lat")
  max_lat_ms=$(convert_to_ms "$max_lat")

  echo "$framework,$threads,$concurrency,$rps,$tps,$avg_lat_ms,$max_lat_ms" >> "$output_file"
}

# Function to convert latency to ms
convert_to_ms() {
  local val=$1
  if [[ $val == *ms ]]; then
    echo "${val/ms/}"
  elif [[ $val == *s ]]; then
    echo "$(awk "BEGIN {print ${val/s/} * 1000}")"
  elif [[ $val == *us ]]; then
    echo "$(awk "BEGIN {print ${val/us/} / 1000}")"
  else
    echo "$val"
  fi
}

# Iterate over each combination
for c in "${concurrencies[@]}"; do
  for t in "${threads[@]}"; do
    echo "🔁 Benchmarking Spring MVC - Threads: $t Concurrency: $c"
    mvc_result=$(wrk -t"$t" -c"$c" --timeout "$timeout" -d"$duration" "$mvc_url")
    parse_wrk_output "SpringMVC" "$t" "$c" "$mvc_result"

    echo "🔁 Benchmarking Spring WebFlux - Threads: $t Concurrency: $c"
    webflux_result=$(wrk -t"$t" -c"$c" --timeout "$timeout" -d"$duration" -s"./stream.lua" "$webflux_url")
    parse_wrk_output "SpringWebFlux" "$t" "$c" "$webflux_result"
  done
done

echo "✅ Benchmarking complete. Results saved to $output_file"
