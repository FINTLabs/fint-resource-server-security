# FINT Resource Server Security 
This project contains common classes to use in authorization.

> This library is a helper when using the Spring Boot Resource Server functionality. It helps with adding claims 
> as authorities and mapping Jwt's to  suitable principal classes

# Install
Add the following dependency to your `build.gradle` file:

`implementation 'no.vigoiks.fint:fint-resource-server-security:<latest version>'`

# Usage

## Example: Authorize an organisation
```java
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${fint.integration.service.authorized-org-id:vigo.no}")
    private String authorizedOrgId;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/**")
                        .hasAnyAuthority("ORGID_" + authorizedOrgId)
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt()
                        .jwtAuthenticationConverter(new FintJwtUserConverter()));
        return http.build();
    }
}
```

## Example: Authorize a role
```java
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${fint.integration.service.authorized-role:https://role-catalog.vigoiks.no/vigo/elevfakturering/user}")
    private String authorizedRole;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/**")
                        .hasRole(authorizedRole)
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt()
                        .jwtAuthenticationConverter(new FintJwtUserConverter()));
        return http.build();
    }
}
```

## Example: Convert a Jwt to a user principal
```java
@GetMapping
public ResponseEntity<FintJwtEndUserPrincipal> getLatestIntegrationConfigurations(@AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(FintJwtEndUserPrincipal.from(jwt));
}
```

