package com.github.chMatvey.chaosTool.chaosServerStarter.web.controller;

import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosSessionInfoResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import com.github.chMatvey.chaosTool.chaosServerStarter.service.ChaosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/chaos-server")
@Slf4j
public record ChaosController(ChaosService chaosService) {

    @PostMapping
    public ResponseEntity<ChaosResponse> create(@RequestBody ChaosCreateRequest createRequest) {
        log.info("REST request to CREATE Chaos Testing Session");
        return ok(chaosService.create(createRequest));
    }

    @PutMapping
    public ResponseEntity<ChaosResponse> update(@RequestBody ChaosUpdateRequest createRequest) {
        log.info("REST request to UPDATE Chaos Testing Session");
        return ok(chaosService.update(createRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChaosSessionInfoResponse> chaosSessionInfo(@PathVariable Integer id) {
        log.info("REST request to GET Chaos Testing Session");
        return of(chaosService.get(id));
    }
}
