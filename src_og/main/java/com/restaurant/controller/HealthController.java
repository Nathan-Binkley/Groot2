package com.restaurant.controller;

import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Health", description = "Health check endpoints")
public class HealthController {

    private final HealthEndpoint healthEndpoint;

    public HealthController(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Checks the health status of the application",
        responses = {
            @ApiResponse(responseCode = "200", description = "Application is healthy"),
            @ApiResponse(responseCode = "503", description = "Application is unhealthy")
        }
    )
    public ResponseEntity<HealthComponent> health() {
        HealthComponent health = healthEndpoint.health();
        HttpStatus status = Status.UP.equals(health.getStatus()) ? 
                           HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return new ResponseEntity<>(health, status);
    }
} 