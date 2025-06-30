package com.example.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class MvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcApplication.class, args);
    }

    @GetMapping("/static")
    public String staticResponse() {
        return "{\"message\":\"Hello from MVC\"}";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/dbtest")
    public String dbTest() {
        String time = jdbcTemplate.queryForObject("SELECT now()", String.class);
        return "{\"dbTime\":\"" + time + "\"}";
    }
}