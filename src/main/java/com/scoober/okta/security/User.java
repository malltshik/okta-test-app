package com.scoober.okta.security;

import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Value
public class User {

    String username;
    String email;
    Instant authTime;
    Instant authExp;
    List<String> groups;

    public User(Map<String, Object> attrs) {

        this.username = (String) attrs.get("sub");
        this.email = (String) attrs.get("email");

        this.authTime = (Instant) ofNullable(attrs.get("iat")).filter(v -> v instanceof Instant).orElse(null);
        this.authExp = (Instant) ofNullable(attrs.get("exp")).filter(v -> v instanceof Instant).orElse(null);

        this.groups = ofNullable(attrs.get("groups")).map(v -> (List<String>) v).orElse(List.of());
    }

}
