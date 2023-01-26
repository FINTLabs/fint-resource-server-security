package no.vigoiks.resourceserver.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;

@Builder
@Data
public class FintJwtCorePrincipal {

    private String orgId;
    private List<String> roles;

    public static FintJwtCorePrincipal from(Jwt jwt) {
        return FintJwtCorePrincipal.builder()
                .orgId(jwt.getClaimAsString("fintAssetIDs"))
                .roles(jwt.hasClaim("roles") ? jwt.getClaimAsStringList("roles") : Collections.emptyList())
                .build();
    }
}
