package com.github.chMatvey.chaosTool.chaosServerStarter.web.controller;

import com.github.chMatvey.chaosTool.chaosModels.CreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.CreateResponse;
import com.github.chMatvey.chaosTool.chaosServerStarter.service.ChaosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/chaos-server")
public record ChaosController(ChaosService chaosService) {

    @GetMapping("/health-check")
    public ResponseEntity<Boolean> healthCheck() {
        return ok(true);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@RequestBody CreateRequest createRequest) {
        return ok(chaosService.create(createRequest));
    }
}
