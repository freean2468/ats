package com.example.webflux;

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
        return databaseClient
            .sql("SELECT now() AS now")
            .fetch()
            .one()
            .map(row -> "{\"dbTime\":\"" + row.get("now") + "\"}");
    }
}
