
package com.example.bfhl.controller;

import com.example.bfhl.dto.ApiResponse;
import com.example.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BfhlController {

    private final BfhlService service;

    @Value("${official.email}")
    private String email;

    public BfhlController(BfhlService service) {
        this.service = service;
    }

    // HEALTH API
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(
                new ApiResponse(true, email, null)
        );
    }

    // MAIN API
    @PostMapping("/bfhl")
    public ResponseEntity<ApiResponse> bfhl(@RequestBody Map<String, Object> body) {

        try {
            if (body.size() != 1) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, email, "Exactly one key required"));
            }

            String key = body.keySet().iterator().next();
            Object value = body.get(key);
            Object result;

            switch (key) {
                case "fibonacci":
                    result = service.fibonacci((Integer) value);
                    break;

                case "prime":
                    result = service.prime((List<Integer>) value);
                    break;

                case "lcm":
                    result = service.lcm((List<Integer>) value);
                    break;

                case "hcf":
                    result = service.hcf((List<Integer>) value);
                    break;

                case "AI":
                    result = service.ai((String) value);
                    break;

                default:
                    return ResponseEntity.badRequest()
                            .body(new ApiResponse(false, email, "Invalid key"));
            }

            return ResponseEntity.ok(
                    new ApiResponse(true, email, result)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, email, e.getMessage()));
        }
    }
}
