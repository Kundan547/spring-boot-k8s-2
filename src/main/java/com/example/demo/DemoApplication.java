package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Main endpoint for testing deployment
    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot on Kubernetes and testing on argocd! - Build Time: " + Instant.now();
    }

    // Health check endpoint for readiness/liveness probes
    @GetMapping("/health")
    public String health() {
        return "OK - " + Instant.now();
    }

    // Version check endpoint
    @GetMapping("/version")
    public String version() {
        String commitSha = System.getenv().getOrDefault("GIT_COMMIT_SHA", "dev-build");
        return "Running version: " + commitSha;
    }
}
