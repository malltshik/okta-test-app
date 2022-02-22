package com.scoober.okta;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/secret")
public class SecretController {

    @GetMapping("/read")
    @PreAuthorize("hasPermission('SECRET', 'READ')")
    public ResponseEntity<String> readSecret() {
        return ResponseEntity.ok("secret");
    }

    @GetMapping("/create")
    @PreAuthorize("hasPermission('SECRET', 'CREATE')")
    public ResponseEntity<String> createSecret() {
        return ResponseEntity.ok("secret");
    }

    @GetMapping("/update")
    @PreAuthorize("hasPermission('SECRET', 'UPDATE')")
    public ResponseEntity<String> updateSecret() {
        return ResponseEntity.ok("secret");
    }
}
