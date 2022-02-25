package com.scoober.okta.web;

import com.scoober.okta.security.User;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
public class SecretController {

    private static final Logger log = LoggerFactory.getLogger(SecretController.class);

    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> readSecret(@RequestParam Map<String, String> payload) {
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/secret/read")
    @PreAuthorize("hasPermission(" +
            "T(com.scoober.okta.security.Domains).SECRET, " +
            "T(com.scoober.okta.security.Permissions).READ)")
    public ResponseEntity<Secret> readSecret(User user, Authentication auth, Principal principal) {
        log.info("USER ### {}", user);
        log.info("AUTH ### {}", auth);
        log.info("PRINCIPAL ### {}", principal);
        return ResponseEntity.ok(new Secret());
    }

    @GetMapping("/secret/create")
    @PreAuthorize("hasPermission(" +
            "T(com.scoober.okta.security.Domains).SECRET, " +
            "T(com.scoober.okta.security.Permissions).CREATE)")
    public ResponseEntity<Secret> createSecret() {
        return ResponseEntity.ok(new Secret());
    }

    @GetMapping("/secret/update")
    @PreAuthorize("hasPermission(" +
            "T(com.scoober.okta.security.Domains).SECRET, " +
            "T(com.scoober.okta.security.Permissions).UPDATE)")
    public ResponseEntity<Secret> updateSecret() {
        return ResponseEntity.ok(new Secret());
    }

    @GetMapping("/secret/delete")
    @PreAuthorize("hasPermission(" +
            "T(com.scoober.okta.security.Domains).SECRET, " +
            "T(com.scoober.okta.security.Permissions).DELETE)")
    public ResponseEntity<Secret> deleteSecret() {
        return ResponseEntity.ok(new Secret());
    }


    @Data
    public static class Secret {
        private final String value = "secret";
    }
}
