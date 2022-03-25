package no.vigoiks.resourceserver.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;

/**
 * Extracts attributes from the JWT token and adds them as authorities.
 */
public class FintJwtDefaultConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private HashMap<String, String> authoritiesMap = new HashMap<>() {{
        put("scope", "SCOPE_");
    }};

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return Flux
                .fromIterable(authoritiesMap.entrySet())
                .filter(claimPrefixEntry -> jwt.hasClaim(claimPrefixEntry.getKey()))
                .flatMap(entry -> extractAuthorities(jwt, entry.getKey(), entry.getValue()))
                .collectList()
                .map(authorities -> new JwtAuthenticationToken(jwt, authorities));
    }

    /**
     * Adds an attribute to the authoritiesMap.
     * @param claimName Attribute name in the JWT token. E.g. <code>organizationid</code>.
     * @param prefix Prefix to use for the authority. E.g. <code>ROLE_</code>.
     * @return This instance.
     */
    public FintJwtDefaultConverter addMapping(String claimName, String prefix) {
        authoritiesMap.put(claimName, prefix);
        return this;
    }

    public FintJwtDefaultConverter setMappings(HashMap<String, String> authoritiesMap) {
        this.authoritiesMap = authoritiesMap;
        return this;
    }

    @SuppressWarnings("unchecked")
    private Flux<GrantedAuthority> extractAuthorities(Jwt jwt, String claimName, String prefix) {
        Object claim = jwt.getClaim(claimName);

        Flux<String> authorities;
        if (claim instanceof String) {
            authorities = Flux.fromArray(((String) claim).split(" "));
        } else if (claim instanceof Collection) {
            authorities = Flux.fromIterable((Collection<String>) claim);
        } else {
            authorities = Flux.empty();
        }

        return authorities.map(authority -> new SimpleGrantedAuthority(prefix + authority));
    }

}