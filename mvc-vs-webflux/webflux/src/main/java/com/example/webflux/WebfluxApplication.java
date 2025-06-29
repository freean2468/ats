package com.example.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
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
}
