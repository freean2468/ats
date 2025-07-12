// File: mvc-vs-webflux/webflux/src/main/java/com/example/webflux/WebfluxApplication.java

package com.example.webflux;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @GetMapping("/static")
    public Mono<String> staticResponse() {
        return Mono.just("{\"message\":\"Hello from WebFlux\"}");
    }

    @Autowired
    private DatabaseClient databaseClient;

    public record NowResult(String now) {}

    @GetMapping("/dbtest")
    public Mono<String> dbTest() {
        return databaseClient.sql("SELECT now() AS now")
            .fetch()
            .first()
            .map(row -> {
                return "{\"dbTime\":\"" + String.valueOf(row.get("now")) + "\"}";
            });
    }

    @GetMapping("/dbtest/slow")
    public Mono<String> dbTestSlow() {
        return databaseClient.sql("SELECT now() AS now, pg_sleep(0.1)")
            .fetch()
            .first()
            .timeout(Duration.ofSeconds(60))
            .onErrorResume(TimeoutException.class, e -> Mono.just(Map.of()))
            // .doOnError(e -> {
            //     System.err.println("DB fetch error: " + e);
            // })
            // .doFinally(signalType -> {
            //     System.out.println("dbTestSlow terminated with signal: " + signalType);
            // })
            .map(row -> {
                return "{\"dbTime\":\"" + String.valueOf(row.get("now")) + "\"}";
            });
    }


}
