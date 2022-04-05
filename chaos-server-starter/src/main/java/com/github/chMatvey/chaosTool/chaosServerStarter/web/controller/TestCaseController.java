package com.github.chMatvey.chaosTool.chaosServerStarter.web.controller;

import com.github.chMatvey.chaosTool.chaosServerStarter.service.ReportGenerator;
import com.github.chMatvey.chaosTool.chaosServerStarter.service.TestCasesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chaos-server")
public record TestCaseController(TestCasesService testCasesService, ReportGenerator reportGenerator) {
    @GetMapping()
    public ResponseEntity<Void> runTestCases() {
        throw new RuntimeException("Not implemented");
    }
}
