package com.quantus.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-05-09
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}")
public class AWSHealthStatusController {

    /**
     * Return a HTTP 200 heartbeat for AWS Target Group Health Checks
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Healthy");
    }
}
