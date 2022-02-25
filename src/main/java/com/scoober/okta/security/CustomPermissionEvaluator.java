package com.scoober.okta.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Map<String, Map<Domains, List<Permissions>>> ROLE_PERMISSION_MATRIX = Map.of(
            "user",      Map.of(Domains.SECRET, List.of(Permissions.READ)),
            "moderator", Map.of(Domains.SECRET, List.of(Permissions.READ, Permissions.CREATE, Permissions.UPDATE)),
            "admin",     Map.of(Domains.SECRET, List.of(Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE)),
            "Everyone",  Map.of(Domains.SECRET, List.of(Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DELETE))
    );

    @Override
    public boolean hasPermission(Authentication auth, Object domain, Object permission) {
        if (auth instanceof AbstractOAuth2TokenAuthenticationToken && domain instanceof Domains && permission instanceof Permissions) {
            var attrs = ((AbstractOAuth2TokenAuthenticationToken<?>) auth).getTokenAttributes();
            var groups = (List<String>) attrs.getOrDefault("groups", List.of());
            return groups.stream().anyMatch(group -> ROLE_PERMISSION_MATRIX
                    .getOrDefault(group, Map.of())
                    .getOrDefault(domain, List.of())
                    .contains(permission));
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String domain, Object permission) {
        return hasPermission(auth, domain, permission);
    }
}