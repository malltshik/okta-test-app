package com.scoober.okta;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	private static final Map<String, Map<String, List<String>>> MATRIX = Map.of(
			"Everyone", Map.of("SECRET", List.of("READ")),
			"Group2", Map.of("SECRET", List.of("READ", "CREATE"))
	);

	@Override
	public boolean hasPermission(Authentication auth, Object domain, Object permission) {
		if (auth instanceof JwtAuthenticationToken && domain instanceof String && permission instanceof String) {
			List<String> groups = (List<String>) ((JwtAuthenticationToken) auth).getTokenAttributes().getOrDefault("groups", List.of());
			for (String group : groups) {
				if(MATRIX.getOrDefault(group, Map.of())
						.getOrDefault(domain, List.of())
						.contains(permission))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String domain, Object permission) {
		return hasPermission(auth, domain, permission);
	}
}